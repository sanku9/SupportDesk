package com.supportdesk.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.domain.IncidentSummary;
import com.supportdesk.domain.UserIncidentSummary;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.IncidentSummaryService;

public class IncidentSummaryAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(IncidentAction.class);
	List<IncidentSummary> incidentSummaryList = new ArrayList<IncidentSummary>();
	List<UserIncidentSummary> userIncidentSummaryList = new ArrayList<UserIncidentSummary>();
	private String cycle;
	private String days;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	@Autowired
	IncidentSummaryService incidentSummaryService;

	public List<IncidentSummary> getIncidentSummaryList() {
		return incidentSummaryList;
	}

	public void setIncidentSummaryList(List<IncidentSummary> incidentSummaryList) {
		this.incidentSummaryList = incidentSummaryList;
	}

	// Your result List
	protected List<IncidentSummary>      gridModel;
	
	protected List<UserIncidentSummary>      gridModelUser;

	public List<IncidentSummary> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<IncidentSummary> gridModel) {
		this.gridModel = gridModel;
	}

	public List<UserIncidentSummary> getGridModelUser() {
		return gridModelUser;
	}

	public void setGridModelUser(List<UserIncidentSummary> gridModelUser) {
		this.gridModelUser = gridModelUser;
	}

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	protected Map<String, Object> userdata = new HashMap<String, Object>();
	
	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	
	public String incidentSummaryResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryList(cycle, days, "incident");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String incidentSummaryResultByApp()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryListByApp(cycle, id, "incident");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String userIncidentCount()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    
	    BigDecimal totalCount = BigDecimal.ZERO;
	    
	    userIncidentSummaryList = incidentSummaryService.getUserIncidentSummaryList(cycle, days, "incident");
	    
	    for (UserIncidentSummary is:userIncidentSummaryList) {
	    	totalCount = totalCount.add(is.getTotal());
	    }
	    userdata.put("item", "Total");
	    userdata.put("total", totalCount);

	    // Count all record (select count(*) from your_custumers)
	    if (userIncidentSummaryList != null) {
	    	records = incidentSummaryService.getUserIncidentCount(userIncidentSummaryList);
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
	    	  setGridModelUser(userIncidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	    	  setGridModelUser(userIncidentSummaryList);
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
	        setGridModelUser(incidentSummaryService.getUserIncidents(userIncidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String userIncidentCountByApp()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal totalCount = BigDecimal.ZERO;
	    
	    userIncidentSummaryList = incidentSummaryService.getUserIncidentSummaryListByApp(cycle, id, "incident");
	    
	    for (UserIncidentSummary is:userIncidentSummaryList) {
	    	totalCount = totalCount.add(is.getTotal());
	    }
	    userdata.put("item", "Total");
	    userdata.put("total", totalCount);

	    // Count all record (select count(*) from your_custumers)
	    if (userIncidentSummaryList != null) {
	    	records = incidentSummaryService.getUserIncidentCount(userIncidentSummaryList);
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
	    	  setGridModelUser(userIncidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	    	  setGridModelUser(userIncidentSummaryList);
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
	    	  setGridModelUser(incidentSummaryService.getUserIncidents(userIncidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String workOrderSummaryResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryList(cycle, days, "workOrder");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String workOrderSummaryResultByApp()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryListByApp(cycle, id, "workOrder");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String changeRequestSummaryResult()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryList(cycle, days, "changeRequest");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	//slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    //userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
	
	public String changeRequestSummaryResultByApp()
	  {
	    logger.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
	    logger.debug("Search :" + searchField + " " + searchOper + " " + searchString);
	    BigDecimal openCount = BigDecimal.ZERO;
	    BigDecimal closedCount = BigDecimal.ZERO;
	    BigDecimal pendingCount = BigDecimal.ZERO;
	    BigDecimal cancelledCount = BigDecimal.ZERO;
	    BigDecimal totalCount = BigDecimal.ZERO;
	    BigDecimal slaCount = BigDecimal.ZERO;
	    
	    incidentSummaryList = incidentSummaryService.getIncidentSummaryListByApp(cycle, id, "changeRequest");
	    
	    for (IncidentSummary is:incidentSummaryList) {
	    	openCount = openCount.add(is.getOpen());
	    	closedCount = closedCount.add(is.getClosed());
	    	pendingCount = pendingCount.add(is.getPending());
	    	cancelledCount = cancelledCount.add(is.getCancelled());
	    	totalCount = totalCount.add(is.getTotal());
	    	//slaCount = slaCount.add(is.getSlamissed());
	    }
	    userdata.put("item", "Total");
	    userdata.put("open", openCount);
	    userdata.put("pending", pendingCount);
	    userdata.put("cancelled", cancelledCount);
	    userdata.put("total", totalCount);
	    userdata.put("closed", closedCount);
	    //userdata.put("slamissed", slaCount);

	    // Count all record (select count(*) from your_custumers)
	    if (incidentSummaryList != null) {
	    	records = incidentSummaryService.getIncidentCount(incidentSummaryList);
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
	        setGridModel(incidentSummaryList.subList(0, totalrows));
	      }
	      else
	      {
	        // All Custumer
	        setGridModel(incidentSummaryList);
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
	        setGridModel(incidentSummaryService.getIncidents(incidentSummaryList, from, to));
	      }
	    }

	    // Calculate total Pages
	    total = (int) Math.ceil((double) records / (double) rows);


	    return SUCCESS;
	  }
}
