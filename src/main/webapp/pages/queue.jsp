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
<span class="sub-header" ><span class="sub-header" ><s:property value="getText('header.queue')" /></span></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel panel-default" >
	<div class="panel-body" style="padding: 0px;">
		<s:url id="remoteurl" action="queueList" />
		<s:url id="editurl" action="updateQueue" />
		
		<sjg:grid id="queueGrid" caption="Queues" dataType="json"
			href="%{remoteurl}" pager="true" navigator="true"
			navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
			navigatorAddOptions="{height:280,reloadAfterSubmit:true,closeAfterAdd:true}"
			navigatorEditOptions="{height:280,reloadAfterSubmit:false,closeAfterEdit:true}"
			navigatorAdd="true" navigatorEdit="true" navigatorView="false"
			navigatorDelete="true" navigatorSearch="false"
			navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
			gridModel="gridModel" rowList="10,15,20" rowNum="15"
			editurl="%{editurl}" editinline="false" onSelectRowTopics="rowselect"
			onEditInlineSuccessTopics="oneditsuccess" viewrecords="true"
			autowidth="true" shrinkToFit="true"
			cssStyle="font-size:12px;">
			<sjg:gridColumn name="queueId" frozen="true" key="true" index="queueId"
				title="Queue Id" hidden="true" editable="false" sortable="false"
				search="true" searchoptions="{sopt:['eq','ne','lt','gt']}" />
			<sjg:gridColumn name="queueName" frozen="false" index="queueName" title="Queue Name"
				editable="true" edittype="text" sortable="false" search="true"
				searchoptions="{sopt:['eq','ne','lt','gt']}" />
		</sjg:grid>
	</div>
</div>
