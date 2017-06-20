<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<s:url id="customTheme" value="/themes" ></s:url>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="%{customTheme}" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<%-- div>

	<span class="sub-header"  style="margin: 5px 0px;">Manage User</span>
</div> --%>
<span class="sub-header" ><span class="sub-header" ><s:property value="getText('header.sla')" /></span></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel panel-default" >
	<div class="panel-body" style="padding: 0px;">
		
	<s:url id="selectServiceLevelURL" action="classTypeListAll"/>
	<s:url id="remoteurl" action="slaList"/>
    <s:url id="editurl" action="updateSla"/>	
    <sjg:grid
    	id="slaGridId"
    	caption="SLA Grid"
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
    	<sjg:gridColumn name="slaId" frozen="true" index="slaId" title="sla Id" key="true" formatter ="integer" hidden="true" editable="false" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="classTypeId.classType" frozen="false" index="classTypeId.classType"  title="Service Level" editable="true" edittype="select"  
					surl="%{selectServiceLevelURL}"
					editoptions="{ dataUrl : '%{selectServiceLevelURL}' }" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="priority" frozen="false" index="priority"  title="Priority" editable="true" edittype="select" editoptions="{value:{Low:'Low',Medium:'Medium',High:'High',Critical:'Critical'}}" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="resolveSlaHours" frozen="false" index="resolveSlaHours"  title="Resolve Sla Hours" editable="true" edittype="text" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="responseSlaHours" frozen="false" index="responseSlaHours"  title="Response Sla Hours" editable="true" edittype="text" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	<sjg:gridColumn name="supportHours" frozen="false" index="supportHours"  title="Business Hours" editable="true" edittype="text" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
    	
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
