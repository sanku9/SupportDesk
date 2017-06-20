package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.ClassTypeEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.ClassTypeService;

public class ClassTypeUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ClassTypeEntity classTypeEntity = new ClassTypeEntity();
	private String oper;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	public ClassTypeEntity getClassTypeEntity() {
		return classTypeEntity;
	}

	public void setClassTypeEntity(ClassTypeEntity appEntity) {
		this.classTypeEntity = appEntity;
	}

	@Autowired
	ClassTypeService classTypeService;
	
	public ClassTypeService getClassTypeService() {
		return classTypeService;
	}

	public void setClassTypeService(ClassTypeService service) {
		this.classTypeService = service;
	}

	public ClassTypeEntity getModel() {
		return classTypeEntity;
	}
	
	public String update() throws ApplicationServiceException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				
				if(getOper().equalsIgnoreCase("del")) {
					if (classTypeEntity.getClassId() == null && getId() != null) 
						classTypeEntity.setClassId(Long.parseLong(getId().toString()));
					classTypeService.delete(classTypeEntity);
				} else if (getOper().equalsIgnoreCase("edit")) {					
					if (classTypeEntity.getClassId() == null && getId() != null) 
						classTypeEntity.setClassId(Long.parseLong(getId().toString()));
					classTypeService.update(classTypeEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					classTypeService.save(classTypeEntity);
				}
				status = SUCCESS;
			}
			
		}

		catch (Exception e) {
			exHandler.logErrors(e);
			status = ERROR;
			//throw new ApplicationServiceException(e.getMessage(),e.getMessage(),e) ;
		}
		
		return status;

	}
}
