package com.supportdesk.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.UserEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.UserService;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
		
	private UserEntity userEntity = new UserEntity();
	private List<UserEntity> userList = new ArrayList<UserEntity>();
	private String oper;
	
	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity user) {
		this.userEntity = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	UserService userService;	

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}
	
	protected List<UserEntity>      gridModel;

	public List<UserEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<UserEntity> gridModel) {
		this.gridModel = gridModel;
	}
	
	/*public UserEntity getModel() {
		return userEntity;
	}*/

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	
	public String userList() throws ApplicationServiceException {
		log.debug("Page " + getPage() + " Rows " + getRows()
				+ " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("Search :" + searchField + " " + searchOper + " "
				+ searchString);
		
		userList = userService.getUserList();

		if (sord != null && sord.equalsIgnoreCase("asc")) {
			Collections.sort(userList);
		}
		if (sord != null && sord.equalsIgnoreCase("desc")) {
			Collections.sort(userList);
			Collections.reverse(userList);
		}

		// Count all record (select count(*) from your_custumers)
		records = userService.getRecordsCount(userList);

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
				setGridModel(userList.subList(0, totalrows));
			} else {
				// All Users
				setGridModel(userList);
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
			setGridModel(userService.getUserLists(userList,
					from, to));
		}

		// Calculate total Pages
		total = (int) Math.ceil((double) records / (double) rows);

		return SUCCESS;
	}
	
	public String userListAll() throws ApplicationBaseException, Exception {
 		userList = userService.getUserList();
		return SUCCESS;

 	}
}
