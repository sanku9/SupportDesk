<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<s:url id="customTheme" value="/themes" ></s:url>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="%{customTheme}" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<script
	src="<c:url value="/js/jquery.timepicker.js"/>"></script>	
<%-- div>

	<span class="sub-header"  style="margin: 5px 0px;">Manage User</span>
</div> --%>
<span class="sub-header" ><span class="sub-header" ><s:property value="getText('header.classType')" /></span></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel panel-default" >
	<div class="panel-body" style="padding: 0px;">
		
	<s:url id="remoteurl" action="classTypeList"/>
    <s:url id="editurl" action="updateClassType"/>	
    <sjg:grid
    	id="classTypeGridId"
    	caption="Class Type Grid"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAddOptions="{height:280,reloadAfterSubmit:true,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterAdd:true}"
    	navigatorEditOptions="{height:280,reloadAfterSubmit:true,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterEdit:true}"
    	navigatorAdd="true"
    	navigatorEdit="true"
    	navigatorView="false"
    	navigatorDelete="true"
    	navigatorSearch="false"
    	navigatorDeleteOptions="{height:280,reloadAfterSubmit:true,closeAfterEdit:true}"
    	gridModel="gridModel"
    	rowList="10,15,20"
    	rowNum="15"
    	editurl="%{editurl}"
    	editinline="false"
    	onSelectRowTopics="rowselect"
    	viewrecords="true"
    	autowidth="true" 
    	shrinkToFit="true"
    	cssStyle="font-size:12px;" 
    >
    	<sjg:gridColumn name="classId" frozen="true" index="classId" title="classId" key="true" formatter ="integer" hidden="true" editable="false" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="classType" frozen="false" index="classType"  title="Class Type" editable="true" edittype="text" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="serviceHourStartAsString" frozen="false" index="serviceHourStartAsString"  title="Business Hour Start" editable="true" 
    	editoptions = "{dataInit: function (element) {$(element).timepicker({ 'timeFormat': 'H:i:s' });}}"
    	 sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	 <sjg:gridColumn name="serviceHourEndAsString" frozen="false" index="serviceHourEndAsString"  title="Business Hour End" editable="true" 
    	editoptions = "{dataInit: function (element) {$(element).timepicker({ 'timeFormat': 'H:i:s' });}}" 
    	 sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	</sjg:grid>
	</div>
</div>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery.ajaxSetup({
			cache : false
		});
	});

	jQuery.subscribe('setCurrentGridIdNewUser', function(event, element) {
		setGridId("classTypeGridId");
	});

	/**To resolve grid issues on complete topic
	 **/
	jQuery.subscribe('setGridViewNewUser', function(event, element) {
		resolveGridIssues("classTypeGridId");
	});	
</script>
