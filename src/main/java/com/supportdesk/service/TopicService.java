package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.TopicDAO;
import com.supportdesk.entity.TopicEntity;

@Service
public class TopicService extends AbstractService {

	@Autowired
	TopicDAO topicDAO;

	@Transactional
	public TopicEntity getTopicById(Integer topicId) {
		return topicDAO.getTopicById(topicId);
	}
	
	@Transactional
	public void updateTopicEntity(TopicEntity entity) {
		topicDAO.updateTopic(entity);
	}

	@Transactional
	public void saveTopic(TopicEntity entity) {
		topicDAO.merge(entity);
	}

	@Transactional
	public List<TopicEntity> fetchTopicList(String searchtext) {
		return topicDAO.fetchTopicList(searchtext);
		
	}

	@Transactional
	public void deleteTopicEntity(Integer topicId) {
		topicDAO.deleteTopic(topicId);
	}

}
