package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.UserService;

public class UserUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private UserEntity userEntity = new UserEntity();
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
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity user) {
		this.userEntity = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	UserService userService;
	
	public UserEntity getModel() {
		return userEntity;
	}
	
	public String updateUser() throws ApplicationServiceException {
		System.out.println("Entering UserUpdateAction.updateUser");

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				
				if(getOper().equalsIgnoreCase("del")) {
					if (userEntity.getUserId() == null) 
						userEntity.setUserId(Long.parseLong(getId()));
					userService.deleteUser(userEntity);
				} else if (getOper().equalsIgnoreCase("edit")) {
					if (userEntity.getUserId() == null) 
						userEntity.setUserId(Long.parseLong(getId()));
					userService.updateUser(userEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					userService.addUser(userEntity);
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
