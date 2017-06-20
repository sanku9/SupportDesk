package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.TaskDAO;
import com.supportdesk.entity.TaskEntity;
import com.supportdesk.entity.TaskIdEntity;

@Service
public class TaskService extends AbstractService {

	@Autowired
	TaskDAO taskDAO;

	@Transactional
	public TaskEntity getTaskById(String taskId) {
		return taskDAO.getTaskById(taskId);
	}
	
	@Transactional
	public void updateTaskEntity(TaskEntity entity) {
		taskDAO.updateTask(entity);
	}

	@Transactional
	public void saveTask(TaskEntity task) {
		TaskIdEntity taskIdEntity = new TaskIdEntity();
		taskDAO.persist(taskIdEntity);
		task.setTaskId("TK"+taskIdEntity.getId());
		taskDAO.updateTask(task);
		/*if (task.getTaskId() == null || task.getTaskId().isEmpty()) {
			taskDAO.udpateTaskId(task);
		}*/
	}

	@Transactional
	public List<TaskEntity> fetchTaskList(String searchtext) {
		return taskDAO.fetchTaskList(searchtext);
		
	}

	@Transactional
	public void deleteTaskEntity(String taskId) {
		taskDAO.deleteTask(taskId);
	}

	@Transactional
	public List<TaskEntity> getOpenTaskList() {
		return taskDAO.getOpenTaskList();
	}
}
