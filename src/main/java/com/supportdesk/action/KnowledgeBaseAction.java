package com.supportdesk.action;

import com.supportdesk.exception.ApplicationBaseException;

public class KnowledgeBaseAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public Object getModel() {
		return null;
	}
}
