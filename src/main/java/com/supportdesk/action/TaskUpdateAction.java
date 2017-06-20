package com.supportdesk.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Constants;
import com.supportdesk.entity.TaskEntity;
import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.CommentService;
import com.supportdesk.service.TaskService;

public class TaskUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(TaskUpdateAction.class);
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	CommentService commentService;
	
	TaskEntity taskEntity;
	
	private String search;
	
	private String task;
	
	private String taskId;
	
	private String comment;
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;
	
	private String assignee;
	
	private String effortHours;
	
	public String getEffortHours() {
		return effortHours;
	}

	public void setEffortHours(String efforHours) {
		this.effortHours = efforHours;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	private List<TaskEntity> data;

	public List<TaskEntity> getData() {
		return data;
	}

	public void setData(List<TaskEntity> topicList) {
		this.data = topicList;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public TaskEntity getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(TaskEntity taskEntity) {
		this.taskEntity = taskEntity;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String details) {
		this.comment = details;
	}

	public String loadTask() throws ApplicationBaseException {
		data = taskService.fetchTaskList(getSearch());
        return SUCCESS;
    }

	public String updateTask() {
		if(taskEntity == null) {
			taskEntity= new TaskEntity();
		}
		
		Map<String, Object> sessionMap = getSession();
		UserEntity userSesionEntity = (UserEntity) sessionMap.get(Constants.SESSION_KEY_USER);
		
		if (taskId == null || taskId.trim().isEmpty()) {
			taskEntity= new TaskEntity();		
			taskEntity.setCreatedBy(userSesionEntity);
			taskEntity.setCreatedDate(Calendar.getInstance().getTime());
			taskEntity.setStartDate(getStartDate());
			taskEntity.setEndDate(getEndDate());
			taskEntity.setAssignee(getAssignee());
			taskEntity.setStatus("New");
			taskEntity.setUpdatedBy(userSesionEntity);
			taskEntity.setTask(getTask());	
			if(getEffortHours() != null && !getEffortHours().isEmpty())
				taskEntity.setEffortHours(getEffortHours());
			taskEntity.setUpdatedDate(Calendar.getInstance().getTime());
			
			taskService.saveTask(taskEntity);
		} else {
			taskEntity= taskService.getTaskById(taskId);
			taskEntity.setStartDate(getStartDate());
			taskEntity.setEndDate(getEndDate());
			taskEntity.setAssignee(getAssignee());
			taskEntity.setStatus(getStatus());
			taskEntity.setUpdatedBy(userSesionEntity);
			taskEntity.setTask(getTask());	
			if(getEffortHours() != null && !getEffortHours().isEmpty())
				taskEntity.setEffortHours(getEffortHours());
			taskEntity.setUpdatedDate(Calendar.getInstance().getTime());
			
			taskService.updateTaskEntity(taskEntity);
		}
		
		
		return "success";
	}
	
	public String deleteTask() {
		if(getTaskId() == null)
			return "error";
		
		taskService.deleteTaskEntity(getTaskId());
		
		return "success";
	}
}
