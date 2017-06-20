package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.CommentEntity;

@Repository
@SuppressWarnings("unchecked")
public class CommentDAO extends AbstractDAO {

	public List<CommentEntity> getCommentById(String entityId) {
		Criteria criteria = getSession().createCriteria(CommentEntity.class);
		criteria.add(Restrictions.eq("entityId",entityId));
		criteria.addOrder(Order.desc("createdDate"));
		return (List<CommentEntity>) criteria.list();
	}
	
	public void updateCommentEntity(CommentEntity commentEntity) {
		merge(commentEntity);
	}
	
}
