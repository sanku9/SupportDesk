package com.supportdesk.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.ApplicationEntity;

@SuppressWarnings("unchecked")
@Repository
public class ManageAppDAO extends AbstractDAO {

	
	//This method return list of employees in database
	public List<ApplicationEntity> getList() {
		Criteria criteria = getSession().createCriteria(ApplicationEntity.class);
		criteria.addOrder(Order.asc("applName"));
		return (List<ApplicationEntity>) criteria.list();
	}

	public ApplicationEntity getByName(String applName) {
		Criteria criteria = getSession().createCriteria(ApplicationEntity.class);
		criteria.add(Restrictions.eq("applName",applName));
		return (ApplicationEntity) criteria.uniqueResult();

	}

	public void insert(ApplicationEntity entity) {
		getSession().persist(entity);
	}

	public void update(ApplicationEntity entity) {
		getSession().update(entity);
	}

	public void delete(ApplicationEntity entity) {
		ApplicationEntity entityToDel = (ApplicationEntity)getSession().get(ApplicationEntity.class,entity.getApplId());
		getSession().delete(entityToDel);
	}

	public void delete(String id) {
		List<String> idStrList = Arrays.asList(id);
		List<Long> idLongList = new ArrayList<Long>(); 
		for(String s : idStrList) idLongList.add(Long.parseLong(s));
		
		Query q = getSession().createQuery("delete from ApplicationEntity where applId in (:idList) ");
		q.setParameterList("idList", idLongList);
		q.executeUpdate();
	}
}
