package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.IncidentSummaryDAO;
import com.supportdesk.domain.IncidentSummary;
import com.supportdesk.domain.UserIncidentSummary;

@Service
public class IncidentSummaryService extends AbstractService {

	@Autowired
	IncidentSummaryDAO incidentDAO;
	
	public IncidentSummaryDAO getIncidentDAO() {
		return incidentDAO;
	}

	public void setIncidentDAO(IncidentSummaryDAO incidentDAO) {
		this.incidentDAO = incidentDAO;
	}

	@Transactional
	public List<IncidentSummary> getIncidentSummaryList(String cycle, String days, String ticketType) {
		return incidentDAO.getIncidentSummaryList(cycle, days, ticketType);
	}
	public Integer getIncidentCount(List<IncidentSummary> incidentList) {
		if (incidentList == null)
			return 0;
		return incidentList.size();
	}

	public List<IncidentSummary> getIncidents(List<IncidentSummary> incidentList,
			int fromIndex, int toIndex) {
		return incidentList.subList(fromIndex, toIndex);
	}
	
	@Transactional
	public List<IncidentSummary> getIncidentSummaryListByApp(String cycle, String id, String ticketType) {
		return incidentDAO.getIncidentSummaryListByApp(cycle, id, ticketType);
	}
	
	@Transactional
	public List<UserIncidentSummary> getUserIncidentSummaryList(String cycle, String days, String ticketType) {
		return incidentDAO.getUserIncidentSummaryList(cycle, days, ticketType);
	}
	public Integer getUserIncidentCount(List<UserIncidentSummary> incidentList) {
		if (incidentList == null)
			return 0;
		return incidentList.size();
	}

	public List<UserIncidentSummary> getUserIncidents(List<UserIncidentSummary> incidentList,
			int fromIndex, int toIndex) {
		return incidentList.subList(fromIndex, toIndex);
	}

	@Transactional
	public List<UserIncidentSummary> getUserIncidentSummaryListByApp(String cycle, String id, String ticketType) {
		return incidentDAO.getUserIncidentSummaryListByApp(cycle, id, ticketType);
	}

}
