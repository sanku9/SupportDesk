package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.CommentDAO;
import com.supportdesk.entity.CommentEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.QueueEntity;

@Service
public class CommentService extends AbstractService {

	@Autowired
	CommentDAO commentDAO;

	@Transactional
	public List<CommentEntity> getCommentById(String entityId) {
		return commentDAO.getCommentById(entityId);
	}
	
	@Transactional
	public void updateCommentEntity(CommentEntity commentEntity) {
		commentDAO.updateCommentEntity(commentEntity);
	}

	@Transactional
	public void saveComment(CommentEntity commentEntity) {
		commentDAO.merge(commentEntity);
	}

}
