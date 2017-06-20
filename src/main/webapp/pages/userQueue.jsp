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
			value="getText('header.userQueue')" /></span></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel">
	<div class="panel-body" style="padding: 0px;">
		<s:url id="selectQueueURL" action="queueListAll" />
		<s:url id="selectUserURL" action="userList" />
		<s:url id="remoteurl" action="userQueueList">
			<s:param name="userId" value="%{userId}" />
		</s:url>
		<s:url id="editurl" action="updateUserQueue">
			<s:param name="selectedUserId" value="%{userId}" />
		</s:url>

		<s:form id="userQueueForm" theme="simple">
			<%-- <s:textfield type="hidden" id="userId" name="userId"/> --%>
			<label for="userId">Select User:</label>
			<sj:select href="%{selectUserURL}" id="userId" name="userId"
				list="userList" listKey="userId" listValue="firstName"
				emptyOption="false" headerKey="-1" headerValue="-- Select User --"
				cssClass="ui-widget ui-widget-content ui-corner-all" />
		</s:form>

		<s:if test="%{userId > 0}">
			<sjg:grid id="UserQueueGrid" caption="User Queues" dataType="json"
				href="%{remoteurl}" pager="true" navigator="true"
				navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
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
				editurl="%{editurl}" editinline="false"
				onSelectRowTopics="rowselect"
				onEditInlineSuccessTopics="oneditsuccess" viewrecords="true"
				width="700" autowidth="true" shrinkToFit="true"
				cssStyle="font-size:12px;" gridview="true">
				<sjg:gridColumn name="userQueueId" frozen="true" key="true" index="userQueueId"
					title="User Queue Id" hidden="true" editable="false" sortable="false"/>				
				<sjg:gridColumn name="queueId.queueName" frozen="false" index="queueId.queueName"
					title="Queue Name" editable="true" edittype="select"
					surl="%{selectQueueURL}"
					editoptions="{ dataUrl : '%{selectQueueURL}' }" sortable="false"
					search="false" />


			</sjg:grid>
		</s:if>

	</div>
</div>
<script>
	$(document).ready(function() {
		$("#userId").change(function() {
			$('#userQueueForm').submit();
		});

	});
</script>
