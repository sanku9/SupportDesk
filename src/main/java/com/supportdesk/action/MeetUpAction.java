package com.supportdesk.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.supportdesk.exception.ApplicationBaseException;

public class MeetUpAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public Object getModel() {
		return null;
	}
}
