package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.IncidentSLMDAO;
import com.supportdesk.dao.ManageAppDAO;
import com.supportdesk.dao.QueueDAO;
import com.supportdesk.dao.SlaDAO;
import com.supportdesk.entity.ApplicationEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.QueueEntity;

@Service
public class SnowTicketService extends AbstractService {

	@Autowired
	IncidentSLMDAO incidentSLMDAO;
	
	@Autowired
	ManageAppDAO 	applicationDAO;
	
	@Autowired
	QueueDAO  queueDAO;
	
	@Autowired
	SlaDAO 	slaDAO;

	public SlaDAO getSlaDAO() {
		return slaDAO;
	}

	public void setSlaDAO(SlaDAO slaDAO) {
		this.slaDAO = slaDAO;
	}
	
	@Transactional
	public List<ApplicationEntity> getApplicationList() {
		return applicationDAO.getList();
	}
	
	@Transactional
	public List<QueueEntity> getQueueList() {
		return queueDAO.getQueueList();
	}
	

	public Integer getIncidentCount(List<IncidentSlmEntity> incidentList) {
		if (incidentList == null)
			return 0;
		return incidentList.size();
	}

	public List<IncidentSlmEntity> getIncidents(List<IncidentSlmEntity> incidentList,
			int fromIndex, int toIndex) {
		return incidentList.subList(fromIndex, toIndex);
	}

	@Transactional
	public void uploadIncidentSLM(List<IncidentSlmEntity> parseIncList) {
		incidentSLMDAO.uploadIncidentSLM(parseIncList);
		
	}

	@Transactional
	public List<IncidentSlmEntity> getIncidentSLMList(String asc, String sortField, String searchParameter) {
		return incidentSLMDAO.getIncidentList(asc, sortField, searchParameter);
	}	

	@Transactional
	public List<IncidentSlmEntity> getOpenIncidentList() {
		return incidentSLMDAO.getOpenIncidentList();
	}
	
	@Transactional
	public void updateIncidentComment(String commentId, String comments) {
		incidentSLMDAO.updateIncidentComment(commentId, comments);
	}

}
