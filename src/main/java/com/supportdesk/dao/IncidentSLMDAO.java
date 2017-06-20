package com.supportdesk.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.supportdesk.domain.IncidentSummary;
import com.supportdesk.entity.IncidentSlmEntity;

@Repository
@SuppressWarnings("unchecked")
public class IncidentSLMDAO extends AbstractDAO {
	
	String monthlySQL = "select stat.month, max(case status when 'Cancelled' then count end) as cancelled"
			+ ", max(case status when 'Closed' then count end) as Closed"
			+ ", max(case status when 'Open' then count end) as Open"
			+ ", max(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total, mon"
			+ "from (select to_char(submit_date, 'MONTH') as month, date_part('month', submit_date) mon, incident_status as status"
			+ ", count(*) over (partition by incident_status)  as count "
			+ "from tbl_incident  where submit_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' group by to_char(submit_date, 'MONTH'),"
			+ "date_part('month', submit_date),incident_status order by date_part('month', submit_date)"
			+ ") as stat group by month,  mon order by mon";
	
	String yearlySQL = "select yr as year"
			+ ", max(case status when 'Cancelled' then count end) as cancelled"
			+ ", max(case status when 'Closed' then count end) as Closed"
			+ ", max(case status when 'Open' then count end) as Open"
			+ ", max(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total"
			+ "from"
			+ "(select date_part('year', submit_date) as yr, incident_status as status, count(*) from tbl_incident"
			+ "group by date_part('year', submit_date),incident_status) as stat group by year"
			+ "order by year";
	
	String lastWeekSQL = "select date as date"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ ", sum(case status when 'Open' then count end) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total"
			+ "from (select submit_date::date date, incident_status as status, count(*) from tbl_incident "
			+ "where submit_date > current_date - interval '7 days'"
			+ "group by submit_date,incident_status) as stat group by date"
			+ "order by date";
	
	String lastWeekSQLByApp = "select date as date, app"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ ", sum(case status when 'Open' then count end) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total"
			+ "from (select submit_date::date date, appl_name app, incident_status as status, count(*) from tbl_incident "
			+ " inner join tbl_application on application_id = appl_id "
			+ "where submit_date > current_date - interval '7 days'"
			+ "group by submit_date,app,incident_status) as stat group by date,app"
			+ "order by date";
	
	String betweenDates = "select date as date"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ ", sum(case status when 'Open' then count end) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total"
			+ "from (select submit_date::date date, incident_status as status, count(*) from tbl_incident "
			+ "where submit_date >= :fromDate and submit_date <= :toDate"
			+ "group by submit_date,incident_status) as stat group by date"
			+ "order by date";
	
	String betweenDatesByApp = "select date as date, app"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ ", sum(case status when 'Open' then count end) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", sum(count) as Total"
			+ "from (select submit_date::date date, appl_name app, incident_status as status, count(*) from tbl_incident "
			+ " inner join tbl_application on application_id = appl_id "
			+ "where submit_date >= :fromDate and submit_date <= :toDate"
			+ "group by submit_date,app,incident_status) as stat group by date,app"
			+ "order by date";

	public List<IncidentSlmEntity> getIncidentList(String asc, String sortField, String searchParameter) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		
		
		Criteria criteria = getSession().createCriteria(IncidentSlmEntity.class);
		
		if (searchParameter != null) {
			String[] params = searchParameter.split(";");
			for (String s : params) {
				String[] param = s.split(":");
				if (param.length > 1 && !param[1].isEmpty() && !param[1].equalsIgnoreCase("-1")) {
					
					if (param[0].equalsIgnoreCase("reportedDate") && !param[1].isEmpty()) {
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
					if (param[0].equalsIgnoreCase("incidentStatus")) {
						if (param[1]!= null && param[1].equalsIgnoreCase("cancelled") )
							criteria.add(Restrictions.in(param[0],Arrays.asList("Cancelled")));
						else if (param[1]!= null && (param[1].equalsIgnoreCase("resolved") || param[1].equalsIgnoreCase("closed") ))
							criteria.add(Restrictions.in(param[0],Arrays.asList("Resolved","Closed")));
						else if (param[1]!= null && param[1].equalsIgnoreCase("open") )
							criteria.add(Restrictions.in(param[0],Arrays.asList("Assigned","In Progress","Work in Progress","Pending")));
					} 
					if (param[0].equalsIgnoreCase("incidentId")) 
					{
						criteria.add(Restrictions.eq(param[0],param[1]));
					}
				}
			}
		}		
		
		if (sortField == null || sortField.isEmpty()) {
			criteria.addOrder(Order.desc("reportedDate"));
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
		return (List<IncidentSlmEntity>) criteria.list();
	}
	
	public List<IncidentSlmEntity> getIncidentListByStatus(String incidentStatus) {
		Criteria criteria = getSession().createCriteria(IncidentSlmEntity.class);
		criteria.add(Restrictions.eq("incidentStatus",incidentStatus));
		return (List<IncidentSlmEntity>) criteria.list();
	}
	
	public List<IncidentSlmEntity> getOpenIncidentList() {
		Query query = getSession().createQuery("from IncidentSlmEntity inc where inc.incidentStatus != :closed and inc.incidentStatus != :cancelled and inc.incidentStatus != :resolved order by inc.reportedDate desc");
		query.setParameter("closed", "Closed");
		query.setParameter("cancelled", "Cancelled");
		query.setParameter("resolved", "Resolved");
		return query.list();
	}
	
	public void uploadIncidentSLM(List<IncidentSlmEntity> incidentList) {
		try {

			for (IncidentSlmEntity entity : incidentList) {
				merge(entity);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void updateIncidentComment(String commentId, String comments) {
		Query q = getSession().createQuery("update IncidentSlmEntity inc set inc.comments = :comments where inc.incidentId = :incidentId ");
		q.setParameter("comments", comments);
		q.setParameter("incidentId", commentId);
		q.executeUpdate();
	}
	
}
