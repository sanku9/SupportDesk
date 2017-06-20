package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.QueueDAO;
import com.supportdesk.entity.QueueEntity;

@Service
public class QueueService extends AbstractService {
	
	@Autowired
	QueueDAO queueDAO;	

	public QueueDAO getQueueDAO() {
		return queueDAO;
	}

	public void setQueueDAO(QueueDAO queueDAO) {
		this.queueDAO = queueDAO;
	}

	@Transactional
	public QueueEntity getByName(String name) {
		if (name == null || name.trim().isEmpty())
			return null;
		return queueDAO.getByName(name);
	}

	@Transactional
	public void addUser(QueueEntity queueEntity) throws Exception {
		queueDAO.insert(queueEntity);
		
	}
	
	@Transactional
	public void update(QueueEntity queueEntity) throws Exception {
		queueDAO.update(queueEntity);
		
	}	

	@Transactional
	public void delete(QueueEntity queueEntity) {
		queueDAO.delete(queueEntity);
		
	}

	public Integer getRecordsCount(List<QueueEntity> queueList) {
		if (queueList == null)
			return 0;
		return queueList.size();
	}

	@Transactional
	public List<QueueEntity> getQueueList() {
		return queueDAO.getQueueList();
	}

	public List<QueueEntity> getQueueLists(List<QueueEntity> userList, int fromIndex,
			int toIndex) {
		return userList.subList(fromIndex, toIndex);
	}
}
