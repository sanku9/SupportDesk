package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.QueueEntity;

@SuppressWarnings("unchecked")
@Repository
public class QueueDAO extends AbstractDAO {

	
	//This method return list of employees in database
	public List<QueueEntity> getQueueList() {
		Criteria criteria = getSession().createCriteria(QueueEntity.class);
		return (List<QueueEntity>) criteria.list();
	}

	public QueueEntity getByName(String queueName) {
		Criteria criteria = getSession().createCriteria(QueueEntity.class);
		criteria.add(Restrictions.eq("queueName",queueName));
		return (QueueEntity) criteria.uniqueResult();

	}

	public void insert(QueueEntity queueEntity) {
		getSession().persist(queueEntity);
	}

	public void update(QueueEntity queueEntity) {
		getSession().update(queueEntity);
	}

	public void delete(QueueEntity queueEntity) {
		QueueEntity entityToDel = (QueueEntity)getSession().get(QueueEntity.class,queueEntity.getQueueId());
		getSession().delete(entityToDel);
	}
}
