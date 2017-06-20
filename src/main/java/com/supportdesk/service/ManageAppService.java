package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.ClassTypeDAO;
import com.supportdesk.dao.ManageAppDAO;
import com.supportdesk.entity.ApplicationEntity;

@Service
public class ManageAppService extends AbstractService {
	
	@Autowired
	ManageAppDAO manageAppDAO;	
	
	@Autowired
	ClassTypeDAO classTypeDAO;

	public ManageAppDAO getManageAppDAO() {
		return manageAppDAO;
	}

	public void setManageAppDAO(ManageAppDAO dao) {
		this.manageAppDAO = dao;
	}

	public ClassTypeDAO getClassTypeDAO() {
		return classTypeDAO;
	}

	public void setClassTypeDAO(ClassTypeDAO classTypeDAO) {
		this.classTypeDAO = classTypeDAO;
	}

	@Transactional
	public ApplicationEntity getByName(String name) {
		if (name == null || name.trim().isEmpty())
			return null;
		return manageAppDAO.getByName(name);
	}

	@Transactional
	public void save(ApplicationEntity entity) throws Exception {
		if (entity.getApplClassType() != null && entity.getApplClassType().getClassType() != null)
			entity.setApplClassType(classTypeDAO.getByType(entity.getApplClassType().getClassType()));
		manageAppDAO.insert(entity);
		
	}
	
	@Transactional
	public void update(ApplicationEntity entity) throws Exception {
		if (entity.getApplClassType() != null && entity.getApplClassType().getClassType() != null)
			entity.setApplClassType(classTypeDAO.getByType(entity.getApplClassType().getClassType()));
		manageAppDAO.update(entity);
		
	}	

	@Transactional
	public void delete(ApplicationEntity entity) {
		manageAppDAO.delete(entity);
		
	}

	public Integer getRecordsCount(List<ApplicationEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	@Transactional
	public List<ApplicationEntity> getList() {
		return manageAppDAO.getList();
	}

	public List<ApplicationEntity> getList(List<ApplicationEntity> list, int fromIndex,
			int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Transactional
	public void delete(String id) {
		manageAppDAO.delete(id);
	}
}
