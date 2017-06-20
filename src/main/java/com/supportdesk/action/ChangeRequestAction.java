package com.supportdesk.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Utility;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.ChangeRequestService;

public class ChangeRequestAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(ChangeRequestAction.class);
	List<ChangeRequestEntity> changeRequestList = new ArrayList<ChangeRequestEntity>();
	ChangeRequestEntity changeRequestEntity = new ChangeRequestEntity();
	
	@Autowired
	ChangeRequestService changeRequestService;
	
	private File changeRequestFile;
	
	private String fileExtension;
	
	private String status;	
	private String changeId;
	private String crStatus;
	private String appl;
	private String reportDate;
	List<ApplicationEntity> appList = new ArrayList<ApplicationEntity>();

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	public String getCrStatus() {
		return crStatus;
	}

	public void setCrStatus(String crStatus) {
		this.crStatus = crStatus;
	}

	public List<ApplicationEntity> getAppList() {
		return appList;
	}

	public void setAppList(List<ApplicationEntity> appList) {
		this.appList = appList;
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
	
	public List<ChangeRequestEntity> getChangeRequestList() {
		return changeRequestList;
	}

	public void setIncidentList(List<ChangeRequestEntity> list) {
		this.changeRequestList = list;
	}

	public ChangeRequestService getChangeRequestService() {
		return changeRequestService;
	}

	public void setChangeRequestService(ChangeRequestService changeRequestService) {
		this.changeRequestService = changeRequestService;
	}
	
	public File getChangeRequestFile() {
		return changeRequestFile;
	}

	public void setChangeRequestFile(File file) {
		this.changeRequestFile = file;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// Your result List
	protected List<ChangeRequestEntity>      gridModel;

	public List<ChangeRequestEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<ChangeRequestEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
		crStatus = "open";
		appList = changeRequestService.getApplicationList();
        return SUCCESS;
    }
	/*
	public IncidentEntity getModel() {
		// TODO Auto-generated method stub
		return incidentEntity;
	}*/
	
	public String changeRequestFileUpload() {
		List<ChangeRequestEntity> parseWOList = null;	
		crStatus = "open";
		appList = changeRequestService.getApplicationList();
		try {
			parseWOList = Utility.parseInputCRFile(getChangeRequestFile(), getFileExtension(), changeRequestService.getApplicationList(), changeRequestService.getQueueList());
			changeRequestService.uploadChangeRequests(parseWOList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addActionError("Some Problem Occurred While Uploading File. Please Check The File Format and Data.");
			e.printStackTrace();
			return "success";
		}
		//System.out.println("List Size :"+parseFile.size());
		addActionMessage("Records Uploaded Successfully.");
		return "success";
	}
	
	public String changeRequestResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);

	    StringBuilder searchString = new StringBuilder();
	    searchString.append("changeId:"+getChangeId());
	    searchString.append(";status:"+getCrStatus());
	    searchString.append(";applicationId:"+getAppl());
	    searchString.append(";submitDate:"+getReportDate());
	    
	    changeRequestList = changeRequestService.getChangeRequestList(getSord(),getSidx(),searchString.toString());

	    if (sord != null && sord.equalsIgnoreCase("asc"))
	    {
	      Collections.sort(changeRequestList);
	    }
	    if (sord != null && sord.equalsIgnoreCase("desc"))
	    {
	      Collections.sort(changeRequestList);
	      Collections.reverse(changeRequestList);
	    }

	    // Count all record (select count(*) from your_custumers)
	    if (changeRequestList != null) {
	    	records = changeRequestService.getChangeRequestCount(changeRequestList);
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
	        setGridModel(changeRequestList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(changeRequestList);
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
	        setGridModel(changeRequestService.getChangeRequests(changeRequestList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
}
