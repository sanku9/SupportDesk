package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.TopicEntity;

@Repository
@SuppressWarnings("unchecked")
public class TopicDAO extends AbstractDAO {

	public TopicEntity getTopicById(Integer topicId) {
		Criteria criteria = getSession().createCriteria(TopicEntity.class);
		criteria.add(Restrictions.eq("topicId",topicId));
		return (TopicEntity) criteria.uniqueResult();
	}
	
	public void updateTopic(TopicEntity topic) {
		merge(topic);
	}

	public List<TopicEntity> fetchTopicList(String searchtext) {
		Criteria criteria = getSession().createCriteria(TopicEntity.class);
		criteria.addOrder(Order.desc("modifiedDate"));
		if (searchtext != null && !searchtext.isEmpty()) {
			Criterion c1 = Restrictions.ilike("details", searchtext, MatchMode.ANYWHERE);
			Criterion c2 = Restrictions.ilike("topic", searchtext, MatchMode.ANYWHERE);
			
			LogicalExpression orExp = Restrictions.or(c1, c2);
			
			criteria.add(orExp);
		}
		return (List<TopicEntity>) criteria.list();
	}

	public void deleteTopic(Integer topicId) {
		TopicEntity topicEntity = (TopicEntity)getSession().get(TopicEntity.class,topicId);
		getSession().delete(topicEntity);
	}
	
}
