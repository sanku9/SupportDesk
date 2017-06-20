package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.ChangeRequestDAO;
import com.supportdesk.dao.ManageAppDAO;
import com.supportdesk.dao.QueueDAO;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.QueueEntity;

@Service
public class ChangeRequestService extends AbstractService {

	@Autowired
	ChangeRequestDAO changeRequestDAO;
	
	@Autowired
	ManageAppDAO 	applicationDAO;
	
	@Autowired
	QueueDAO  queueDAO;

	public ChangeRequestDAO getChangeRequestDAO() {
		return changeRequestDAO;
	}

	public void setChangeRequestDAO(ChangeRequestDAO changeRequestDAO) {
		this.changeRequestDAO = changeRequestDAO;
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
	public List<ChangeRequestEntity> getChangeRequestList(String asc, String sortField, String searchParameter) {
		return changeRequestDAO.getChangeRequestList(asc, sortField, searchParameter);
	}
	
	@Transactional
	public List<ChangeRequestEntity> getOpenChangeRequestList() {
		return changeRequestDAO.getOpenChangeRequestList();
	}

	public Integer getChangeRequestCount(List<ChangeRequestEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	public List<ChangeRequestEntity> getChangeRequests(List<ChangeRequestEntity> list,
			int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Transactional
	public void uploadChangeRequests(List<ChangeRequestEntity> parseWOList) {
		changeRequestDAO.uploadChangeRequests(parseWOList);
		
	}

	@Transactional
	public void updateChangeRequestComment(String commentId, String comments) {
		changeRequestDAO.updateChangeRequestComment(commentId, comments);
	}

}
