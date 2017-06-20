package com.supportdesk.action;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.CommentEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.CommentService;

public class CommentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(CommentAction.class);
	private String entityId;
	private Integer userId;
	private String comment;
	private CommentEntity commentEntity;
	private List<CommentEntity> commentEntityList;
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Autowired
	CommentService commentService;

	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public Object getModel() {
		return commentEntity;
	}
	
	public CommentEntity getCommentEntity() {
		return commentEntity;
	}
	public void setCommentEntity(CommentEntity commentEntity) {
		this.commentEntity = commentEntity;
	}
	
	public List<CommentEntity> getCommentEntityList() {
		return commentEntityList;
	}
	public void setCommentEntityList(List<CommentEntity> commentEntityList) {
		this.commentEntityList = commentEntityList;
	}
	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String loadComment() throws ApplicationBaseException {
		commentEntityList = commentService.getCommentById(entityId);
		return SUCCESS;
    }
	
	public String saveComment() throws ApplicationBaseException {
		if (commentEntity == null)
			commentEntity = new CommentEntity();
		commentEntity.setUserId(getUserId());
		commentEntity.setComment(getComment());
		commentEntity.setEntityId(getEntityId());
		commentEntity.setCreatedDate(Calendar.getInstance().getTime());
		commentService.saveComment(commentEntity);
		return SUCCESS;
    }
	
}
