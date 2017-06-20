package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.supportdesk.common.Constants;
import com.supportdesk.entity.UserEntity;

@SuppressWarnings("unchecked")
@Repository
public class UserDAO extends AbstractDAO {
	
    private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void addUser(UserEntity user) throws Exception {
		try {
			user.setPassword(getPasswordEncoder().encode(Constants.DEFAULT_APP_PASSWORD));
			user.setIsAdmin(false);
			user.setIsActive(true);
			persist(user);
		} catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	public void update(UserEntity user) throws Exception{
		try {
			merge(user);
		} catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void changePassword(UserEntity user) throws Exception{
		try {
			UserEntity userEntity = (UserEntity) getSession()
					.get(UserEntity.class, user.getUserId());
			if (null != userEntity) {
				userEntity.setPassword(getPasswordEncoder().encode(user.getPassword()));
				merge(userEntity);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void resetPassword(UserEntity user) throws Exception{
		try {
			UserEntity userEntity = (UserEntity) getSession()
					.get(UserEntity.class, user.getUsername());
			if (null != userEntity) {
				userEntity.setPassword(getPasswordEncoder().encode(Constants.DEFAULT_APP_PASSWORD));
				merge(userEntity);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	//This method return list of employees in database
	public List<UserEntity> getAllUsers() {
		Criteria criteria = getSession().createCriteria(UserEntity.class);
		return (List<UserEntity>) criteria.list();
	}

	public void deleteUser(Long userId) {
		UserEntity user = (UserEntity) getSession()
				.load(UserEntity.class, userId);
		if (null != user) {
			delete(user);
		}
	}

	public void deleteUserByUserId(Long userId) {
		Query query = getSession().createQuery("delete from UserEntity where userId = :userId");
		query.setLong("userId", userId);
		query.executeUpdate();
	}

	public UserEntity getUserByUserName(String username) {
		/*		
		Query query = getSession().createQuery("from UserEntity usr where usr.username = :usrName ");
		query.setParameter(":usrName", username);
		List<UserEntity> userList= query.list();
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}*/

		Criteria criteria = getSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("username",username).ignoreCase());
		return (UserEntity) criteria.uniqueResult();

	}
	
	public UserEntity getUserByUserCredential(UserEntity user) {
		/*		
		Query query = getSession().createQuery("from UserEntity usr where usr.username = :usrName ");
		query.setParameter(":usrName", username);
		List<UserEntity> userList= query.list();
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}*/
		
		UserEntity userEntity =  getUserByUserName(user.getUsername());
		
		if (userEntity != null && userEntity.getIsActive() && getPasswordEncoder().matches(user.getPassword(), userEntity.getPassword())) {
			return userEntity;
		} else {
			return null;
		}
	}

	public void updateUser(UserEntity userEntity) {
		String updateQuery = "UPDATE UserEntity SET username=:username, first_name=:firstName, last_name=:lastName,email=:email, default_queue_id=:defaultQueueId, is_admin=:isAdmin, is_active=:isActive WHERE user_id = :userId";
		getSession().createQuery(updateQuery)
		.setParameter("firstName", userEntity.getFirstName())
		.setParameter("lastName", userEntity.getLastName())
		.setParameter("username", userEntity.getUsername())
		.setParameter("email", userEntity.getEmail())
		.setParameter("defaultQueueId", userEntity.getDefaultQueueId(),StandardBasicTypes.LONG)
		.setParameter("isAdmin", userEntity.getIsAdmin(), StandardBasicTypes.BOOLEAN)
		.setParameter("isActive", userEntity.getIsActive(), StandardBasicTypes.BOOLEAN)
		.setParameter("userId", userEntity.getUserId(),StandardBasicTypes.LONG)
		.executeUpdate();
		
	}
}
