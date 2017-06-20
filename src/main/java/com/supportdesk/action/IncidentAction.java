package com.supportdesk.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Utility;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.IncidentService;
import com.supportdesk.service.SnowTicketService;

public class IncidentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(IncidentAction.class);
	List<IncidentSlmEntity> incidentList = new ArrayList<IncidentSlmEntity>();
	List<ApplicationEntity> appList = new ArrayList<ApplicationEntity>();
	
	public List<ApplicationEntity> getAppList() {
		return appList;
	}

	public void setAppList(List<ApplicationEntity> appList) {
		this.appList = appList;
	}

	@Autowired
	IncidentService incidentService;
	
	@Autowired
	SnowTicketService snowTicketService;
	
	private File incidentFile;
	
	private String fileExtension;
	
	private String status;	
	
	private String incId;
	public String getIncId() {
		return incId;
	}

	public void setIncId(String incId) {
		this.incId = incId;
	}

	private String incStat;
	private String appl;
	private String reportDate;
	
	public String getIncStat() {
		return incStat;
	}

	public void setIncStat(String incStat) {
		this.incStat = incStat;
	}

	public String getAppl() {
		return appl;
	}

	public void setAppl(String appl) {
		this.appl = appl;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<IncidentSlmEntity> getIncidentList() {
		return incidentList;
	}

	public void setIncidentList(List<IncidentSlmEntity> incidentList) {
		this.incidentList = incidentList;
	}

	public IncidentService getIncidentService() {
		return incidentService;
	}

	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	public File getIncidentFile() {
		return incidentFile;
	}

	public void setIncidentFile(File incidentFile) {
		this.incidentFile = incidentFile;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// Your result List
	protected List<IncidentSlmEntity>      gridModel;

	public List<IncidentSlmEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<IncidentSlmEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
		incStat = "open";
		appList = incidentService.getApplicationList();
        return SUCCESS;
    }
	/*
	public IncidentEntity getModel() {
		// TODO Auto-generated method stub
		return incidentEntity;
	}*/
	
	public String incidentSLMFileUpload() {
		//List<IncidentSlmEntity> parseIncList = null;	
		try {
			/*parseIncList = Utility.parseInputIncSLMFile(getIncidentFile(), getFileExtension(), incidentService.getApplicationList(), incidentService.getQueueList());
			incidentService.uploadIncidentSLM(parseIncList);*/
			
			Utility.parseInputSnowIncFile(getIncidentFile(), getFileExtension(), snowTicketService.getApplicationList(), snowTicketService.getQueueList());
			snowTicketService.uploadIncidentSLM(Utility.getIncidentList());
			incStat = "open";
			appList = incidentService.getApplicationList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addActionError("Some Problem Occurred While Uploading File. Please Check The File Format and Data.");
			e.printStackTrace();
			return SUCCESS;
		}
		//System.out.println("List Size :"+parseFile.size());
		addActionMessage("Records Uploaded Successfully.");
		return SUCCESS;
	}
	
	public String incidentResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    System.out.println("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());

	    StringBuilder searchString = new StringBuilder();
	    searchString.append("incidentId:"+getIncId());
	    searchString.append(";incidentStatus:"+getIncStat());
	    searchString.append(";applicationId:"+getAppl());
	    searchString.append(";reportedDate:"+getReportDate());
	    
	    incidentList = incidentService.getIncidentSLMList(getSord(),getSidx(),searchString.toString());

	   /* if (sord != null && sord.equalsIgnoreCase("asc"))
	    {
	      Collections.sort(incidentList);
	    }
	    if (sord != null && sord.equalsIgnoreCase("desc"))
	    {
	      Collections.sort(incidentList);
	      Collections.reverse(incidentList);
	    }*/

	    // Count all record (select count(*) from your_custumers)
	    if (incidentList != null) {
	    	records = incidentService.getIncidentCount(incidentList);
	    }

	    if (totalrows != null)
	    {
	      records = totalrows;
	    }

	    // Calucalate until rows ware selected
	    int to = (rows * page);

	    // Calculate the first row to read
	    int from = to - rows;

	    // Set to = max rows
	    if (to > records) to = records;

	    if (loadonce)
	    {
	      if (totalrows != null && totalrows > 0)
	      {
	        setGridModel(incidentList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentList);
	      }
	    }
	    else
	    {
	      // Search Custumers
	      if (searchString != null && searchOper != null)
	      {/*
	        int id = Integer.parseInt(searchString);
	        if (searchOper.equalsIgnoreCase("eq"))
	        {
	          logger.debug("search id equals " + id);
	          List<Customer> cList = new ArrayList<Customer>();
	          Customer customer = incidentService.findById(incidentList, id);

	          if (customer != null) cList.add(customer);

	          setGridModel(cList);
	        }
	        else if (searchOper.equalsIgnoreCase("ne"))
	        {
	          logger.debug("search id not " + id);
	          setGridModel(incidentService.findNotById(incidentList, id, from, to));
	        }
	        else if (searchOper.equalsIgnoreCase("lt"))
	        {
	          logger.debug("search id lesser then " + id);
	          setGridModel(incidentService.findLesserAsId(incidentList, id, from, to));
	        }
	        else if (searchOper.equalsIgnoreCase("gt"))
	        {
	          logger.debug("search id greater then " + id);
	          setGridModel(incidentService.findGreaterAsId(incidentList, id, from, to));
	        }*/
	      }
	      else
	      {
	        setGridModel(incidentService.getIncidents(incidentList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
}
