package com.supportdesk.action;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.common.Constants;
import com.supportdesk.entity.TopicEntity;
import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.TopicService;

public class TopicUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(TopicUpdateAction.class);
	
	@Autowired
	TopicService topicService;
	
	TopicEntity topicEntity;
	
	private String search;
	
	private String topic;
	
	private Integer topicId;
	
	private String details;
	
	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	private List<TopicEntity> data;

	public List<TopicEntity> getData() {
		return data;
	}

	public void setData(List<TopicEntity> topicList) {
		this.data = topicList;
	}

	public TopicService getTopicService() {
		return topicService;
	}

	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	public TopicEntity getTopicEntity() {
		return topicEntity;
	}

	public void setTopicEntity(TopicEntity topicEntity) {
		this.topicEntity = topicEntity;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String loadTopic() throws ApplicationBaseException {
		data = topicService.fetchTopicList(getSearch());
        return SUCCESS;
    }

	public String updateTopic() {
		if(topicEntity == null)
			topicEntity= new TopicEntity();
		Map<String, Object> sessionMap = getSession();
		UserEntity userSesionEntity = (UserEntity) sessionMap.get(Constants.SESSION_KEY_USER);
		
		if (topicId == null) {
			topicEntity= new TopicEntity();		
			topicEntity.setUserId(userSesionEntity);
			topicEntity.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			topicEntity= topicService.getTopicById(topicId);
		}
		topicEntity.setUpdateUserId(userSesionEntity);
		topicEntity.setTopic(getTopic());	
		topicEntity.setDetails(getDetails());
		topicEntity.setModifiedDate(Calendar.getInstance().getTime());
		topicService.updateTopicEntity(topicEntity);
		return "success";
	}
	
	public String deleteTopic() {
		if(topicId == null)
			return "error";
		
		topicService.deleteTopicEntity(topicId);
		
		return "success";
	}
}
