package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.QueueDAO;
import com.supportdesk.dao.UserQueueDAO;
import com.supportdesk.entity.UserQueueEntity;

@Service
public class UserQueueService extends AbstractService {
	
	@Autowired
	UserQueueDAO userQueueDAO;	
	
	@Autowired
	QueueDAO queueDAO;	


	public UserQueueDAO getUserQueueDAO() {
		return userQueueDAO;
	}

	public void setUserQueueDAO(UserQueueDAO queueDAO) {
		this.userQueueDAO = queueDAO;
	}

	public QueueDAO getQueueDAO() {
		return queueDAO;
	}

	public void setQueueDAO(QueueDAO queueDAO) {
		this.queueDAO = queueDAO;
	}

	@Transactional
	public UserQueueEntity getByUser(Long userId) {
		if (userId == null)
			return null;
		return userQueueDAO.getByUser(userId);
	}

	@Transactional
	public void save(UserQueueEntity entity) throws Exception {
		if (entity.getQueueId() != null && entity.getQueueId().getQueueName() != null)
			entity.setQueueId(queueDAO.getByName(entity.getQueueId().getQueueName()));
		userQueueDAO.insert(entity);
		
	}
	
	@Transactional
	public void update(UserQueueEntity entity) throws Exception {
		if (entity.getQueueId() != null && entity.getQueueId().getQueueName() != null)
			entity.setQueueId(queueDAO.getByName(entity.getQueueId().getQueueName()));
		userQueueDAO.update(entity);
		
	}	

	@Transactional
	public void delete(UserQueueEntity entity) {
		userQueueDAO.delete(entity);
		
	}

	public Integer getRecordsCount(List<UserQueueEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	@Transactional
	public List<UserQueueEntity> getList(Long userId) {
		return userQueueDAO.getListByUser(userId);
	}

	public List<UserQueueEntity> getList(List<UserQueueEntity> list, int fromIndex,
			int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
}
