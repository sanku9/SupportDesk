package com.supportdesk.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.UserQueueEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.UserQueueService;

public class UserQueueAction extends BaseAction {

	private static final long serialVersionUID = 1L;
		
	private List<UserQueueEntity> list = new ArrayList<UserQueueEntity>();
	private String oper;
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public UserQueueService getUserQueueService() {
		return userQueueService;
	}

	public void setUserQueueService(UserQueueService userService) {
		this.userQueueService = userService;
	}

	@Autowired
	UserQueueService userQueueService;	

	public List<UserQueueEntity> getList() {
		return list;
	}

	public void setList(List<UserQueueEntity> list) {
		this.list = list;
	}
	
	protected List<UserQueueEntity>      gridModel;

	public List<UserQueueEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<UserQueueEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String userQueueList() throws ApplicationServiceException {
		log.debug("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("Search :" + searchField + " " + searchOper + " "
				+ searchString);
		
		list = userQueueService.getList(userId);

		if (sord != null && sord.equalsIgnoreCase("asc")) {
			Collections.sort(list);
		}
		if (sord != null && sord.equalsIgnoreCase("desc")) {
			Collections.sort(list);
			Collections.reverse(list);
		}

		// Count all record (select count(*) from your_custumers)
		records = userQueueService.getRecordsCount(list);

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
				setGridModel(list.subList(0, totalrows));
			} else {
				// All Users
				setGridModel(list);
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
			setGridModel(userQueueService.getList(list,
					from, to));
		}

		// Calculate total Pages
		total = (int) Math.ceil((double) records / (double) rows);

		return SUCCESS;
	}	
}
