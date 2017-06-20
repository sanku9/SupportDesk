package com.supportdesk.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supportdesk.entity.ChangeRequestEntity;

@Repository
@SuppressWarnings("unchecked")
public class ChangeRequestDAO extends AbstractDAO {

	public List<ChangeRequestEntity> getChangeRequestList(String asc, String sortField, String searchParameter) {
		Criteria criteria = getSession().createCriteria(ChangeRequestEntity.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;	
		
		if (searchParameter != null) {
			String[] params = searchParameter.split(";");
			for (String s : params) {
				String[] param = s.split(":");
				if (param.length > 1 && !param[1].isEmpty() && !param[1].equalsIgnoreCase("-1")) {
					
					if (param[0].equalsIgnoreCase("submitDate") && !param[1].isEmpty()) {
						Calendar c = Calendar.getInstance();
						
						try {
							date = sdf.parse(param[1]);
							c.setTime(sdf.parse(param[1]));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						criteria.add(Restrictions.gt(param[0],date));
						c.add(Calendar.DATE, 1); 
						criteria.add(Restrictions.lt(param[0],c.getTime()));
					} 
					if (param[0].equalsIgnoreCase("applicationId")) {
						criteria.createAlias(param[0], param[0]+"Alias");
						criteria.add(Restrictions.eq(param[0]+"Alias.applId",Long.valueOf(param[1])));
					} 
					if (param[0].equalsIgnoreCase("status")) {
						if (param[1]!= null && param[1].equalsIgnoreCase("cancelled") )
							criteria.add(Restrictions.in(param[0],Arrays.asList("Cancelled")));
						else if (param[1]!= null && (param[1].equalsIgnoreCase("closed") || param[1].equalsIgnoreCase("completed") ))
							criteria.add(Restrictions.in(param[0],Arrays.asList("Closed","Completed")));
						else if (param[1]!= null && param[1].equalsIgnoreCase("open") )
							criteria.add(Restrictions.in(param[0],Arrays.asList("Draft","Request For Authorization","Planning In Progress","Pending","Scheduled For Review")));
					} 
					if (param[0].equalsIgnoreCase("changeId")) 
					{
						criteria.add(Restrictions.eq(param[0],param[1]));
					}
				}
			}
		}		
		
		if (sortField == null || sortField.isEmpty()) {
			criteria.addOrder(Order.desc("submitDate"));
		} else {
			if (sortField.contains(".")) {
				String[] str = sortField.split("\\.");
				System.out.println(str[0]+"Alias." + str[1]);
				criteria.createAlias(str[0], str[0]+"Alias");
				sortField = str[0]+"Alias." + str[1];
				System.out.println("sortField: "+sortField);
			}
			if (asc!= null && asc.equalsIgnoreCase("asc") ) {
				criteria.addOrder(Order.asc(sortField));
			} else {
				criteria.addOrder(Order.desc(sortField));
			}
		}
		return (List<ChangeRequestEntity>) criteria.list();
	}
	
	public List<ChangeRequestEntity> getChangeRequestListByStatus(String status) {
		Criteria criteria = getSession().createCriteria(ChangeRequestEntity.class);
		criteria.add(Restrictions.eq("status",status));
		return (List<ChangeRequestEntity>) criteria.list();
	}
	
	public List<ChangeRequestEntity> getOpenChangeRequestList() {
		Query query = getSession().createQuery("from ChangeRequestEntity cr where cr.status != :closed and cr.status != :cancelled and cr.status != :completed order by submitDate desc ");
		query.setParameter("closed", "Closed");
		query.setParameter("cancelled", "Cancelled");
		query.setParameter("completed", "Completed");
		return query.list();
	}

	public void uploadChangeRequests(List<ChangeRequestEntity> list) {

		try {
			for (ChangeRequestEntity entity : list) {
				merge(entity);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	public void updateChangeRequestComment(String commentId, String comments) {
		Query q = getSession().createQuery("update ChangeRequestEntity cr set cr.woComment = :comments where cr.workOrderId = :workOrderId ");
		q.setParameter("comments", comments);
		q.setParameter("workOrderId", commentId);
		q.executeUpdate();
	}
	
}
