package com.supportdesk.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.QueueEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.QueueService;

public class QueueAction extends BaseAction {

	private static final long serialVersionUID = 1L;
		
	private QueueEntity queueEntity = new QueueEntity();
	private List<QueueEntity> queueList = new ArrayList<QueueEntity>();
	private String oper;

	@Autowired
	QueueService queueService;	
	
	protected List<QueueEntity>      gridModel;

	public List<QueueEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<QueueEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public QueueEntity getQueueEntity() {
		return queueEntity;
	}

	public void setQueueEntity(QueueEntity queueEntity) {
		this.queueEntity = queueEntity;
	}

	public List<QueueEntity> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<QueueEntity> queueList) {
		this.queueList = queueList;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public QueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(QueueService queueService) {
		this.queueService = queueService;
	}

	public String queueList() throws ApplicationServiceException {
		log.debug("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("Search :" + searchField + " " + searchOper + " "
				+ searchString);
		
		queueList = queueService.getQueueList();

		if (sord != null && sord.equalsIgnoreCase("asc")) {
			Collections.sort(queueList);
		}
		if (sord != null && sord.equalsIgnoreCase("desc")) {
			Collections.sort(queueList);
			Collections.reverse(queueList);
		}

		// Count all record (select count(*) from your_custumers)
		records = queueService.getRecordsCount(queueList);

		if (totalrows != null) {
			records = totalrows;
		}

		// Calucalate until rows ware selected
		int to = (rows * page);

		// Calculate the first row to read
		int from = to - rows;

		// Set to = max rows
		if (to > records)
			to = records;

		if (loadonce) {
			if (totalrows != null && totalrows > 0) {
				setGridModel(queueList.subList(0, totalrows));
			} else {
				// All Users
				setGridModel(queueList);
			}
		} else {
			// Search Users
			/*
			 * if (searchString != null && searchOper != null) { int id =
			 * Integer.parseInt(searchString); if
			 * (searchOper.equalsIgnoreCase("eq")) {
			 * log.debug("search id equals " + id); List<ApplicationNameVO>
			 * cList = new ArrayList<ApplicationNameVO>(); ApplicationNameVO
			 * ApplicationNameVO =
			 * ApplicationNameVODAO.findById(userList, id);
			 * 
			 * if (ApplicationNameVO != null) cList.add(ApplicationNameVO);
			 * 
			 * setGridModel(cList); } else if
			 * (searchOper.equalsIgnoreCase("ne")) { log.debug("search id not "
			 * + id);
			 * setGridModel(ApplicationNameVODAO.findNotById(userList
			 * , id, from, to)); } else if (searchOper.equalsIgnoreCase("lt")) {
			 * log.debug("search id lesser then " + id);
			 * setGridModel(ApplicationNameVODAO
			 * .findLesserAsId(userList, id, from, to)); } else if
			 * (searchOper.equalsIgnoreCase("gt")) {
			 * log.debug("search id greater then " + id);
			 * setGridModel(ApplicationNameVODAO
			 * .findGreaterAsId(userList, id, from, to)); } } else {
			 * setGridModel
			 * (ApplicationNameVODAO.getApplicationNameVOs(userList,
			 * from, to)); }
			 */
			setGridModel(queueService.getQueueLists(queueList,
					from, to));
		}

		// Calculate total Pages
		total = (int) Math.ceil((double) records / (double) rows);

		return SUCCESS;
	}
	
	public String queueListAll() throws ApplicationBaseException, Exception {
 		queueList = queueService.getQueueList();
		return SUCCESS;

 	}
}
