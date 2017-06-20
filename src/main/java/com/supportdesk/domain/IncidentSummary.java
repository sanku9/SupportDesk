package com.supportdesk.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncidentSummary implements Comparable {

	String item;
	String  appname;
	Date date;
	String  month;
	Double  mon;
	Double  year;
	BigDecimal  inprogress = BigDecimal.ZERO;
	BigDecimal  pending = BigDecimal.ZERO;
	BigDecimal  cancelled = BigDecimal.ZERO;
	BigDecimal  closed = BigDecimal.ZERO;
	BigDecimal  open = BigDecimal.ZERO;
	BigDecimal  total = BigDecimal.ZERO;
	BigDecimal  slamissed = BigDecimal.ZERO;

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

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getInprogress() {
		return inprogress;
	}

	public void setInprogress(BigDecimal inprogress) {
		this.inprogress = inprogress;
	}

	public BigDecimal getSlamissed() {
		return slamissed;
	}

	public void setSlamissed(BigDecimal slaMissed) {
		this.slamissed = slaMissed;
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

	public BigDecimal  getPending() {
		return pending;
	}

	public void setPending(BigDecimal  pending) {
		this.pending = pending;
	}

	public BigDecimal  getCancelled() {
		return cancelled;
	}

	public void setCancelled(BigDecimal  cancelled) {
		this.cancelled =  cancelled;
	}

	public BigDecimal  getClosed() {
		return closed;
	}

	public void setClosed(BigDecimal  closed) {
		this.closed = closed;
	}

	public BigDecimal  getOpen() {
		return open;
	}

	public void setOpen(BigDecimal  open) {
		this.open = open;
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
