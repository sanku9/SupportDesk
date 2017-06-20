package com.supportdesk.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserIncidentSummary implements Comparable {

	String itemAssignee;
	String assignee;
	String item;
	String  appname;
	Date date;
	String  month;
	Double  mon;
	Double  year;
	BigDecimal  total = BigDecimal.ZERO;

	public String getItem() {
		if (getDate() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(getDate());
		} else if (getMonth() != null)
			return getMonth().trim() + "-"+ String.valueOf(getYear().intValue());
		else if (getYear() != null) {
			return String.valueOf(getYear().intValue());
		}
		
		return "";
	}

	

	public String getItemAssignee() {
		return this.item + "::" + this.assignee;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String  getAppname() {
		return appname;
	}

	public void setAppname(String  appName) {
		this.appname = appName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String  getMonth() {
		return month;
	}

	public void setMonth(String  month) {
		this.month = month;
	}

	public Double getMon() {
		return mon;
	}

	public void setMon(Double mon) {
		this.mon = mon;
	}

	public Double  getYear() {
		return year;
	}

	public void setYear(Double  year) {
		this.year = year;
	}

	public BigDecimal  getTotal() {
		return total;
	}

	public void setTotal(BigDecimal  total) {
		this.total = total;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
