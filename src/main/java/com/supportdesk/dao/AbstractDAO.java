package com.supportdesk.dao;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.BaseEntity;

public abstract class AbstractDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected DataSource dataSource;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void persist(BaseEntity entity) {
        getSession().persist(entity);
    }
 
    public void delete(BaseEntity entity) {
        getSession().delete(entity);
    }
    
    public void merge(BaseEntity entity) {
        getSession().merge(entity);
    }
}
