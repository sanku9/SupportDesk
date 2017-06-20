package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.ManageAppDAO;
import com.supportdesk.dao.QueueDAO;
import com.supportdesk.dao.WorkOrderDAO;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.QueueEntity;
import com.supportdesk.entity.WorkOrderEntity;

@Service
public class WorkOrderService extends AbstractService {

	@Autowired
	WorkOrderDAO workOrderDAO;
	
	@Autowired
	ManageAppDAO 	applicationDAO;
	
	@Autowired
	QueueDAO  queueDAO;

	public WorkOrderDAO getWorkOrderDAO() {
		return workOrderDAO;
	}

	public void setWorkOrderDAO(WorkOrderDAO incidentDAO) {
		this.workOrderDAO = incidentDAO;
	}
	
	@Transactional
	public List<ApplicationEntity> getApplicationList() {
		return applicationDAO.getList();
	}
	
	@Transactional
	public List<QueueEntity> getQueueList() {
		return queueDAO.getQueueList();
	}

	@Transactional
	public List<WorkOrderEntity> getWorkOrderList(String asc, String sortField, String searchParameter) {
		return workOrderDAO.getWorkOrderList(asc, sortField, searchParameter);
	}
	
	@Transactional
	public List<WorkOrderEntity> getOpenWorkOrderList() {
		return workOrderDAO.getOpenWorkOrderList();
	}

	public Integer getWorkOrderCount(List<WorkOrderEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	public List<WorkOrderEntity> getWorkOrders(List<WorkOrderEntity> list,
			int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Transactional
	public void uploadWorkOrders(List<WorkOrderEntity> parseWOList) {
		workOrderDAO.uploadWorkOrders(parseWOList);
		
	}

	@Transactional
	public void updateWorkOrderComment(String commentId, String comments) {
		workOrderDAO.updateWorkOrderComment(commentId, comments);
	}

}
