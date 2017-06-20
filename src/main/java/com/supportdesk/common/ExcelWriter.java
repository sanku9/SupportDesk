package com.supportdesk.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.TaskEntity;
import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;

public class ExcelWriter {
	
	private List<IncidentSlmEntity> incidentList;
	private List<WorkOrderEntity> workOrderList;
	private List<ChangeRequestEntity> changeRequestList;
	private List<TaskEntity> taskList;

	public List<IncidentSlmEntity> getIncidentList() {
		return incidentList;
	}

	public void setIncidentList(List<IncidentSlmEntity> incidentList) {
		this.incidentList = incidentList;
	}

	public List<WorkOrderEntity> getWorkOrderList() {
		return workOrderList;
	}

	public void setWorkOrderList(List<WorkOrderEntity> workOrderList) {
		this.workOrderList = workOrderList;
	}

	public List<ChangeRequestEntity> getChangeRequestList() {
		return changeRequestList;
	}

	public void setChangeRequestList(List<ChangeRequestEntity> changeRequestList) {
		this.changeRequestList = changeRequestList;
	}

	public List<TaskEntity> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskEntity> taskList) {
		this.taskList = taskList;
	}

	public File exportToXLS() throws Exception {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		IncidentExcelWriter excelIncidentWriter = new IncidentExcelWriter();
		WorkOrderExcelWriter workOrderExcelWriter = new WorkOrderExcelWriter();
		ChangeRequestExcelWriter changeRequestExcelWriter = new ChangeRequestExcelWriter();
		TaskExcelWriter taskExcelWriter = new TaskExcelWriter();
		
		File file = null;
		String excelFilePath = Constants.Incident_Email_Excel;
		try {
			Workbook workbook = getWorkbook(excelFilePath);
			Sheet sheet = null;
			sheet = excelIncidentWriter.writeIncident(workbook, sheet, getIncidentList());
			sheet = workOrderExcelWriter.writeWorkOrder(workbook, sheet, getWorkOrderList());
			sheet = changeRequestExcelWriter.writeChangeRequest(workbook, sheet, getChangeRequestList());

			taskExcelWriter.writeTask(workbook, null, getTaskList());
			
			try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
				workbook.write(outputStream);
			}	
		} catch (Exception e) {
			exHandler.logErrors(e);
			throw e;
		}

		return file;
	
	}
	
	private Workbook getWorkbook(String excelFilePath)
	        throws IOException {
	    Workbook workbook = null;
	 
	    if (excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook();
	    } else if (excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook();
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}
	
}
