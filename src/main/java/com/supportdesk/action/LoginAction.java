package com.supportdesk.action;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Constants;
import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.service.UserService;
import com.opensymphony.xwork2.Action;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
		
	private UserEntity userEntity = new UserEntity();
	
	private String clickAction;
	
	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
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
	

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String login() throws ApplicationBaseException {
		return "dashboard";
	}
	
	public String signUp() throws ApplicationBaseException {
		setClickAction("signUp");
		return Action.LOGIN;
	}
	
	// Log Out user
	public String logOut() {
	        getSession().remove(Constants.SESSION_KEY_USER);
	        addActionMessage("You have been Successfully Logged Out");
	        return SUCCESS;
	}
	
	public String authenticate() throws ApplicationBaseException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		try{
		
			UserEntity user = userService.getUserByUserCredential(userEntity);
			if(user == null) {
				addActionError("Invalid UserName or Password");
				return Action.ERROR;
			}
			
			/*
			*  Setting values on Session;
			*/
			Map<String, Object> sessionMap = getSession();
			sessionMap.put(Constants.SESSION_KEY_USER, user);
			/*HttpSession session = request.getSession();
			session.setAttribute(Constants.SESSION_KEY_USER, userEntity);*/
			
		}catch (Exception e) {
			exHandler.logErrors(e);
			addActionError("Invalid UserName or Password");
			return Action.ERROR;
		}

        return SUCCESS;
    }
	
	public String addUser() throws ApplicationBaseException {
		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		try{
			
			userService.addUser(userEntity);
			Map<String, Object> sessionMap = getSession();
			sessionMap.put(Constants.SESSION_KEY_USER, userEntity);
			
		} catch (ConstraintViolationException e) {
			if (e.getSQLException() != null && e.getSQLException().getSQLState().equals("23505") ) {
				exHandler.logErrors(e);
				addActionError("User already Exists. ");
				setClickAction("signUp");
				return Action.LOGIN;
			}
		} catch (Exception e) {
			exHandler.logErrors(e);
			
			addActionError("Error Occurred while adding new User. ");
			setClickAction("signUp");
			return Action.LOGIN;
		}
        return SUCCESS;
    }
	
	public Object getModel() {
		return userEntity;
	}
}
