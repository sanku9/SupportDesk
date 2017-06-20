package com.supportdesk.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import com.supportdesk.exception.ApplicationBaseException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction extends ActionSupport implements ModelDriven, SessionAware {

	private static final long serialVersionUID = 1L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;

	protected String selectedMenu;
	protected String selectedSubMenu;

	protected static final Log    log              = LogFactory.getLog(BaseAction.class);

	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	protected Integer             rows             = 0;

	// Get the requested page. By default grid sets this to 1.
	protected Integer             page             = 0;

	// sorting order - asc or desc
	protected String              sord;

	// get index row - i.e. user click to sort.
	protected String              sidx;

	// Search Field
	protected String              searchField;

	// The Search String
	protected String              searchString;

	// Limit the result when using local data, value form attribute rowTotal
	protected Integer             totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	protected String              searchOper;

	// Your Total Pages
	protected Integer             total            = 0;

	// All Records
	protected Integer             records          = 0;

	protected boolean             loadonce         = false;

	public void setSession(Map<String, Object> map) {
		this.session = map;
	}

	public String getSelectedMenu() {
		return selectedMenu;
	}
	public void setSelectedMenu(String selectedMenu) {
		this.selectedMenu = selectedMenu;
	}
	public String getSelectedSubMenu() {
		return selectedSubMenu;
	}
	public void setSelectedSubMenu(String selectedSubMenu) {
		this.selectedSubMenu = selectedSubMenu;
	}
	/**
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getServletRequest(){
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}

	/**
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getServletResponse(){
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}

	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public String getJSON() throws ApplicationBaseException
	{
		return "success";
	}
}
