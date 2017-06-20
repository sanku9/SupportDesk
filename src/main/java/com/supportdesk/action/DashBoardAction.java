package com.supportdesk.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.TaskEntity;
import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.ChangeRequestService;
import com.supportdesk.service.IncidentService;
import com.supportdesk.service.TaskService;
import com.supportdesk.service.WorkOrderService;

@Component("DashBoardAction")
@Scope("prototype")
public class DashBoardAction extends BaseAction {

private static final long serialVersionUID = 1L;
	
	private int incidentOpenCount;
	private int workOrderOpenCount;
	private int crOpenCount;
	private int taskOpenCount;
	private String cycle = "1";
	
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public int getIncidentOpenCount() {
		return incidentOpenCount;
	}

	public void setIncidentOpenCount(int incidentOpenCount) {
		this.incidentOpenCount = incidentOpenCount;
	}

	public int getWorkOrderOpenCount() {
		return workOrderOpenCount;
	}

	public void setWorkOrderOpenCount(int workOrderOpenCount) {
		this.workOrderOpenCount = workOrderOpenCount;
	}

	public int getCrOpenCount() {
		return crOpenCount;
	}

	public void setCrOpenCount(int crOpenCount) {
		this.crOpenCount = crOpenCount;
	}

	public int getTaskOpenCount() {
		return taskOpenCount;
	}

	public void setTaskOpenCount(int taskOpenCount) {
		this.taskOpenCount = taskOpenCount;
	}
	
	@Autowired
	IncidentService incidentService;

	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	ChangeRequestService changeRequestService;
	
	@Autowired
	TaskService taskService;
	
	public String execute() throws ApplicationBaseException {
		List<IncidentSlmEntity> incidentList = incidentService.getOpenIncidentList();
		if(incidentList != null)
			setIncidentOpenCount(incidentList.size());
		
		List<WorkOrderEntity> workOrderList = workOrderService.getOpenWorkOrderList();
		if(workOrderList != null)
			setWorkOrderOpenCount(workOrderList.size());
		
		List<ChangeRequestEntity> changeRequestList = changeRequestService.getOpenChangeRequestList();
		if(changeRequestList != null)
			setCrOpenCount(changeRequestList.size());
		
		List<TaskEntity> taskList = taskService.getOpenTaskList();
		if(taskList != null)
			setTaskOpenCount(taskList.size());
		
        return SUCCESS;
    }
	
	public Object getModel() {
		return null;
	}
}
