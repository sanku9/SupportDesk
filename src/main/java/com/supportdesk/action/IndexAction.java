package com.supportdesk.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Constants;
import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.service.UserService;
import com.opensymphony.xwork2.Action;

public class IndexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserEntity userEntity = new UserEntity();
	private String action;
	
	private String clickAction;
	
	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	UserService userService;
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public UserEntity getModel() {
		return userEntity;
	}	
	
	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String login() throws ApplicationBaseException {
		return "dashboard";
	}
	

	
	public String changePassword() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String updatePassword() throws ApplicationBaseException {
		Map<String, Object> sessionMap = getSession();
		UserEntity userSesionEntity = (UserEntity) sessionMap.get(Constants.SESSION_KEY_USER);
		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		try{
			userEntity.setUserId(userSesionEntity.getUserId());
			userService.changePassword(userEntity);
			addActionMessage("Password Changed Successfully");
		}catch (Exception e) {
			exHandler.logErrors(e);
			addActionError("Error Occurred while adding new User. ");
			setClickAction("signUp");
			return Action.LOGIN;
		}
        return SUCCESS;
    }
	
	public String forgotPassword() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String resetPassword() throws ApplicationBaseException {
		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		try{
			
			userService.update(userEntity);
			
		}catch (Exception e) {
			exHandler.logErrors(e);
			addActionError("Error Occurred while adding new User. ");
			setClickAction("signUp");
			return Action.LOGIN;
		}
        return SUCCESS;
    }
}
