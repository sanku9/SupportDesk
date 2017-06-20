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
<span class="sub-header" ><s:property value="getText('header.manageUser')" /></span>
<div class="page-sub-header" style="margin: 5px 0px;"></div>
<div class="panel panel-default" >
	<div class="panel-body" style="padding: 0px;">
		<s:url id="selectQueueURL" action="<%-- queueListByUser --%>" />
		<s:url id="remoteurl" action="userList" />
		<s:url id="editurl" action="updateUser" />
		
		<sjg:grid id="userGrid" caption="Users" dataType="json"
			href="%{remoteurl}" pager="true" navigator="true"
			navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
			navigatorAddOptions="{reloadAfterSubmit:true,beforeInitData:addBeforeShowForm,onClose:afterShowForm,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterAdd:true}"
			navigatorEditOptions="{reloadAfterSubmit:false,beforeInitData:addBeforeShowForm,onClose:afterShowForm,afterSubmit:function(response, postdata) {
                            return [true,'',''];
                         },closeAfterEdit:true}"
			navigatorAdd="true" navigatorEdit="true" navigatorView="false"
			navigatorDelete="true" navigatorSearch="false"
			navigatorDeleteOptions="{reloadAfterSubmit:true}"
			gridModel="gridModel" rowList="10,15,20" rowNum="15"
			editurl="%{editurl}" editinline="false" onSelectRowTopics="rowselect"
			onEditInlineSuccessTopics="oneditsuccess" viewrecords="true"
			autowidth="true" shrinkToFit="true"
			cssStyle="font-size:12px;">
			<sjg:gridColumn name="userId" frozen="true" key="true" index="userId"
				title="User Id" hidden="true" editable="false" sortable="false"
				search="true" searchoptions="{sopt:['eq','ne','lt','gt']}" />
			<sjg:gridColumn name="username" frozen="false" index="username" title="Login"
				editable="true" edittype="text" sortable="false" search="true"
				searchoptions="{sopt:['eq','ne','lt','gt']}" />
			<sjg:gridColumn name="firstName" frozen="false" index="firstName"
				title="First Name" editable="true" edittype="text" sortable="true"
				search="false" />
			<sjg:gridColumn name="lastName" frozen="false" index="lastName"
				title="Last Name" editable="true" edittype="text" sortable="true"
				search="false" />
			<sjg:gridColumn name="email" frozen="false" index="email"
				title="Email" editable="true" edittype="text" sortable="true"
				search="false" />
			<sjg:gridColumn name="isAdmin" index="isAdmin" title="Admin" formatter="select"
				editable="true" edittype="select" editoptions="{value:'true:Y;false:N'}"
				sortable="false" search="false" />
			<sjg:gridColumn name="isActive" index="isActive" title="Active" formatter="select"
				editable="true" edittype="select" editoptions="{value:'true:Y;false:N'}"
				sortable="false" search="false" />
			<sjg:gridColumn name="alstomId" frozen="false" index="alstomId"
				title="Client ID" editable="true" edittype="text" sortable="true"
				search="false" />
			<sjg:gridColumn name="d" frozen="false" index="d"
				title="ID" editable="true" edittype="text" sortable="true"
				search="false" />
			<sjg:gridColumn name="contact" frozen="false" index="contact"
				title="Phone" editable="true" edittype="text" sortable="true"
				search="false" />

		</sjg:grid>
	</div>
</div>
<script>
	$.subscribe('rowselect', function(event, data) {
		//var grid = event.originalEvent.grid;
		var grid = $("#userGrid");
		var sel_id = grid.jqGrid('getGridParam', 'selrow');
		var id = grid.jqGrid('getCell', sel_id, 'id');
		//alert('queueListByUser?id='+id);
		//alert(event.originalEvent.action);
		/*  var actionURL = '<s:property value="%{selectQueueURL}" />';
		 actionURL += '?id=' + id; */
		jQuery("#userGrid").jqGrid('setColProp', 'defaultQueueName', {
			editoptions : {
				dataUrl : 'queueListByUser?id=' + id
			}
		});
	});

	var addBeforeShowForm = function($form, oper) {
		if (oper == 'add') {
			$("#userGrid").jqGrid('setColProp', 'defaultQueueName', {
				editoptions : {
					dataUrl : 'queueListByUser?id=0'
				}
			});
			$("#userGrid").jqGrid('resetSelection');
		} else if (oper == 'edit') {
			var grid = $("#userGrid");
			var sel_id = grid.jqGrid('getGridParam', 'selrow');
			var id = grid.jqGrid('getCell', sel_id, 'id');
			$("#userGrid").jqGrid('setColProp', 'defaultQueueName', {
				editoptions : {
					dataUrl : 'queueListByUser?id=' + id
				}
			});
		}
	};

	var afterShowForm = function($form) {
		//$('#userGrid').trigger('rowselect');
		$("#userGrid").jqGrid('setColProp', 'defaultQueueName', {
			editoptions : {
				dataUrl : 'queueListByUser'
			}
		});
	};

	var editBeforeShowForm = function($form) {
		//$('#userGrid').trigger('rowselect');
	};
</script>
