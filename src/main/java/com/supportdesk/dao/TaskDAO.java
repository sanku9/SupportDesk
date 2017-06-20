package com.supportdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.TaskEntity;

@Repository
@SuppressWarnings("unchecked")
public class TaskDAO extends AbstractDAO {

	public TaskEntity getTaskById(String taskId) {
		Criteria criteria = getSession().createCriteria(TaskEntity.class);
		criteria.add(Restrictions.eq("taskId",taskId));
		return (TaskEntity) criteria.uniqueResult();
	}
	
	public void updateTask(TaskEntity task) {
		try {
			merge(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}/*
	
	public void udpateTaskId(TaskEntity task) {
		String hql="update tbl_task set task_id= :newId where task_id= :oldId ";
		Query query=getSession().createQuery(hql);
		query.setParameter("oldId", task.getTaskId());
		query.setParameter("newId", "TK"+task.getTaskId());
		query.executeUpdate();
	}*/

	public List<TaskEntity> fetchTaskList(String searchtext) {
		Criteria criteria = getSession().createCriteria(TaskEntity.class);
		criteria.addOrder(Order.desc("createdDate"));
		if (searchtext != null && !searchtext.isEmpty()) {
			Criterion c1 = Restrictions.ilike("task", searchtext, MatchMode.ANYWHERE);
			Criterion c2 = Restrictions.ilike("task", searchtext, MatchMode.ANYWHERE);
			
			LogicalExpression orExp = Restrictions.or(c1, c2);
			
			criteria.add(orExp);
		}
		return (List<TaskEntity>) criteria.list();
	}

	public void deleteTask(String taskId) {
		TaskEntity taskEntity = (TaskEntity)getSession().get(TaskEntity.class,taskId);
		getSession().delete(taskEntity);
	}

	public List<TaskEntity> getOpenTaskList() {
		Query query = getSession().createQuery("from TaskEntity task where task.status != :closed and task.status != :cancelled and task.status != :completed order by createdDate desc ");
		query.setParameter("closed", "Closed");
		query.setParameter("cancelled", "Cancelled");
		query.setParameter("completed", "Completed");
		return query.list();
	}
	
}
