package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.ClassTypeDAO;
import com.supportdesk.entity.ClassTypeEntity;

@Service
public class ClassTypeService extends AbstractService {
	
	@Autowired
	ClassTypeDAO classTypeDAO;	

	public ClassTypeDAO getClassTypeDAO() {
		return classTypeDAO;
	}

	public void setClassTypeDAO(ClassTypeDAO dao) {
		this.classTypeDAO = dao;
	}

	@Transactional
	public ClassTypeEntity getByType(String name) {
		if (name == null || name.trim().isEmpty())
			return null;
		return classTypeDAO.getByType(name);
	}

	@Transactional
	public void save(ClassTypeEntity entity) throws Exception {
		classTypeDAO.insert(entity);
		
	}
	
	@Transactional
	public void update(ClassTypeEntity entity) throws Exception {
		classTypeDAO.update(entity);
		
	}	

	@Transactional
	public void delete(ClassTypeEntity entity) {
		classTypeDAO.delete(entity);
		
	}

	public Integer getRecordsCount(List<ClassTypeEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	@Transactional
	public List<ClassTypeEntity> getList() {
		return classTypeDAO.getList();
	}

	public List<ClassTypeEntity> getList(List<ClassTypeEntity> list, int fromIndex,
			int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
}
