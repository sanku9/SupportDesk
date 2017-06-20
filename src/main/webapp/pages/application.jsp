<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<s:url id="customTheme" value="/themes"></s:url>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="%{customTheme}" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<%-- div>

	<span class="sub-header"  style="margin: 5px 0px;">Manage User</span>
</div> --%>
<span class="sub-header"><span class="sub-header"><s:property
			value="getText('header.manageApp')" /></span></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel panel-default">
	<div class="panel-body" style="padding: 0px;">
		
		<s:url id="selectServiceLevelURL" action="classTypeListAll"/>
		<s:url id="remoteurl" action="appList" />
		<s:url id="editurl" action="updateApp" />
		<sjg:grid id="appGridId" caption="Support Applications"
			dataType="json" href="%{remoteurl}" pager="true" navigator="true"
			navigatorSearchOptions="{sopt:['eq','ne','lt','gt'],closeAfterSearch:true}"
			navigatorAddOptions="{height:280,reloadAfterSubmit:true,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterAdd:true}"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterEdit:true}"
			navigatorAdd="true" navigatorEdit="true" navigatorView="false"
			navigatorDelete="true" navigatorSearch="false"
			navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
			gridModel="gridModel" rowList="10,15,20" rowNum="15"
			editurl="%{editurl}" editinline="false" onSelectRowTopics="rowselect"
			onEditInlineSuccessTopics="oneditsuccess" viewrecords="true"
			width="700" autowidth="true" shrinkToFit="true">
			<sjg:gridColumn name="applId" frozen="true" index="applId" key="true" 
				title="App Id" hidden="true" editable="false" sortable="false"
				search="true" searchoptions="{sopt:['eq','ne','lt','gt']}" />
			<sjg:gridColumn name="applName" frozen="false" index="applName"
				title="App Name" editable="true" edittype="text" sortable="false"
				search="true" searchoptions="{sopt:['eq']}" />
			<sjg:gridColumn name="applType" frozen="false" index="applType"
				title="App type" editable="true" edittype="select" editoptions="{value:'GROUP1:GROUP1;GROUP2:GROUP2'}" sortable="true"
				search="true" searchoptions="{sopt:['eq']}" />
			<sjg:gridColumn name="applClassType.classType" frozen="false" index="applClassType.classType"  title="App Service Level" editable="true" edittype="select"  
					surl="%{selectServiceLevelURL}" 
					editoptions="{ dataUrl : '%{selectServiceLevelURL}' }" sortable="false" search="true" searchoptions="{sopt:['eq','ne','lt','gt']}"/>
		</sjg:grid>
	</div>
</div>
