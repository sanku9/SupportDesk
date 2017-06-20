package com.supportdesk.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.WorkOrderService;

public class ChangeRequestUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(ChangeRequestUpdateAction.class);
	
	@Autowired
	WorkOrderService workOrderService;
	
	private String commentId;	
	private String comments;	
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}


	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }

	public String updateWorkOrderComment() {
		workOrderService.updateWorkOrderComment(commentId, comments);
		addActionMessage("Comment Updated Successfully.");
		return "success";
	}
}
