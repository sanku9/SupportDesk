package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.ClassTypeEntity;

@SuppressWarnings("unchecked")
@Repository
public class ClassTypeDAO extends AbstractDAO {

	
	//This method return list of employees in database
	public List<ClassTypeEntity> getList() {
		Criteria criteria = getSession().createCriteria(ClassTypeEntity.class);
		return (List<ClassTypeEntity>) criteria.list();
	}

	public ClassTypeEntity getByType(String type) {
		Criteria criteria = getSession().createCriteria(ClassTypeEntity.class);
		criteria.add(Restrictions.eq("classType",type));
		return (ClassTypeEntity) criteria.uniqueResult();

	}

	public void insert(ClassTypeEntity entity) {
		getSession().persist(entity);
	}

	public void update(ClassTypeEntity entity) {
		getSession().update(entity);
	}

	public void delete(ClassTypeEntity entity) {
		ClassTypeEntity classTypeEntity = (ClassTypeEntity)getSession().get(ClassTypeEntity.class,entity.getClassId());
		getSession().delete(classTypeEntity);
	}
}
