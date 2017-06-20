package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.UserQueueEntity;

@SuppressWarnings("unchecked")
@Repository
public class UserQueueDAO extends AbstractDAO {

	
	//This method return list of employees in database
	public List<UserQueueEntity> getList() {
		Criteria criteria = getSession().createCriteria(UserQueueEntity.class);
		return (List<UserQueueEntity>) criteria.list();
	}
	
	public List<UserQueueEntity> getListByUser(Long userId) {
		Criteria criteria = getSession().createCriteria(UserQueueEntity.class);
		criteria.add(Restrictions.eq("userId.userId",userId));
		return (List<UserQueueEntity>) criteria.list();
	}

	public UserQueueEntity getByUser(Long userId) {
		Criteria criteria = getSession().createCriteria(UserQueueEntity.class);
		criteria.add(Restrictions.eq("userId.userId",userId));
		return (UserQueueEntity) criteria.uniqueResult();

	}

	public void insert(UserQueueEntity entity) {
		getSession().persist(entity);
	}

	public void update(UserQueueEntity entity) {
		getSession().update(entity);
	}

	public void delete(UserQueueEntity entity) {
		UserQueueEntity entityToDel = (UserQueueEntity)getSession().get(UserQueueEntity.class,entity.getUserQueueId());
		getSession().delete(entityToDel);
	}
}
