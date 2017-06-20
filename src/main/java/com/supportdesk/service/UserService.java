package com.supportdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supportdesk.dao.UserDAO;
import com.supportdesk.entity.UserEntity;

@Service
public class UserService extends AbstractService {
	
	@Autowired
	UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	public UserEntity getUserByUserName(String username) {
		if (username == null || username.trim().isEmpty())
			return null;
		return userDAO.getUserByUserName(username);
	}
	
	@Transactional
	public UserEntity getUserByUserCredential(UserEntity userEntity) {
		if (userEntity == null || userEntity.getUsername() == null || userEntity.getUsername().trim().isEmpty())
			return null;
		return userDAO.getUserByUserCredential(userEntity);
	}

	@Transactional
	public void addUser(UserEntity userEntity) throws Exception {
		userDAO.addUser(userEntity);
		
	}
	
	@Transactional
	public void update(UserEntity userEntity) throws Exception {
		userDAO.update(userEntity);
		
	}	

	@Transactional
	public void deleteUser(UserEntity userEntity) {
		userDAO.deleteUserByUserId(userEntity.getUserId());
		
	}

	public Integer getRecordsCount(List<UserEntity> userList) {
		if (userList == null)
			return 0;
		return userList.size();
	}

	@Transactional
	public List<UserEntity> getUserList() {
		return userDAO.getAllUsers();
	}

	public List<UserEntity> getUserLists(List<UserEntity> userList, int fromIndex,
			int toIndex) {
		return userList.subList(fromIndex, toIndex);
	}
	
	@Transactional
	public void updateUser(UserEntity userEntity) {
		userDAO.updateUser(userEntity);		
	}
	
	@Transactional
	public void changePassword(UserEntity userEntity) throws Exception {
		userDAO.changePassword(userEntity);
		
	}
}
