package com.supportdesk.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.supportdesk.domain.IncidentSummary;
import com.supportdesk.domain.UserIncidentSummary;

@Repository
@SuppressWarnings("unchecked")
public class IncidentSummaryDAO extends AbstractDAO {
	
	String userIncident_weeklySQL = "select stat.week as date"
			+ ", coalesce(sum(count),0) as Total, assignee"
			+ " from (select date_trunc('week', reported_date) week"
			+ ", count(assignee_name)  as count"
			+ ", assignee_name as assignee "
			+ " from tbl_incident_slm  where reported_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months' group by week, assignee_name"
			+ " order by week"
			+ " ) as stat group by week, assignee order by week desc";
	
	String userIncident_monthlySQL = "select yr as year, stat.month"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon, assignee"
			+ " from (select date_part('year', reported_date) as yr, to_char(reported_date, 'MONTH') as month, date_part('month', reported_date) mon"
			+ ", count(assignee_name)  as count "
			+ ", assignee_name as assignee "
			+ " from tbl_incident_slm  where reported_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ "group by yr, month,"
			+ " mon, assignee_name"
			+ " order by yr,mon"
			+ " ) as stat group by yr, month,  mon, assignee order by yr desc, mon desc";
	
	String userIncident_yearlySQL = "select yr as year"
			+ ", sum(count) as total, assignee"
			+ " from"
			+ " (select date_part('year', reported_date) as yr"
			+ ", count(assignee_name), assignee_name as assignee"
			+ " from tbl_incident_slm"
			+ " group by yr,assignee_name) as stat group by year,assignee"
			+ " order by year desc";
	
	String userIncident_lastWeekSQL = "select date as date"
			+ ", coalesce(sum(count),0) as Total, assignee"
			+ " from (select cast(reported_date as date) date, "
			+ " count(assignee_name), assignee_name as assignee "
			+ " from tbl_incident_slm "
			+ " where reported_date > current_date - (:intval * interval '1 days')"
			+ " group by reported_date,assignee_name) as stat group by date, assignee"
			+ " order by date desc";
	
	String userIncident_lastWeekSQLByApp = "select date as date, app as appName"
			+ ", coalesce(sum(count),0) as Total"
			+ " from (select cast(reported_date as date) date, appl_name app, assignee_name as assignee, count(assignee_name)"
			+ " from tbl_incident_slm "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where reported_date > cast(:reportedDate as Date) and reported_date < cast(:reportedDate as Date) + interval '1 days'"
			+ " and assignee_name = :assignee "
			+ " group by reported_date,app,assignee) as stat group by date,app"
			+ " order by app";
	
	String userIncident_weeklySQLByApp = "select stat.week as date, app as appName"
			+ ", coalesce(sum(count),0) as Total"
			+ " from (select date_trunc('week', reported_date) week, appl_name app, assignee_name as assignee"
			+ ", count(assignee_name)  as count "
			+ " from tbl_incident_slm  "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_trunc('week', reported_date) = :selectedWeek "
			+ " and assignee_name = :assignee "
			+ "  group by week,app,assignee order by week"
			+ " ) as stat group by week, appName order by app";
	
	String userIncident_monthlySQLByApp = "select yr as year, stat.month, app as appName"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			+ " from (select date_part('year', reported_date) as yr, to_char(reported_date, 'MONTH') as month, date_part('month', reported_date) mon"
			+ ", appl_name app, assignee_name as assignee"
			+ ", count(assignee_name)  as count "
			+ " from tbl_incident_slm"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where RTRIM(to_char(reported_date, 'MONTH')) = :selectedMonth and date_part('year', reported_date) = :selectedYear"
			+ " and assignee_name = :assignee "
			+ " group by yr, month,mon,app,assignee order by yr, mon"
			+ " ) as stat group by yr, month, appName, mon order by app ";
	
	String userIncident_yearlySQLByApp = "select yr as year, app as appName"
			+ ", sum(count) as total"
			+ " from"
			+ " (select date_part('year', reported_date) as yr, appl_name app, assignee_name as assignee"
			+ ", count(assignee_name)"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_part('year',reported_date) = :selectedYear "
			+ " and assignee_name = :assignee "
			+ " group by yr,app,assignee) as stat "
			+ " group by year, appName"
			+ " order by app";
	
	String incident_weeklySQL = "select stat.week as date"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', reported_date) week, incident_status as status"
			+ ", count(incident_status)  as count "
			+ " ,sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla "
			+ " from tbl_incident_slm  where reported_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months' group by week"
			+ " ,incident_status order by week"
			+ " ) as stat group by week order by week desc";
	
	String incident_monthlySQL = "select yr as year, stat.month"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', reported_date) as yr, to_char(reported_date, 'MONTH') as month, date_part('month', reported_date) mon, incident_status as status"
			+ ", count(incident_status)  as count "
			+ " ,sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla "
			+ " from tbl_incident_slm  where reported_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ "group by yr, month,"
			+ " mon,incident_status order by yr,mon"
			+ " ) as stat group by yr, month,  mon order by yr desc, mon desc";
	
	String incident_yearlySQL = "select yr as year"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', reported_date) as yr, incident_status as status, count(incident_status)"
			+ ", sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla  from tbl_incident_slm"
			+ " group by yr,incident_status) as stat group by year"
			+ " order by year desc";
	
	String incident_lastWeekSQL = "select date as date"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned'  then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(reported_date as date) date, incident_status as status, count(incident_status)"
			+ ", sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla"
			+ " from tbl_incident_slm "
			+ " where reported_date > current_date - (:intval * interval '1 days')"
			+ " group by reported_date,incident_status) as stat group by date"
			+ " order by date desc";
	
	String incident_lastWeekSQLByApp = "select date as date, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned'  then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(reported_date as date) date, appl_name app, incident_status as status, count(incident_status)"
			+ ", sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla"
			+ " from tbl_incident_slm "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where reported_date > cast(:reportedDate as Date) and reported_date < cast(:reportedDate as Date) + interval '1 days'"
			+ " group by reported_date,app,incident_status) as stat group by date,app"
			+ " order by app";
	
	String incident_weeklySQLByApp = "select stat.week as date, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', reported_date) week, appl_name app, incident_status as status"
			+ ", count(incident_status)  as count "
			+ " ,sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla "
			+ " from tbl_incident_slm  "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_trunc('week', reported_date) = :selectedWeek "
			//+ " where date_trunc('week', reported_date) reported_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months'"
			+ "  group by week,app,incident_status order by week"
			+ " ) as stat group by week, appName order by app";
	
	String incident_monthlySQLByApp = "select yr as year, stat.month, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', reported_date) as yr, to_char(reported_date, 'MONTH') as month, date_part('month', reported_date) mon, appl_name app, incident_status as status"
			+ ", count(incident_status)  as count "
			+ " ,sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla "
			+ " from tbl_incident_slm"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where RTRIM(to_char(reported_date, 'MONTH')) = :selectedMonth and date_part('year', reported_date) = :selectedYear"
			//+ "  where reported_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ " group by yr, month,mon,app,incident_status order by yr, mon"
			+ " ) as stat group by yr, month, appName, mon order by app ";
	
	String incident_yearlySQLByApp = "select yr as year, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status='In Progress'  or status='Work in Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', reported_date) as yr, appl_name app, incident_status as status, count(incident_status)"
			+ ", sum(case when sla_breach_flag='Yes' then 1 else 0 end) sla  from tbl_incident_slm"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_part('year',reported_date) = :selectedYear "
			+ " group by yr,app,incident_status) as stat "
			+ " group by year, appName"
			+ " order by app";
	
	/*String betweenDates = "select date as date"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ " ,coalesce( max(case when status='Open' or status='In Progress' or status='Assigned'  then count end),0) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", coalesce(max(case status when 'In Progress' then count end),0) as inprogress"
			+ ", sum(count) as Total"
			+ " from (select submit_date::date date, incident_status as status, count(*) from tbl_incident "
			+ " where submit_date >= :fromDate and submit_date <= :toDate"
			+ " group by submit_date,incident_status) as stat group by date"
			+ " order by date";
	
	String betweenDatesByApp = "select date as date, app"
			+ ", sum(case status when 'Cancelled' then count end) as cancelled"
			+ ", sum(case status when 'Closed' then count end) as Closed"
			+ " ,coalesce( max(case when status='Open' or status='In Progress' or status='Assigned'  then count end),0) as Open"
			+ ", sum(case status when 'Pending' then count end) as Pending"
			+ ", coalesce(max(case status when 'In Progress' then count end),0) as inprogress"
			+ ", sum(count) as Total"
			+ " from (select submit_date::date date, appl_name app, incident_status as status, count(*) from tbl_incident "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where submit_date >= :fromDate and submit_date <= :toDate"
			+ " group by submit_date,app,incident_status) as stat group by date,app"
			+ " order by date";
*/
	
	String workOrder_weeklySQL = "select stat.week as date"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', submit_date) week, status as status"
			+ ", count(status)  as count "
			+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_work_order"
			+ "  where submit_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months' group by week"
			+ " ,status order by week"
			+ " ) as stat group by week order by week desc";
	
	String workOrder_monthlySQL = "select yr as year, stat.month"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', submit_date) as yr, to_char(submit_date, 'MONTH') as month, date_part('month', submit_date) mon, status as status"
			+ ", count(status)  as count "
			+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_work_order"
			+ "  where submit_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ "group by yr, month,"
			+ " mon,status order by yr,mon"
			+ " ) as stat group by yr, month,  mon order by yr desc, mon desc";
	
	String workOrder_yearlySQL = "select yr as year"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', submit_date) as yr, status as status, count(status)"
			+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ "  from tbl_work_order"
			+ " group by yr,status) as stat group by year"
			+ " order by year desc";
	
	String workOrder_lastWeekSQL = "select date as date"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(submit_date as date) date, status as status, count(status)"
			+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ " from tbl_work_order "
			+ " where submit_date > current_date - (:intval * interval '1 days')"
			+ " group by submit_date,status) as stat group by date"
			+ " order by date desc";
	
	String workOrder_lastWeekSQLByApp = "select date as date, app as appName"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(submit_date as date) date, appl_name app, status as status, count(status)"
			+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ " from tbl_work_order "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where submit_date > cast(:reportedDate as Date) and submit_date < cast(:reportedDate as Date) + interval '1 days'"
			+ " group by submit_date,app,status) as stat group by date,app"
			+ " order by app";
	
	String workOrder_weeklySQLByApp = "select stat.week as date, app as appName"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', submit_date) week, appl_name app, status as status"
			+ ", count(status)  as count "
			+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_work_order  "
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_trunc('week', submit_date) = :selectedWeek "
			//+ " where date_trunc('week', reported_date) reported_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months'"
			+ "  group by week,app,status order by week"
			+ " ) as stat group by week, appName order by app";
	
	String workOrder_monthlySQLByApp = "select yr as year, stat.month, app as appName"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', submit_date) as yr, to_char(submit_date, 'MONTH') as month, date_part('month', submit_date) mon"
			+ ", appl_name app, status as status"
			+ ", count(status)  as count "
			+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_work_order"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where RTRIM(to_char(submit_date, 'MONTH')) = :selectedMonth and date_part('year', submit_date) = :selectedYear"
			//+ "  where reported_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ " group by yr, month,mon,app,status order by yr, mon"
			+ " ) as stat group by yr, month, appName, mon order by app ";
	
	String workOrder_yearlySQLByApp = "select yr as year, app as appName"
			+ ", coalesce(sum(case status when 'CLosed Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed Successfull' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress'  or status='Work in Progress' or status='Assigned' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case when status = 'In Progress' or status = 'Work in Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', submit_date) as yr, appl_name app, status as status, count(status)"
			+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ "  from tbl_work_order"
			+ " inner join tbl_application on application_id = appl_id "
			+ " where date_part('year',submit_date) = :selectedYear "
			+ " group by yr,app,status) as stat "
			+ " group by year, appName"
			+ " order by app";
	
	//+++++++++=========== ChangeRequest =========+++++++//
	
	String changeRequest_weeklySQL = "select stat.week as date"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', submit_date) week, status as status"
			+ ", count(status)  as count "
			//+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_change_request"
			+ "  where submit_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months' group by week"
			+ " ,status order by week"
			+ " ) as stat group by week order by week desc";
	
	String changeRequest_monthlySQL = "select yr as year, stat.month"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', submit_date) as yr, to_char(submit_date, 'MONTH') as month, date_part('month', submit_date) mon, status as status"
			+ ", count(status)  as count "
			//+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_change_request"
			+ "  where submit_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ "group by yr, month,"
			+ " mon,status order by yr,mon"
			+ " ) as stat group by yr, month,  mon order by yr desc, mon desc";
	
	String changeRequest_yearlySQL = "select yr as year"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', submit_date) as yr, status as status, count(status)"
			//+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ "  from tbl_change_request"
			+ " group by yr,status) as stat group by year"
			+ " order by year desc";
	
	String changeRequest_lastWeekSQL = "select date as date"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Open' or status='In Progress' or status='Assigned'  then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(submit_date as date) date, status as status, count(status)"
			//+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ " from tbl_change_request "
			+ " where submit_date > current_date - (:intval * interval '1 days')"
			+ " group by submit_date,status) as stat group by date"
			+ " order by date desc";
	
	String changeRequest_lastWeekSQLByApp = "select date as date, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select cast(submit_date as date) date, appl_name app, status as status, count(status)"
			//+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ " from tbl_change_request "
			+ " left join tbl_application on appl_id = application_id "
			+ " where submit_date > cast(:reportedDate as Date) and submit_date < cast(:reportedDate as Date) + interval '1 days'"
			+ " group by submit_date,app,status) as stat group by date,app"
			+ " order by app";
	
	String changeRequest_weeklySQLByApp = "select stat.week as date, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_trunc('week', submit_date) week, appl_name app, status as status"
			+ ", count(status)  as count "
			//+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_change_request  "
			+ " left join tbl_application on appl_id= application_id "
			+ " where date_trunc('week', submit_date) = :selectedWeek "
			//+ " where date_trunc('week', reported_date) reported_date > date_trunc('week', CURRENT_DATE) - INTERVAL '3 months'"
			+ "  group by week,app,status order by week"
			+ " ) as stat group by week, appName order by app";
	
	String changeRequest_monthlySQLByApp = "select yr as year, stat.month, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as Closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as Pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", coalesce(sum(count),0) as Total"
			+ ", mon"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from (select date_part('year', submit_date) as yr, to_char(submit_date, 'MONTH') as month, date_part('month', submit_date) mon"
			+ ", appl_name app, status as status"
			+ ", count(status)  as count "
			//+ " ,sum(case when slm_status='Yes' then 1 else 0 end) sla "
			+ " from tbl_change_request"
			+ " left join tbl_application on appl_id = application_id "
			+ " where RTRIM(to_char(submit_date, 'MONTH')) = :selectedMonth and date_part('year', submit_date) = :selectedYear"
			//+ "  where reported_date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year' "
			+ " group by yr, month,mon,app,status order by yr, mon"
			+ " ) as stat group by yr, month, appName, mon order by app ";
	
	String changeRequest_yearlySQLByApp = "select yr as year, app as appName"
			+ ", coalesce(sum(case status when 'Cancelled' then count end),0) as cancelled"
			+ ", coalesce(sum(case status when 'Closed' then count end),0) as closed"
			+ " ,coalesce( sum(case when status='Draft' or status='Request For Authorization'  or status='Planning In Progress' or status='Scheduled For Review' then count end),0) as Open"
			+ ", coalesce(sum(case status when 'Pending' then count end),0) as pending"
			+ ", coalesce(sum(case status when 'In Progress' then count end),0) as inprogress"
			+ ", sum(count) as total"
			//+ ", coalesce(sum(sla),0) as slamissed"
			+ " from"
			+ " (select date_part('year', submit_date) as yr, appl_name app, status as status, count(status)"
			//+ ", sum(case when slm_status='Yes' then 1 else 0 end) sla"
			+ "  from tbl_change_request"
			+ " left join tbl_application on appl_id = application_id "
			+ " where date_part('year',submit_date) = :selectedYear "
			+ " group by yr,app,status) as stat "
			+ " group by year, appName"
			+ " order by app";
	
	Map<String, String> sqlQueryMap;
	
	public List<IncidentSummary> getIncidentSummaryList(String cycle, String days, String ticketType) {
		String sqlQuery = "";
		sqlQueryMap = new HashMap<String, String>();
		if (ticketType.equalsIgnoreCase("incident")) {
			sqlQueryMap.put("1", incident_lastWeekSQL);
			sqlQueryMap.put("2", incident_weeklySQL);
			sqlQueryMap.put("3", incident_monthlySQL);
			sqlQueryMap.put("4", incident_yearlySQL);
		} else if (ticketType.equalsIgnoreCase("workOrder")) {
			sqlQueryMap.put("1", workOrder_lastWeekSQL);
			sqlQueryMap.put("2", workOrder_weeklySQL);
			sqlQueryMap.put("3", workOrder_monthlySQL);
			sqlQueryMap.put("4", workOrder_yearlySQL);
		} else if (ticketType.equalsIgnoreCase("changeRequest")) {
			sqlQueryMap.put("1", changeRequest_lastWeekSQL);
			sqlQueryMap.put("2", changeRequest_weeklySQL);
			sqlQueryMap.put("3", changeRequest_monthlySQL);
			sqlQueryMap.put("4", changeRequest_yearlySQL);
		}
		
		sqlQuery = sqlQueryMap.get(cycle);
		
		Query query = getSession().createSQLQuery(sqlQuery);
		List list = null;
		try {
			if (cycle.equalsIgnoreCase("1")) { 
				query.setParameter("intval", Integer.parseInt(days) );
			}
			query.setResultTransformer(Transformers.aliasToBean(IncidentSummary.class));
			
			list = query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List<IncidentSummary> getIncidentSummaryListByApp(String cycle, String id, String ticketType) {
		String sqlQuery = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Calendar.getInstance().getTime();
		try {
			date = sdf.parse(id);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		sqlQueryMap = new HashMap<String, String>();
		
		if (ticketType.equalsIgnoreCase("incident")) {
			sqlQueryMap.put("1", incident_lastWeekSQLByApp);
			sqlQueryMap.put("2", incident_weeklySQLByApp);
			sqlQueryMap.put("3", incident_monthlySQLByApp);
			sqlQueryMap.put("4", incident_yearlySQLByApp);
		} else if (ticketType.equalsIgnoreCase("workOrder")) {
			sqlQueryMap.put("1", workOrder_lastWeekSQLByApp);
			sqlQueryMap.put("2", workOrder_weeklySQLByApp);
			sqlQueryMap.put("3", workOrder_monthlySQLByApp);
			sqlQueryMap.put("4", workOrder_yearlySQLByApp);
		} else if (ticketType.equalsIgnoreCase("changeRequest")) {
			sqlQueryMap.put("1", changeRequest_lastWeekSQLByApp);
			sqlQueryMap.put("2", changeRequest_weeklySQLByApp);
			sqlQueryMap.put("3", changeRequest_monthlySQLByApp);
			sqlQueryMap.put("4", changeRequest_yearlySQLByApp);
		}
		sqlQuery = sqlQueryMap.get(cycle);
		Query query = getSession().createSQLQuery(sqlQuery);
		List list = null;
		try {
			if (cycle.equalsIgnoreCase("1")) {
				query.setParameter("reportedDate", date );
			} else if (cycle.equalsIgnoreCase("2")) {
				query.setParameter("selectedWeek", date );
			} else if (cycle.equalsIgnoreCase("3")) {
				String str[] = id.split("-");
				query.setParameter("selectedMonth", str[0].trim());
				query.setParameter("selectedYear", Double.parseDouble(str[1].trim()));
			} else if (cycle.equalsIgnoreCase("4")) {
				query.setParameter("selectedYear", Double.parseDouble(id.substring(0, 4)));
			}
			
			query.setResultTransformer(Transformers.aliasToBean(IncidentSummary.class));
			
			list = query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List<UserIncidentSummary> getUserIncidentSummaryList(String cycle, String days, String ticketType) {
		String sqlQuery = "";
		sqlQueryMap = new HashMap<String, String>();
		if (ticketType.equalsIgnoreCase("incident")) {
			sqlQueryMap.put("1", userIncident_lastWeekSQL);
			sqlQueryMap.put("2", userIncident_weeklySQL);
			sqlQueryMap.put("3", userIncident_monthlySQL);
			sqlQueryMap.put("4", userIncident_yearlySQL);
		} else if (ticketType.equalsIgnoreCase("workOrder")) {
			sqlQueryMap.put("1", workOrder_lastWeekSQL);
			sqlQueryMap.put("2", workOrder_weeklySQL);
			sqlQueryMap.put("3", workOrder_monthlySQL);
			sqlQueryMap.put("4", workOrder_yearlySQL);
		} else if (ticketType.equalsIgnoreCase("changeRequest")) {
			sqlQueryMap.put("1", changeRequest_lastWeekSQL);
			sqlQueryMap.put("2", changeRequest_weeklySQL);
			sqlQueryMap.put("3", changeRequest_monthlySQL);
			sqlQueryMap.put("4", changeRequest_yearlySQL);
		}
		
		sqlQuery = sqlQueryMap.get(cycle);
		
		Query query = getSession().createSQLQuery(sqlQuery);
		List list = null;
		try {
			if (cycle.equalsIgnoreCase("1")) { 
				query.setParameter("intval", Integer.parseInt(days) );
			}
			query.setResultTransformer(Transformers.aliasToBean(UserIncidentSummary.class));
			
			list = query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List<UserIncidentSummary> getUserIncidentSummaryListByApp(String cycle, String index, String ticketType) {
		String sqlQuery = "";
		String[] ids = index.split("::");
		String id = ids[0];
		String assignee = ids[1];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Calendar.getInstance().getTime();
		try {
			date = sdf.parse(id);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		sqlQueryMap = new HashMap<String, String>();
		
		if (ticketType.equalsIgnoreCase("incident")) {
			sqlQueryMap.put("1", userIncident_lastWeekSQLByApp);
			sqlQueryMap.put("2", userIncident_weeklySQLByApp);
			sqlQueryMap.put("3", userIncident_monthlySQLByApp);
			sqlQueryMap.put("4", userIncident_yearlySQLByApp);
		} else if (ticketType.equalsIgnoreCase("workOrder")) {
			sqlQueryMap.put("1", workOrder_lastWeekSQLByApp);
			sqlQueryMap.put("2", workOrder_weeklySQLByApp);
			sqlQueryMap.put("3", workOrder_monthlySQLByApp);
			sqlQueryMap.put("4", workOrder_yearlySQLByApp);
		} else if (ticketType.equalsIgnoreCase("changeRequest")) {
			sqlQueryMap.put("1", changeRequest_lastWeekSQLByApp);
			sqlQueryMap.put("2", changeRequest_weeklySQLByApp);
			sqlQueryMap.put("3", changeRequest_monthlySQLByApp);
			sqlQueryMap.put("4", changeRequest_yearlySQLByApp);
		}
		sqlQuery = sqlQueryMap.get(cycle);
		Query query = getSession().createSQLQuery(sqlQuery);
		List list = null;
		try {
			if (cycle.equalsIgnoreCase("1")) {
				query.setParameter("reportedDate", date );
				query.setParameter("assignee", assignee );
			} else if (cycle.equalsIgnoreCase("2")) {
				query.setParameter("selectedWeek", date );
				query.setParameter("assignee", assignee );
			} else if (cycle.equalsIgnoreCase("3")) {
				String str[] = id.split("-");
				query.setParameter("selectedMonth", str[0].trim());
				query.setParameter("selectedYear", Double.parseDouble(str[1].trim()));
				query.setParameter("assignee", assignee );
			} else if (cycle.equalsIgnoreCase("4")) {
				query.setParameter("selectedYear", Double.parseDouble(id.substring(0, 4)));
				query.setParameter("assignee", assignee );
			}
			
			query.setResultTransformer(Transformers.aliasToBean(UserIncidentSummary.class));
			
			list = query.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
}
