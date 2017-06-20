package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.UserEntity;
import com.supportdesk.entity.UserQueueEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.UserQueueService;

public class UserQueueUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private UserQueueEntity userQueueEntity = new UserQueueEntity();
	private String oper;
	private String id;
	private Long selectedUserId;
	
	public Long getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(Long selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

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
	public UserQueueEntity getUserQueueEntity() {
		return userQueueEntity;
	}

	public void setUserQueueEntity(UserQueueEntity appEntity) {
		this.userQueueEntity = appEntity;
	}

	@Autowired
	UserQueueService userQueueService;
	
	public UserQueueService getUserQueueService() {
		return userQueueService;
	}

	public void setUserQueueService(UserQueueService service) {
		this.userQueueService = service;
	}

	public UserQueueEntity getModel() {
		return userQueueEntity;
	}
	
	public String update() throws ApplicationServiceException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				if(getSelectedUserId() != null)
					userQueueEntity.setUserId(new UserEntity(getSelectedUserId()));
				
				if(getOper().equalsIgnoreCase("del")) {
					if (userQueueEntity.getUserQueueId() == null) 
						userQueueEntity.setUserQueueId(Long.parseLong(getId()));
					userQueueService.delete(userQueueEntity);
				} else if (getOper().equalsIgnoreCase("edit")) {	
					if (userQueueEntity.getUserQueueId() == null) 
						userQueueEntity.setUserQueueId(Long.parseLong(getId()));
					userQueueService.update(userQueueEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					userQueueService.save(userQueueEntity);
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
