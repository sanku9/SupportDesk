package com.supportdesk.interceptors;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.supportdesk.action.LoginAction;
import com.supportdesk.common.Constants;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();
	private String clickAction;
		
	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	public String intercept(final ActionInvocation invocation) throws ApplicationBaseException, Exception {
		  LOGGER.debug("Entering SessionInterceptor.intercept");
		  final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		  Map<String,Object> sessionAttributes = null;
		  String invokeStr = "";
		  try{
			  sessionAttributes = invocation.getInvocationContext().getSession();
			  LOGGER.debug("session in intercepter is: " + sessionAttributes);
			  Object action = invocation.getAction ();
			  if (sessionAttributes == null
					  || sessionAttributes.get(Constants.SESSION_KEY_USER) == null
					  /*|| sessionAttributes.get("userName").equals("")*/) {
				  
				  if (action instanceof LoginAction) 
					  return invocation.invoke();
				  else
					  invokeStr = Action.LOGIN;
			  }else{
				  if (!(sessionAttributes.get(Constants.SESSION_KEY_USER)).equals(null)){
					  return invocation.invoke();
				  }else{
					  if (action instanceof LoginAction) 
						  return invocation.invoke();
					  else
						  invokeStr = Action.LOGIN;
				  }
			  }
		  }
		  catch (Exception ex) {
				exHandler.logErrors(ex);
				throw ex;
		  }
		  LOGGER.debug("Leaving SessionInterceptor.intercept");
		  return invokeStr;
	  }


}
