package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.SlaEntity;

@SuppressWarnings("unchecked")
@Repository
public class SlaDAO extends AbstractDAO {

	
	//This method return list of employees in database
	public List<SlaEntity> getList() {
		Criteria criteria = getSession().createCriteria(SlaEntity.class);
		return (List<SlaEntity>) criteria.list();
	}
	
	public SlaEntity getSlaByPriorityAndClassType(String priority, String classType) {
		Criteria criteria = getSession().createCriteria(SlaEntity.class);
		criteria.createAlias("classTypeId", "classTypeAlias");
		criteria.add(Restrictions.eq("priority",priority));
		criteria.add(Restrictions.eq("classTypeAlias.classType",classType));
		return (SlaEntity) criteria.uniqueResult();
	}

	public void insert(SlaEntity entity) {
		getSession().persist(entity);
	}

	public void update(SlaEntity entity) {
		getSession().update(entity);
	}

	public void delete(SlaEntity entity) {
		SlaEntity SlaEntity = (SlaEntity)getSession().get(SlaEntity.class,entity.getSlaId());
		getSession().delete(SlaEntity);
	}
}
