package com.supportdesk.common;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.supportdesk.domain.ChangeRequestReport;
import com.supportdesk.domain.IncidentReport;
import com.supportdesk.domain.SnowTicketReport;
import com.supportdesk.domain.WorkOrderReport;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.QueueEntity;
import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.exception.ApplicationServiceException;

public class Utility {

	private static List<IncidentSlmEntity> incidentList;
	private static List<WorkOrderEntity> workOrderList;

	public static String encryptPassword(String pwd) {
		String encrypted = "";
		char c = 'e';
		int temp = 0;

		if (pwd == null)
			return encrypted;

		for (int i = 0; i < pwd.length(); i++) {
			c = pwd.charAt(i);
			if (((int) c >= 97 && (int) c <= 110)
					|| ((int) c >= 65 && (int) c <= 78)) {
				// System.out.println("Char c1:"+c);
				temp = (int) c + 13;
				c = (char) temp;
				encrypted = encrypted + c;
				// System.out.println("encrypted c:"+encrypted);
			} else if (((int) c > 78 && (int) c <= 90)
					|| (((int) c > 110 && (int) c <= 123))) {
				// System.out.println("Char c2:"+c);
				temp = (int) c - 13;
				c = (char) temp;
				encrypted = encrypted + c;
				// System.out.println("encrypted c:"+encrypted);
			} else {
				encrypted = encrypted + c;
			}
		}
		return encrypted;
	}

	/**
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimestampFromString(String dateString)
			throws ParseException {
		java.sql.Timestamp timestamp = null;
		String formattedDate = "";
		Date outputDate = null;
		if (StringUtils.isNotBlank(dateString)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constants.REMEDY_UPLOAD_TIMEFORMAT);
			SimpleDateFormat targetDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			java.util.Date parsedDate = dateFormat.parse(dateString);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			// formattedDate = "to_date('"+new
			// SimpleDateFormat("dd/MMM/yyyy HH:mm").format(timestamp)+"', 'dd-MON-yyyy hh24:mi')";
			formattedDate = targetDateFormat.format(timestamp);
			System.out.println("Formatted Date :" + formattedDate);
			outputDate = targetDateFormat.parse(formattedDate);
		} else
			formattedDate = "null";
		System.out.println("Timestamp1 :" + timestamp);
		return outputDate;

	}

	public static List<IncidentSlmEntity> parseInputIncSLMFile(
			File incidentFile, String fileExtension,
			List<ApplicationEntity> appList, List<QueueEntity> queueList)
			throws ApplicationServiceException {
		List<IncidentSlmEntity> incidentList = new ArrayList<IncidentSlmEntity>();
		List<IncidentReport> incidentIntermediateList = null;

		if (fileExtension.equalsIgnoreCase(("xls"))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xls*****************");
			IncidentSLMExcelReader excelReader = new IncidentSLMExcelReader();
			incidentIntermediateList = excelReader.readExcelFile(incidentFile);
			/* *
			 * if file extension .xls then following else if loop used to parse
			 * file
			 */
		} else if ((fileExtension.equalsIgnoreCase(("xlsx")))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xlsx*******************");
			IncidentSLMExcel2007Reader excelReader = new IncidentSLMExcel2007Reader();
			incidentIntermediateList = excelReader.readExcelFile(incidentFile);
		}

		incidentList = copyToFinalIncident(incidentIntermediateList, appList,
				queueList);

		return incidentList;

	}

	private static List<IncidentSlmEntity> copyToFinalIncident(
			List<IncidentReport> incidentIntermediateList,
			List<ApplicationEntity> appList, List<QueueEntity> queueList)
			throws ApplicationServiceException {
		List<IncidentSlmEntity> incidentList = new ArrayList<IncidentSlmEntity>();
		IncidentSlmEntity incidentEntity = null;

		Map<String, ApplicationEntity> appMap = new HashMap<String, ApplicationEntity>();
		for (ApplicationEntity entity : appList)
			appMap.put(entity.getApplName(), entity);

		Map<String, QueueEntity> queueMap = new HashMap<String, QueueEntity>();
		for (QueueEntity entity : queueList)
			queueMap.put(entity.getQueueName(), entity);

		boolean flag = false;
		for (IncidentReport record : incidentIntermediateList) {

			if (record.getIncidentID() == null
					|| record.getIncidentID().trim().isEmpty()
					|| !record.getIncidentID().trim().substring(0, 3)
							.equalsIgnoreCase("INC")
					|| record.getGoalCategoryChar() == null
					|| record.getGoalCategoryChar().isEmpty()
					|| appMap.get(record.getProductName()) == null
					|| queueMap.get(record.getAssignedGroup()) == null
					|| queueMap.get(record.getOwnerGroup()) == null) {
				System.err
						.println("Unable to insert record with incident ID : "
								+ record.getIncidentID());
				continue; // invalid record
			}

			IncidentSlmEntity incEntity = containsIncident(incidentList,
					record.getIncidentID());
			try {
				if (incEntity == null) {
					incidentEntity = new IncidentSlmEntity();
					flag = true;

					incidentEntity.setIncidentId(record.getIncidentID());
					incidentEntity.setApplicationId(appMap.get(record
							.getProductName()));
					incidentEntity.setAssignedGroup(queueMap.get(record
							.getAssignedGroup()));
					incidentEntity.setAssignedSupportCompany(record
							.getAssignedSupportCompany());
					incidentEntity.setAssignedSupportOrganization(record
							.getAssignedSupportOrganization());
					incidentEntity.setAssigneeName(record.getAssignee());

					if (record.getClosedDate() != null
							&& !record.getClosedDate().isEmpty())
						incidentEntity
								.setClosedDate(Utility
										.getTimestampFromString(record
												.getClosedDate()));

					incidentEntity.setCustFirstName(record.getFirstName());
					if (record.getEffortDurationHour() != null
							&& !record.getEffortDurationHour().isEmpty())
						incidentEntity.setEffortDurationHours(Integer
								.parseInt(record.getEffortDurationHour()));
					incidentEntity.setEscalated(record.getEscalated());
					// incidentEntity.setGoalType(record.getGoalCategoryChar());
					if (record.getGroupTransfers() != null
							&& !record.getGroupTransfers().isEmpty())
						incidentEntity.setGroupTransfers(Integer
								.parseInt(record.getGroupTransfers()));
					if (record.getIndividualTransfers() != null
							&& !record.getIndividualTransfers().isEmpty())
						incidentEntity.setIndividualTransfers(Integer
								.parseInt(record.getIndividualTransfers()));

					incidentEntity.setIncidentStatus(record.getStatus());
					incidentEntity.setIncidentSummary(record.getSummary());
					if (record.getLastDateDurationCalculated() != null
							&& !record.getLastDateDurationCalculated()
									.isEmpty())
						incidentEntity.setLastDateDurationCalculated(Utility
								.getTimestampFromString(record
										.getLastDateDurationCalculated()));
					if (record.getLastModifiedDate() != null
							&& !record.getLastModifiedDate().isEmpty())
						incidentEntity.setLastModifiedDate(Utility
								.getTimestampFromString(record
										.getLastModifiedDate()));
					incidentEntity.setLastResolvedDate(Utility
							.getTimestampFromString(record
									.getLastResolvedDate()));

					incidentEntity.setOlaHold(record.getoLAHold());
					if (record.getOverallStopTime() != null
							&& !record.getOverallStopTime().isEmpty())
						incidentEntity.setOverallEndDate(Utility
								.getTimestampFromString(record
										.getOverallStopTime()));
					if (record.getOverallStartTime() != null
							&& !record.getOverallStartTime().isEmpty())
						incidentEntity.setOverallStartDate(Utility
								.getTimestampFromString(record
										.getOverallStartTime()));
					incidentEntity.setOwnerGroup(queueMap.get(record
							.getOwnerGroup()));
					incidentEntity.setOwnerSupportCompany(record
							.getOwnerSupportCompany());
					incidentEntity.setOwnerSupportOrganization(record
							.getOwnerSupportOrganization());
					incidentEntity.setPriority(record.getPriority());
					incidentEntity.setReasonCode(record.getReasonCode());
					incidentEntity.setReasonDescription(record
							.getReasonDescription());
					if (record.getReOpenedDate() != null
							&& !record.getReOpenedDate().isEmpty())
						incidentEntity.setReopenedDate(Utility
								.getTimestampFromString(record
										.getReOpenedDate()));
					if (record.getReportedDate() != null
							&& !record.getReportedDate().isEmpty())
						incidentEntity.setReportedDate(Utility
								.getTimestampFromString(record
										.getReportedDate()));
					incidentEntity.setResolution(record.getResolution());
					if (record.getRespondedDate() != null
							&& !record.getRespondedDate().isEmpty())
						incidentEntity.setRespondedDate(Utility
								.getTimestampFromString(record
										.getRespondedDate()));
					incidentEntity.setServiceRequestId(record
							.getServiceRequestID());
					incidentEntity.setSlaHold(record.getsLAHold());
					incidentEntity.setSlmPriority(record.getsLMPriority());
					if (record.getSubmitDate() != null
							&& !record.getSubmitDate().isEmpty())
						incidentEntity
								.setSubmitDate(Utility
										.getTimestampFromString(record
												.getSubmitDate()));
					incidentEntity.setTicketType(record.getTicketType());

				} else {
					incidentEntity = incEntity;
					flag = false;
				}

				if (record.getGoalCategoryChar().contains("Response")) {
					if (record.getUpElapsedTime() != null
							&& !record.getUpElapsedTime().isEmpty())
						incidentEntity.setResponseEffortInSec(Integer
								.parseInt(record.getUpElapsedTime()));
					if (record.getsVTDueDate() != null
							&& !record.getsVTDueDate().isEmpty())
						incidentEntity
								.setResponseSlaDueDate(Utility
										.getTimestampFromString(record
												.getsVTDueDate()));
					incidentEntity.setSlmResponseStatus(record
							.getsLMRealTimeStatus());
					incidentEntity.setSlmResponseStatus(record
							.getsLMRealTimeStatus());
					incidentEntity.setMeasurementStatusResponse(record
							.getMeasurementStatus());
				} else {
					if (record.getUpElapsedTime() != null
							&& !record.getUpElapsedTime().isEmpty())
						incidentEntity.setResolveEffortInSec(Integer
								.parseInt(record.getUpElapsedTime()));
					if (record.getsVTDueDate() != null
							&& !record.getsVTDueDate().isEmpty())
						incidentEntity
								.setResolveSlaDueDate(Utility
										.getTimestampFromString(record
												.getsVTDueDate()));
					incidentEntity.setSlmResolveStatus(record
							.getsLMRealTimeStatus());
					incidentEntity.setSlmResolveStatus(record
							.getsLMRealTimeStatus());
					incidentEntity.setMeasurementStatusResolve(record
							.getMeasurementStatus());
				}
				if (record.getMeasurementStatus().equalsIgnoreCase("Missed")) {
					incidentEntity.setSlaBreachFlag("Yes");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationServiceException("", "", ex);
			}

			if (flag) {
				incidentList.add(incidentEntity);
			}

		}
		return incidentList;
	}

	public static IncidentSlmEntity containsIncident(
			List<IncidentSlmEntity> list, String id) {
		for (IncidentSlmEntity object : list) {
			if (object.getIncidentId().equalsIgnoreCase(id)) {
				return object;
			}
		}
		return null;
	}

	public static List<WorkOrderEntity> parseInputWOFile(File workOrderFile,
			String fileExtension, List<ApplicationEntity> appList,
			List<QueueEntity> queueList) throws ApplicationServiceException {
		List<WorkOrderEntity> workOrderList = new ArrayList<WorkOrderEntity>();
		List<WorkOrderReport> workOrderIntermediateList = null;

		if (fileExtension.equalsIgnoreCase(("xls"))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xls*****************");
			WOExcelReader excelReader = new WOExcelReader();
			workOrderIntermediateList = excelReader
					.readExcelFile(workOrderFile);
			/* *
			 * if file extension .xls then following else if loop used to parse
			 * file
			 */
		} else if ((fileExtension.equalsIgnoreCase(("xlsx")))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xlsx*******************");
			WOExcel2007Reader excelReader = new WOExcel2007Reader();
			workOrderIntermediateList = excelReader
					.readExcelFile(workOrderFile);
		}

		workOrderList = copyToFinalWorkOrder(workOrderIntermediateList,
				appList, queueList);

		return workOrderList;
	}

	private static List<WorkOrderEntity> copyToFinalWorkOrder(
			List<WorkOrderReport> workOrderIntermediateList,
			List<ApplicationEntity> appList, List<QueueEntity> queueList)
			throws ApplicationServiceException {
		List<WorkOrderEntity> workOrderList = new ArrayList<WorkOrderEntity>();
		WorkOrderEntity workOrderEntity = null;

		Map<String, ApplicationEntity> appMap = new HashMap<String, ApplicationEntity>();
		for (ApplicationEntity entity : appList)
			appMap.put(entity.getApplName(), entity);

		Map<String, QueueEntity> queueMap = new HashMap<String, QueueEntity>();
		for (QueueEntity entity : queueList)
			queueMap.put(entity.getQueueName(), entity);

		boolean flag = false;
		for (WorkOrderReport record : workOrderIntermediateList) {

			if (record.getWorkOrderID() == null
					|| record.getWorkOrderID().trim().isEmpty()
					|| !record.getWorkOrderID().trim().substring(0, 2)
							.equalsIgnoreCase("WO")
					|| appMap.get(record.getProductName()) == null
					|| queueMap.get(record.getAssigneeSupportGroupName()) == null) {
				System.err
						.println("Unable to insert record with WorkOrder ID : "
								+ record.getWorkOrderID());
				continue; // invalid record
			}

			try {

				workOrderEntity = new WorkOrderEntity();

				workOrderEntity.setWorkOrderId(record.getWorkOrderID());
				workOrderEntity.setAssociatedRequestId(record
						.getAssociatedRequestID());
				workOrderEntity.setStatus(record.getStatus());
				workOrderEntity.setSummary(record.getSummary());
				workOrderEntity.setApplicationId(appMap.get(record
						.getProductName()));
				workOrderEntity.setAssigneeSupportGroupName(queueMap.get(record
						.getAssigneeSupportGroupName()));
				workOrderEntity.setAssigneeSupportCompany(record
						.getAssigneeSupportCompany());
				workOrderEntity.setAssigneeSupportOrganization(record
						.getAssigneeSupportOrganization());
				workOrderEntity.setAssignee(record.getRequestAssignee());

				if (record.getCompletedDate() != null
						&& !record.getCompletedDate().isEmpty())
					workOrderEntity.setCompletedDate(Utility
							.getTimestampFromString(record.getCompletedDate()));
				if (record.getSubmitDate() != null
						&& !record.getSubmitDate().isEmpty())
					workOrderEntity.setSubmitDate(Utility
							.getTimestampFromString(record.getSubmitDate()));
				if (record.getLastModifiedDate() != null
						&& !record.getLastModifiedDate().isEmpty())
					workOrderEntity.setLastModifiedDate(Utility
							.getTimestampFromString(record
									.getLastModifiedDate()));
				if (record.getNextTargetDate() != null
						&& !record.getNextTargetDate().isEmpty())
					workOrderEntity
							.setNextTargetDate(Utility
									.getTimestampFromString(record
											.getNextTargetDate()));

				workOrderEntity.setTicketType(record.getTicketType());

				workOrderEntity.setCustFirstName(record
						.getRequestedByFirstName());
				workOrderEntity
						.setCustLastName(record.getRequestedByLastName());

				workOrderEntity.setAlstomBusinessEntity(record
						.getAlstomBusinessEntity());

				workOrderEntity.setPriority(record.getPriority());
				workOrderEntity.setRequestId(record.getRequestID());

				if (record.getReassignCounter() != null
						&& !record.getReassignCounter().isEmpty())
					workOrderEntity.setReassignCounter(Integer.parseInt(record
							.getReassignCounter()));

				workOrderEntity.setServiceLevel(record.getServiceLevel());
				if (record.getsLMStatus() != null
						&& !record.getsLMStatus().isEmpty()
						&& record.getsLMStatus().equalsIgnoreCase("Breached")) {
					workOrderEntity.setSlmStatus("Yes");
				} else {
					workOrderEntity.setSlmStatus("No");
				}
				workOrderEntity.setSlmStatus(record.getsLMStatus());
				workOrderEntity.setPriority(record.getPriority());
				workOrderEntity.setBreachreason(record.getBreachReason());
				
				workOrderList.add(workOrderEntity);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationServiceException("", "", ex);
			}

		}
		return workOrderList;
	}
	
	public static List<ChangeRequestEntity> parseInputCRFile(File changeRequestFile,
			String fileExtension, List<ApplicationEntity> appList,
			List<QueueEntity> queueList) throws ApplicationServiceException {
		List<ChangeRequestEntity> changeRequestList = new ArrayList<ChangeRequestEntity>();
		List<ChangeRequestReport> changeRequestIntermediateList = null;

		if (fileExtension.equalsIgnoreCase(("xls"))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xls*****************");
			CRExcelReader excelReader = new CRExcelReader();
			changeRequestIntermediateList = excelReader
					.readExcelFile(changeRequestFile);
			/* *
			 * if file extension .xls then following else if loop used to parse
			 * file
			 */
		} else if ((fileExtension.equalsIgnoreCase(("xlsx")))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xlsx*******************");
			CRExcel2007Reader excelReader = new CRExcel2007Reader();
			changeRequestIntermediateList = excelReader
					.readExcelFile(changeRequestFile);
		}

		if (changeRequestIntermediateList != null) {
			changeRequestList = copyToFinalChangeRequest(changeRequestIntermediateList, appList, queueList);
		} else {
			changeRequestList = new ArrayList<ChangeRequestEntity>();
		}

		return changeRequestList;
	}
	
	private static List<ChangeRequestEntity> copyToFinalChangeRequest(
			List<ChangeRequestReport> changeRequestIntermediateList,
			List<ApplicationEntity> appList, List<QueueEntity> queueList)
			throws ApplicationServiceException {
		List<ChangeRequestEntity> changeRequestList = new ArrayList<ChangeRequestEntity>();
		ChangeRequestEntity changeRequestEntity = null;

		Map<String, ApplicationEntity> appMap = new HashMap<String, ApplicationEntity>();
		for (ApplicationEntity entity : appList)
			appMap.put(entity.getApplName(), entity);

		Map<String, QueueEntity> queueMap = new HashMap<String, QueueEntity>();
		for (QueueEntity entity : queueList)
			queueMap.put(entity.getQueueName(), entity);

		boolean flag = false;
		for (ChangeRequestReport record : changeRequestIntermediateList) {

			if (record.getChangeId() == null
					|| record.getChangeId().trim().isEmpty()
					|| !record.getChangeId().trim().substring(0, 2)
							.equalsIgnoreCase("CR")
					/*|| appMap.get(record.getProductName()) == null*/
					|| queueMap.get(record.getChangeCoordinatorGroup()) == null) {
				System.err
						.println("Unable to insert record with Change Request ID : "
								+ record.getChangeId());
				continue; // invalid record
			}

			try {

				changeRequestEntity = new ChangeRequestEntity();

				changeRequestEntity.setChangeId(record.getChangeId());
				changeRequestEntity.setNote(record
						.getNote());
				changeRequestEntity.setStatus(record.getStatus());
				changeRequestEntity.setSummary(record.getSummary());
				changeRequestEntity.setStatus(record.getStatus());
				changeRequestEntity.setPriority(record.getPriority());
				changeRequestEntity.setApplicationId(appMap.get(record
						.getProductName()));
				changeRequestEntity.setChangeCoordinatorGroup(queueMap.get(record
						.getChangeCoordinatorGroup()));
				changeRequestEntity.setChangeCoordinator(record
						.getChangeCoordinator());
				changeRequestEntity.setChangeManager(record
						.getChangeManager());
				changeRequestEntity.setManagerGroup(record.getManagerGroup());
				
				if (record.getActualEndDate() != null
						&& !record.getActualEndDate().isEmpty())
					changeRequestEntity.setActualEndDate(Utility
							.getTimestampFromString(record.getActualEndDate()));
				if (record.getActualStartDate() != null
						&& !record.getActualStartDate().isEmpty())
					changeRequestEntity.setActualStartDate(Utility
							.getTimestampFromString(record.getActualStartDate()));
				if (record.getScheduledEndDate() != null
						&& !record.getScheduledEndDate().isEmpty())
					changeRequestEntity.setScheduledEndDate(Utility
							.getTimestampFromString(record
									.getScheduledEndDate()));
				if (record.getScheduledStartDate() != null
						&& !record.getScheduledStartDate().isEmpty())
					changeRequestEntity
							.setScheduledStartDate(Utility
									.getTimestampFromString(record
											.getScheduledStartDate()));

				if (record.getCompletedDate() != null
						&& !record.getCompletedDate().isEmpty())
					changeRequestEntity.setCompletedDate(Utility
							.getTimestampFromString(record.getCompletedDate()));
				if (record.getSubmitDate() != null
						&& !record.getSubmitDate().isEmpty())
					changeRequestEntity.setSubmitDate(Utility
							.getTimestampFromString(record.getSubmitDate()));
				if (record.getLastModifiedDate() != null
						&& !record.getLastModifiedDate().isEmpty())
					changeRequestEntity.setLastModifiedDate(Utility
							.getTimestampFromString(record
									.getLastModifiedDate()));
				if (record.getTargetDate() != null
						&& !record.getTargetDate().isEmpty())
					changeRequestEntity
							.setTargetDate(Utility
									.getTimestampFromString(record
											.getTargetDate()));

				changeRequestEntity.setPriority(record.getPriority());
				changeRequestEntity.setRequestId(record.getRequestId());

				if (record.getTotalTimeSpent() != null
						&& !record.getTotalTimeSpent().isEmpty())
					changeRequestEntity.setTotalTimeSpent(Integer.parseInt(record
							.getTotalTimeSpent()));

				changeRequestEntity.setServiceLevel(record.getServiceLevel());
				
				
				changeRequestList.add(changeRequestEntity);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationServiceException("", "", ex);
			}

		}
		return changeRequestList;
	}

	public static List<IncidentSlmEntity> parseInputSnowIncFile(File incidentFile, String fileExtension,
			List<ApplicationEntity> applicationList, List<QueueEntity> queueList) throws ApplicationServiceException {
		List<IncidentSlmEntity> incidentList = new ArrayList<IncidentSlmEntity>();
		List<SnowTicketReport> incidentIntermediateList = null;

		if (fileExtension.equalsIgnoreCase(("xls"))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xls*****************");
			SnowTicketExcelReader excelReader = new SnowTicketExcelReader();
			incidentIntermediateList = excelReader.readExcelFile(incidentFile);
			/* *
			 * if file extension .xls then following else if loop used to parse
			 * file
			 */
		} else if ((fileExtension.equalsIgnoreCase(("xlsx")))) {
			/**
			 * call readExcelFile method of excelReader to read the excel file.
			 */
			System.out.println("xlsx*******************");
			SnowTicketExcel2007Reader excelReader = new SnowTicketExcel2007Reader();
			incidentIntermediateList = excelReader.readExcelFile(incidentFile);
		}

		copyToFinalSnowIncident(incidentIntermediateList, applicationList,
				queueList);

		return incidentList;
	}
	
	private static List<IncidentSlmEntity> copyToFinalSnowIncident(
			List<SnowTicketReport> incidentIntermediateList,
			List<ApplicationEntity> appList, List<QueueEntity> queueList)
			throws ApplicationServiceException {
		incidentList = new ArrayList<IncidentSlmEntity>();
		IncidentSlmEntity incidentEntity = null;
		
		workOrderList = new ArrayList<WorkOrderEntity>();
		WorkOrderEntity workOrderEntity = null;
		
		Map<String, ApplicationEntity> appMap = new HashMap<String, ApplicationEntity>();
		for (ApplicationEntity entity : appList)
			appMap.put(entity.getApplName().toLowerCase(), entity);

		Map<String, QueueEntity> queueMap = new HashMap<String, QueueEntity>();
		for (QueueEntity entity : queueList)
			queueMap.put(entity.getQueueName(), entity);

		boolean flag = false;
		for (SnowTicketReport record : incidentIntermediateList) {

			if ((record.getAffectedCI() != null && appMap.get(record.getAffectedCI().toLowerCase()) == null)
					|| queueMap.get(record.getAssignmentGroup()) == null) {
				System.err
						.println("Unable to insert record with incident ID : "
								+ record.getTask());
				continue; // invalid record
			}
			
			if(record.getTaskType().equalsIgnoreCase("incident")) {

				IncidentSlmEntity incEntity = containsIncident(incidentList,
						record.getTask());
				try {
					if (incEntity == null) {
						incidentEntity = new IncidentSlmEntity();
						flag = true;
	
						incidentEntity.setIncidentId(record.getTask());
						incidentEntity.setApplicationId(appMap.get(record
								.getAffectedCI().toLowerCase()));
						incidentEntity.setAssignedGroup(queueMap.get(record
								.getAssignmentGroup()));
						incidentEntity.setOwnerGroup(incidentEntity.getAssignedGroup());
						incidentEntity.setAssigneeName(record.getAssignedTo());
						//incidentEntity.setSlaBreachFlag(record.getHasBreached());
	
						
						incidentEntity.setIncidentSummary(record.getSummary());
						incidentEntity.setReOpenedCount(Integer.parseInt(record.getReOpenCount()));
						
						
						incidentEntity.setTicketType(record.getTaskType());
						if (record.getDueDate() != null
								&& !record.getDueDate().isEmpty())
							incidentEntity
									.setResolveSlaDueDate(Utility
											.getTimestampFromString(record
													.getDueDate()));
					} else {
						incidentEntity = incEntity;
						flag = false;
					}
					
					if (record.getCreated() != null
							&& !record.getCreated().isEmpty())
					{
						incidentEntity
								.setSubmitDate(Utility
										.getTimestampFromString(record
												.getCreated()));
						incidentEntity.setReportedDate(Utility
						.getTimestampFromString(record
								.getCreated()));
					}
					
					if (record.getUpdated() != null
							&& !record.getUpdated().isEmpty())
						incidentEntity.setLastModifiedDate(Utility
								.getTimestampFromString(record
										.getUpdated()));
	
					
					incidentEntity.setPriority(record.getPriority());
					
					
					if (record.getGroupHopCount() != null
							&& !record.getGroupHopCount().isEmpty())
						incidentEntity.setGroupTransfers(Integer
								.parseInt(record.getGroupHopCount()));
					
					if (record.getAssignToHopCount() != null
							&& !record.getAssignToHopCount().isEmpty())
						incidentEntity.setIndividualTransfers(Integer
								.parseInt(record.getAssignToHopCount()));
	
					incidentEntity.setIncidentStatus(record.getStatus());
	
					if (record.getSlaType().equalsIgnoreCase("Resolution")) {
						if (record.getEndTime() != null
								&& !record.getEndTime().isEmpty())
							incidentEntity
									.setClosedDate(Utility
											.getTimestampFromString(record
													.getEndTime()));
						incidentEntity.setSlmResolveStatus(record
								.getHasBreached());
					} else if (record.getSlaType().equalsIgnoreCase("Response")) {
						incidentEntity.setSlmResponseStatus(record
								.getHasBreached());
						if (record.getPlannedEndTime() != null
								&& !record.getPlannedEndTime().isEmpty())
							incidentEntity
									.setResponseSlaDueDate(Utility
											.getTimestampFromString(record
													.getPlannedEndTime()));
					}
					
					if (Boolean.valueOf(record.getHasBreached())) {
						incidentEntity.setSlaBreachFlag("Yes");
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new ApplicationServiceException("", "", ex);
				}
	
				if (flag) {
					incidentList.add(incidentEntity);
				}
				
			} else if(record.getTaskType().equalsIgnoreCase("Catalog Task")) {

					if (appMap.get(record.getAffectedCI().toLowerCase()) == null
							|| queueMap.get(record.getAssignmentGroup()) == null) {
						System.err
								.println("Unable to insert record with WorkOrder ID : "
										+ record.getTask());
						continue; // invalid record
					}

					try {

						workOrderEntity = new WorkOrderEntity();

						workOrderEntity.setWorkOrderId(record.getTask());
						
						workOrderEntity.setStatus(record.getStatus());
						workOrderEntity.setSummary(record.getSummary());
						workOrderEntity.setApplicationId(appMap.get(record.getAffectedCI().toLowerCase()));
						workOrderEntity.setAssigneeSupportGroupName(queueMap.get(record
								.getAssignmentGroup()));
						workOrderEntity.setAssignee(record.getAssignedTo());

													
						if (record.getCreated() != null
								&& !record.getCreated().isEmpty())
							workOrderEntity.setSubmitDate(Utility
									.getTimestampFromString(record.getCreated()));
						if (record.getUpdated() != null
								&& !record.getUpdated().isEmpty()) {
							workOrderEntity.setLastModifiedDate(Utility
									.getTimestampFromString(record
											.getUpdated()));
							
							if(record.getStatus() != null && record.getStatus().contains("Closed")) {
								workOrderEntity.setCompletedDate(Utility
										.getTimestampFromString(record.getUpdated()));
							}
						}
							
						if (record.getPlannedEndTime() != null
								&& !record.getPlannedEndTime().isEmpty())
							workOrderEntity
									.setNextTargetDate(Utility
											.getTimestampFromString(record
													.getPlannedEndTime()));

						workOrderEntity.setTicketType(record.getTaskType());

						

						workOrderEntity.setPriority(record.getPriority());
						

						if (record.getAssignToHopCount() != null
								&& !record.getAssignToHopCount().isEmpty())
							workOrderEntity.setReassignCounter(Integer.parseInt(record
									.getAssignToHopCount()));

						if (Boolean.valueOf(record.getHasBreached())) {
							workOrderEntity.setSlmStatus("Yes");
						} else {
							workOrderEntity.setSlmStatus("No");
						}
						workOrderEntity.setSlmStatus(record.getStatus());
						workOrderEntity.setPriority(record.getPriority());
						
						workOrderList.add(workOrderEntity);
						
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new ApplicationServiceException("", "", ex);
					}

			}

		}
		return incidentList;
	}

	public static List<IncidentSlmEntity> getIncidentList() {
		return incidentList;
	}
	
	public static List<WorkOrderEntity> getWorkOrderList() {
		return workOrderList;
	}
	
}
