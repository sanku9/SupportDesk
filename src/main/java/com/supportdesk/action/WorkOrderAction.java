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
import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.SnowTicketService;
import com.supportdesk.service.WorkOrderService;

public class WorkOrderAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(WorkOrderAction.class);
	List<WorkOrderEntity> workOrderList = new ArrayList<WorkOrderEntity>();
	WorkOrderEntity workOrderEntity = new WorkOrderEntity();
	
	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	SnowTicketService snowTicketService;
	
	private File workOrderFile;
	
	private String fileExtension;
	
	private String status;	
	private String woId;
	private String woStat;
	private String appl;
	private String reportDate;
	List<ApplicationEntity> appList = new ArrayList<ApplicationEntity>();

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getWoStat() {
		return woStat;
	}

	public void setWoStat(String woStat) {
		this.woStat = woStat;
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
	
	public List<WorkOrderEntity> getWorkOrderList() {
		return workOrderList;
	}

	public void setIncidentList(List<WorkOrderEntity> list) {
		this.workOrderList = list;
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService incidentService) {
		this.workOrderService = incidentService;
	}
	
	public File getWorkOrderFile() {
		return workOrderFile;
	}

	public void setWorkOrderFile(File file) {
		this.workOrderFile = file;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// Your result List
	protected List<WorkOrderEntity>      gridModel;

	public List<WorkOrderEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<WorkOrderEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
		woStat = "open";
		appList = workOrderService.getApplicationList();
        return SUCCESS;
    }
	/*
	public IncidentEntity getModel() {
		// TODO Auto-generated method stub
		return incidentEntity;
	}*/
	
	public String workOrderFileUpload() {
		List<WorkOrderEntity> parseWOList = null;	
		woStat = "open";
		//appList = workOrderService.getApplicationList();
		try {
			/*parseWOList = Utility.parseInputWOFile(getWorkOrderFile(), getFileExtension(), workOrderService.getApplicationList(), workOrderService.getQueueList());
			workOrderService.uploadWorkOrders(parseWOList);*/
			Utility.parseInputSnowIncFile(getWorkOrderFile(), getFileExtension(), snowTicketService.getApplicationList(), snowTicketService.getQueueList());
			workOrderService.uploadWorkOrders(Utility.getWorkOrderList());
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
	
	public String workOrderResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);

	    StringBuilder searchString = new StringBuilder();
	    searchString.append("workOrderId:"+getWoId());
	    searchString.append(";status:"+getWoStat());
	    searchString.append(";applicationId:"+getAppl());
	    searchString.append(";submitDate:"+getReportDate());
	    
	    workOrderList = workOrderService.getWorkOrderList(getSord(),getSidx(),searchString.toString());

	    if (sord != null && sord.equalsIgnoreCase("asc"))
	    {
	      Collections.sort(workOrderList);
	    }
	    if (sord != null && sord.equalsIgnoreCase("desc"))
	    {
	      Collections.sort(workOrderList);
	      Collections.reverse(workOrderList);
	    }

	    // Count all record (select count(*) from your_custumers)
	    if (workOrderList != null) {
	    	records = workOrderService.getWorkOrderCount(workOrderList);
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
	        setGridModel(workOrderList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(workOrderList);
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
	        setGridModel(workOrderService.getWorkOrders(workOrderList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
}
