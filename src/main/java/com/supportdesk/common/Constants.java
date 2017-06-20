package com.supportdesk.common;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to maintain all constants for the application.
 * 
 * @version 1.0
 */
public class Constants {

	/**
	 * List of static constants used throughout the application.
	 * 
	 */
	
	//Incident SLM Report Excel File Column Positions
	
	public static final String SESSION_KEY_USER = "USER";
	public static final CharSequence DEFAULT_APP_PASSWORD = "23";
	
	static final Map<Integer, String> incidentReportMap;

	static {
		incidentReportMap = new HashMap<Integer, String>();
		int i = 0;
		incidentReportMap.put( i++ ,"Number");
		incidentReportMap.put( i++ ,"Summary");
		incidentReportMap.put( i++ ,"Priority");
		incidentReportMap.put( i++ ,"Assigned To");
		incidentReportMap.put( i++ ,"Created");
		incidentReportMap.put( i++ ,"Closed");
		incidentReportMap.put( i++ ,"Due Date");
		incidentReportMap.put( i++ ,"Assignment Group");
		incidentReportMap.put( i++ ,"Status");
		incidentReportMap.put( i++ ,"Sub Status");
		incidentReportMap.put( i++ ,"Task type");
		incidentReportMap.put( i++ ,"Description");
		incidentReportMap.put( i++ ,"Group Hop count");
		incidentReportMap.put( i++ ,"Assign to hop count");
		incidentReportMap.put( i++ ,"Created By");
		incidentReportMap.put( i++ ,"Updated");
		incidentReportMap.put( i++ ,"Updated by");
	}
	
	static final Map<Integer, String> workOrderReportMap;

	static {
		workOrderReportMap = new HashMap<Integer, String>();
		int i = 0;
		workOrderReportMap.put( i++ ,"workOrderID");
		workOrderReportMap.put( i++ ,"status");
		workOrderReportMap.put( i++ ,"summary");
		workOrderReportMap.put( i++ ,"productName");
		workOrderReportMap.put( i++ ,"submitDate");
		workOrderReportMap.put( i++ ,"completedDate");
		workOrderReportMap.put( i++ ,"lastModifiedDate");
		workOrderReportMap.put( i++ ,"nextTargetDate");
		workOrderReportMap.put( i++ ,"reassignCounter");
		workOrderReportMap.put( i++ ,"assigneeSupportGroupName");
		workOrderReportMap.put( i++ ,"assigneeSupportCompany");
		workOrderReportMap.put( i++ ,"assigneeSupportOrganization");
		workOrderReportMap.put( i++ ,"associatedRequestID");
		workOrderReportMap.put( i++ ,"requestAssignee");
		workOrderReportMap.put( i++ ,"requestID");
		workOrderReportMap.put( i++ ,"sLMStatus");
		workOrderReportMap.put( i++ ,"serviceLevel");
		workOrderReportMap.put( i++ ,"ticketType");
		workOrderReportMap.put( i++ ,"priority");
		workOrderReportMap.put( i++ ,"requestedByFirstName");
		workOrderReportMap.put( i++ ,"requestedByLastName");
		workOrderReportMap.put( i++ ,"breachReason");
		workOrderReportMap.put( i++ ,"alstomBusinessEntity");
		workOrderReportMap.put( i++ ,"detailedDescription");
	}
	
	static final Map<Integer, String> changeRequestReportMap;

	static {
		changeRequestReportMap = new HashMap<Integer, String>();
		int i = 0;
		changeRequestReportMap.put( i++ ,"changeId");
		changeRequestReportMap.put( i++ ,"status");
		changeRequestReportMap.put( i++ ,"summary");
		changeRequestReportMap.put( i++ ,"priority");
		changeRequestReportMap.put( i++ ,"riskLevel");
		changeRequestReportMap.put( i++ ,"scheduledStartDate");
		changeRequestReportMap.put( i++ ,"actualStartDate");
		changeRequestReportMap.put( i++ ,"scheduledEndDate");
		changeRequestReportMap.put( i++ ,"actualEndDate");
		changeRequestReportMap.put( i++ ,"requestedEndDate");
		changeRequestReportMap.put( i++ ,"changeCoordinator");
		changeRequestReportMap.put( i++ ,"changeManager");
		changeRequestReportMap.put( i++ ,"completedDate");
		changeRequestReportMap.put( i++ ,"changeCoordinatorGroup");
		changeRequestReportMap.put( i++ ,"lastModifiedDate");
		changeRequestReportMap.put( i++ ,"managerGroup");
		changeRequestReportMap.put( i++ ,"productName");
		changeRequestReportMap.put( i++ ,"requestId");
		changeRequestReportMap.put( i++ ,"serviceLevel");
		changeRequestReportMap.put( i++ ,"submitDate");
		changeRequestReportMap.put( i++ ,"targetDate");
		changeRequestReportMap.put( i++ ,"totalTimeSpent");
		changeRequestReportMap.put( i++ ,"note");

	}
	
	/*static final Map<Integer, String> snowIncidentReportMap;

	static {
		snowIncidentReportMap = new HashMap<Integer, String>();
		int i = 0;
		snowIncidentReportMap.put(	i++	,"number");
		snowIncidentReportMap.put(	i++	,"summary");
		snowIncidentReportMap.put(	i++	,"priority");
		snowIncidentReportMap.put(	i++	,"assignedTo");
		snowIncidentReportMap.put(	i++	,"created");
		snowIncidentReportMap.put(	i++	,"closed");
		snowIncidentReportMap.put(	i++	,"goalCategoryChar");
		snowIncidentReportMap.put(	i++	,"groupTransfers");
		snowIncidentReportMap.put(	i++	,"incidentID");
		snowIncidentReportMap.put(	i++	,"individualTransfers");
		snowIncidentReportMap.put(	i++	,"lastDateDurationCalculated");
		snowIncidentReportMap.put(	i++	,"lastModifiedDate");
		snowIncidentReportMap.put(	i++	,"lastResolvedDate");
		snowIncidentReportMap.put(	i++	,"measurementStatus");
		snowIncidentReportMap.put(	i++	,"oLAHold");
		snowIncidentReportMap.put(	i++	,"overallStartTime");
		snowIncidentReportMap.put(	i++	,"overallStopTime");
		snowIncidentReportMap.put(	i++	,"ownerGroup");
		snowIncidentReportMap.put(	i++	,"ownerSupportCompany");
		snowIncidentReportMap.put(	i++	,"ownerSupportOrganization");
		snowIncidentReportMap.put(	i++	,"priority");
		snowIncidentReportMap.put(	i++	,"productName");
		snowIncidentReportMap.put(	i++	,"reOpenedDate");
		snowIncidentReportMap.put(  i++ ,"reasonCode");
		snowIncidentReportMap.put(  i++ ,"reasonDescription");
		snowIncidentReportMap.put(  i++ ,"reportedDate");
		snowIncidentReportMap.put(  i++ ,"resolution");
		snowIncidentReportMap.put(	i++	,"respondedDate");
		snowIncidentReportMap.put(	i++	,"sLAHold");
		snowIncidentReportMap.put(	i++	,"sLMPriority");
		snowIncidentReportMap.put(	i++	,"sLMRealTimeStatus");
		snowIncidentReportMap.put(	i++	,"sVTDueDate");
		snowIncidentReportMap.put(	i++	,"serviceRequestID");
		snowIncidentReportMap.put(	i++	,"status");
		snowIncidentReportMap.put(	i++	,"submitDate");
		snowIncidentReportMap.put(	i++	,"ticketType");
		snowIncidentReportMap.put(	i++	,"upElapsedTime");
		snowIncidentReportMap.put(	i++	,"assignee");
		snowIncidentReportMap.put(	i++	,"firstName");
		snowIncidentReportMap.put(	i++	,"summary");
	}*/
	
	static final Map<Integer, String> snowIncidentMap;

	static {
		snowIncidentMap = new HashMap<Integer, String>();
		int i = 0;
	
		snowIncidentMap.put(i++	,"task");
		snowIncidentMap.put(i++	,"summary");
		snowIncidentMap.put(i++	,"assignedTo");
		snowIncidentMap.put(i++	,"assignmentGroup");	
		snowIncidentMap.put(i++	,"sla");	
		snowIncidentMap.put(i++	,"hasBreached");
		snowIncidentMap.put(i++	,"stage");	
		snowIncidentMap.put(i++	,"created");	
		snowIncidentMap.put(i++	,"dueDate");	
		snowIncidentMap.put(i++	,"endTime");	
		snowIncidentMap.put(i++	,"description");
		snowIncidentMap.put(i++	,"subStatus");
		snowIncidentMap.put(i++	,"status");	
		snowIncidentMap.put(i++	,"taskType");	
		snowIncidentMap.put(i++	,"updated");	
		snowIncidentMap.put(i++	,"updatedBy");	
		snowIncidentMap.put(i++	,"assignToHopCount");
		snowIncidentMap.put(i++	,"groupHopCount");		
		snowIncidentMap.put(i++	,"priority");
		snowIncidentMap.put(i++	,"slaType");
		snowIncidentMap.put(i++	,"plannedEndTime");
		snowIncidentMap.put(i++	,"affectedCI");
		snowIncidentMap.put(i++	,"reOpenCount");
	}
	
	public static final String UPLOAD_FOLDER_INCIDENT = "D:\\supportdesk\\incident";
	public static final String UPLOAD_FOLDER_WO = "D:\\supportdesk\\workorder";
	public static final String UPLOAD_FOLDER_CR = "D:\\supportdesk\\changerequest";
	
	public static final String UPLOAD_FOLDER_SNOW_TICKET = "D:\\supportdesk\\snowTickets";
	
	public static final String REMEDY_UPLOAD_TIMEFORMAT="dd/MM/yyyy HH:mm:ss";
	
	// for default exception message heading
	public final static String DEFAULT_EXCEPTION = "Exception: ";
	// for default exception message 
	public final static String EXCEPTION_DEFAULT_MESSAGE = "Some application specific exception has occurred.";
	// for default system exception message 
	public final static String SYSTEM_EXCEPTION_DEFAULT_MESSAGE = "Some system level exception has occurred.";
	//for exception print stack trace
	public final static String EXCEPTION_DETAILS="Exception Details: ";
	// for exception error code
	public final static String EXCEPTION_ERROR_CODE = "Error Code: ";
	// for exception error message
	public final static String EXCEPTION_ERROR_MESSAGE = "Error Message: ";
	public static final String Incident_Email_Excel = "D:\\Status Report.xlsx";
}
	
