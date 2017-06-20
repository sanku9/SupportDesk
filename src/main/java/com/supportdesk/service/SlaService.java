package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.ClassTypeDAO;
import com.supportdesk.dao.SlaDAO;
import com.supportdesk.entity.SlaEntity;

@Service
public class SlaService extends AbstractService {
	
	@Autowired
	SlaDAO slaDAO;	

	public SlaDAO getSlaDAO() {
		return slaDAO;
	}

	public void setSlaDAO(SlaDAO dao) {
		this.slaDAO = dao;
	}
	
	@Autowired
	ClassTypeDAO classTypeDAO;

	public ClassTypeDAO getClassTypeDAO() {
		return classTypeDAO;
	}

	public void setClassTypeDAO(ClassTypeDAO classTypeDAO) {
		this.classTypeDAO = classTypeDAO;
	}

	@Transactional
	public void save(SlaEntity entity) throws Exception {
		if (entity.getClassTypeId() != null && entity.getClassTypeId().getClassType() != null)
			entity.setClassTypeId(classTypeDAO.getByType(entity.getClassTypeId().getClassType()));
		slaDAO.insert(entity);
		
	}
	
	@Transactional
	public void update(SlaEntity entity) throws Exception {
		if (entity.getClassTypeId() != null && entity.getClassTypeId().getClassType() != null)
			entity.setClassTypeId(classTypeDAO.getByType(entity.getClassTypeId().getClassType()));
		slaDAO.update(entity);
		
	}	

	@Transactional
	public void delete(SlaEntity entity) {
		slaDAO.delete(entity);
		
	}

	public Integer getRecordsCount(List<SlaEntity> list) {
		if (list == null)
			return 0;
		return list.size();
	}

	@Transactional
	public List<SlaEntity> getList() {
		return slaDAO.getList();
	}

	public List<SlaEntity> getList(List<SlaEntity> list, int fromIndex,
			int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
}
