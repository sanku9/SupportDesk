package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.ManageAppService;

public class ManageAppUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ApplicationEntity appEntity = new ApplicationEntity();
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
	public ApplicationEntity getApplicationEntity() {
		return appEntity;
	}

	public void setApplicationEntity(ApplicationEntity appEntity) {
		this.appEntity = appEntity;
	}

	@Autowired
	ManageAppService manageAppService;
	
	public ManageAppService getManageAppService() {
		return manageAppService;
	}

	public void setManageAppService(ManageAppService service) {
		this.manageAppService = service;
	}

	public ApplicationEntity getModel() {
		return appEntity;
	}
	
	public String update() throws ApplicationServiceException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				
				if(getOper().equalsIgnoreCase("del")) {
					if (appEntity.getApplId() == null) 
						appEntity.setApplId(Long.parseLong(getId()));
					manageAppService.delete(getId());
				} else if (getOper().equalsIgnoreCase("edit")) {	
					if (appEntity.getApplId() == null) 
						appEntity.setApplId(Long.parseLong(getId()));
					manageAppService.update(appEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					manageAppService.save(appEntity);
				}
				status = SUCCESS;
			}
			
		}

		catch (Exception e) {
			exHandler.logErrors(e);
			status = ERROR;
			throw new ApplicationServiceException(e.getMessage(),e.getMessage(),e) ;
		}
		
		return status;

	}
}
