package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.SlaEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.SlaService;

public class SlaUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private SlaEntity slaEntity = new SlaEntity();
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
	public SlaEntity getSlaEntity() {
		return slaEntity;
	}

	public void setSlaEntity(SlaEntity appEntity) {
		this.slaEntity = appEntity;
	}

	@Autowired
	SlaService slaService;
	
	public SlaService getSlaService() {
		return slaService;
	}

	public void setSlaService(SlaService service) {
		this.slaService = service;
	}

	public SlaEntity getModel() {
		return slaEntity;
	}
	
	public String update() throws ApplicationServiceException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				
				if(getOper().equalsIgnoreCase("del")) {
					if (slaEntity.getSlaId() == null) 
						slaEntity.setSlaId(Long.parseLong(getId()));
					slaService.delete(slaEntity);
				} else if (getOper().equalsIgnoreCase("edit")) {	
					if (slaEntity.getSlaId() == null) 
						slaEntity.setSlaId(Long.parseLong(getId()));
					slaService.update(slaEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					slaService.save(slaEntity);
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
