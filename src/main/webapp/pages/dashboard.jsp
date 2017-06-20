<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="themes" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<style>
label {
	font-weight: normal;
	padding: 5px 5px;
}
</style>
<span style="font-size: xx-large"><s:property
		value="getText('sub.header.dashboard')" /></span>
<div class="page-sub-header" style="margin: 0px 0px 20px 0px;"></div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-blue">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-6">
						<i class="fa fa-list fa-3x"></i>
					</div>
					<div class="col-xs-6 text-right">
						<div class="huge">
							<s:property value="incidentOpenCount" />
						</div>
						<div>Open Incidents</div>
					</div>
				</div>
			</div>

			<div class="panel-footer">
				<a href="<c:url value="/Incident"/>"> <span class="pull-left">Show
						Details</span> <span class="pull-right"><i
						class="fa fa-arrow-circle-right"></i></span></a>
				<div class="clearfix"></div>
			</div>

		</div>
	</div>
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-blue">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-6">
						<i class="fa fa-support fa-3x"></i>
					</div>
					<div class="col-xs-6 text-right">
						<div class="huge">
							<s:property value="workOrderOpenCount" />
						</div>
						<div>Open Work Orders</div>
					</div>
				</div>
			</div>
			<a href="#">
				<div class="panel-footer">
					<a href="<c:url value="/WorkOrder"/>"> <span class="pull-left">Show
							Details</span> <span class="pull-right"><i
							class="fa fa-arrow-circle-right"></i></span></a>
					<div class="clearfix"></div>
				</div>
			</a>
		</div>
	</div>
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-blue">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-twitch fa-3x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<div class="huge">
							<s:property value="crOpenCount" />
						</div>
						<div>Open Change Requests</div>
					</div>
				</div>
			</div>
			<a href="#">
				<div class="panel-footer">
					<a href="<c:url value="/ChangeRequest"/>"> <span
						class="pull-left">Show Details</span> <span class="pull-right"><i
							class="fa fa-arrow-circle-right"></i></span></a>
					<div class="clearfix"></div>
				</div>
			</a>
		</div>
	</div>
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-blue">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-6">
						<i class="fa fa-tasks fa-3x"></i>
					</div>
					<div class="col-xs-6 text-right">
						<div class="huge">
							<s:property value="taskOpenCount" />
						</div>
						<div>Open Tasks</div>
					</div>
				</div>
			</div>
			<div class="panel-footer">
				<a href="<c:url value="/Task"/>"> <span class="pull-left">Show
						Details</span> <span class="pull-right"><i
						class="fa fa-arrow-circle-right"></i></span></a>
				<div class="clearfix"></div>
			</div>

		</div>
	</div>
</div>
<div id="dashBoardModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- Content will be loaded here from "a href"  -->
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$("input[name='cycle']").change(function() {
			var selected = $("input[name='cycle']:checked").val();
			//alert(selected);
			
			if (selected == '1'){
				$("select[name='days']").prop('disabled', false);
			} else {
				$("select[name='days']").prop('disabled', true);
			}
			$.publish('reloadIncidentGrid');
			$.publish('reloadIncidentUserGrid');
			return false;
		});
		
		$("select[name='days']").change(function() {
			$.publish('reloadIncidentGrid');
			$.publish('reloadIncidentUserGrid');
			return false;
		});
		
		$("#workOrderCycleForm input[name='cycle']").change(function() {
			var selected = $("#workOrderCycleForm input[name='cycle']:checked").val();
			//alert(selected);
			
			if (selected == '1'){
				$("#workOrderCycleForm select[name='days']").prop('disabled', false);
			} else {
				$("#workOrderCycleForm select[name='days']").prop('disabled', true);
			}
			$.publish('reloadWorkOrderGrid');
			return false;
		});
		
		$("#workOrderCycleForm select[name='days']").change(function() {
			$.publish('reloadWorkOrderGrid');
			return false;
		});
		
		$("#changeRequestCycleForm input[name='cycle']").change(function() {
			var selected = $("#changeRequestCycleForm input[name='cycle']:checked").val();
			//alert(selected);
			
			if (selected == '1'){
				$("#changeRequestCycleForm select[name='days']").prop('disabled', false);
			} else {
				$("#changeRequestCycleForm select[name='days']").prop('disabled', true);
			}
			$.publish('reloadChangeRequestGrid');
			return false;
		});
		
		$("#changeRequestCycleForm select[name='days']").change(function() {
			$.publish('reloadChangeRequestGrid');
			return false;
		});
	});
</script>

<div class="row" style="padding: 0px;">
	<div class="col-lg-12 col-md-12">

		<s:url id="remoteUrl" action="incidentSummaryResult" />
		<s:url id="incidentdetailsurl" action="incidentSummaryResultByApp" />
		<s:url id="woRemoteUrl" action="workOrderSummaryResult" />
		<s:url id="workorderdetailsurl" action="workOrderSummaryResultByApp" />
		<s:url id="crRemoteUrl" action="changeRequestSummaryResult" />
		<s:url id="changerequestdetailsurl"
			action="changeRequestSummaryResultByApp" />
			
		<s:url id="userIncidentCountUrl" action="userIncidentCount" />
		<s:url var="incidentUserCountByAppUrl" action="userIncidentCountByApp" />

		<ul class="nav nav-tabs" id="DashTab">
			<li class="active"><a href="#incidentTabContent"
				data-toggle="tab">Incident</a></li>
			<li><a href="#workOrderTabContent" data-toggle="tab">Service
					Request</a></li>
			<li><a href="#changeRequestTabContent" data-toggle="tab">Change
					Request</a></li>
		</ul>
		<div class="tab-content">
			<div id="incidentTabContent" class="tab-pane fade in active">
				<div class="panel panel-body" style="padding: 0px; width: '100%'">
					<div class="btn-group" data-toggle="buttons">
						<s:form id="incidentCycleForm" theme="simple">
							<s:radio id="radiobuttonset" tooltip="Choose Incident Cycle"
								labelposition="inline"
								list="#{'2':'Weekly','3':'Monthly','4':'Yearly','1':'Last '}"
								name="cycle" listCssStyle="color:red;" />
							<s:select list="#{'7':'7', '15':'15', '30':'30', '90':'90'}"
								name="days" cssStyle="background-color: #eee;" />
							<label> days</label>
						</s:form>
					</div>
					<sjg:grid id="IncSummaryResultGrid" dataType="json"
						href="%{remoteUrl}" pager="true" gridModel="gridModel"
						rowList="5,10,25,50,100" rowNum="10" rownumbers="false"
						autowidth="true" shrinkToFit="true" hoverrows="false"
						cssStyle="font-size:12px;" viewrecords="true" footerrow="true"
						listenTopics="submitCycle" userDataOnFooter="true" altRows="true"
						formIds="incidentCycleForm" altClass="myAltRowClassEven"
						reloadTopics="reloadIncidentGrid">
						<sjg:grid id="incidentsubgridtable"
							subGridUrl="%{incidentdetailsurl}" gridModel="gridModel"
							rowNum="10" formIds="incidentCycleForm" footerrow="true"
							autowidth="true" shrinkToFit="true" userDataOnFooter="true">
							<%-- <sjg:gridColumn name="item" title="item" width="100"/> --%>
							<sjg:gridColumn name="appname" title="App Name" width="150" />
							<sjg:gridColumn name="total" title="total" />
							<sjg:gridColumn name="open" title="Assigned/In-Progress" />
							<sjg:gridColumn name="pending" title="pending" />
							<sjg:gridColumn name="closed" title="closed" />
							<sjg:gridColumn name="cancelled" title="cancelled" />
							<sjg:gridColumn name="slamissed" title="slamissed" />
						</sjg:grid>
						<sjg:gridColumn name="item" index="item" title="Item" align="left"
							key="true" resizable="true" sortable="false" />

						<sjg:gridColumn name="total" index="total" title="Total Reported"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="open" index="open"
							title="Assigned/In-Progress" align="left" resizable="true"
							sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="pending" index="pending" title="Pending"
							align="left" resizable="true" sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="closed" index="closed" title="Closed"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="cancelled" index="cancelled"
							title="Cancelled" align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="slamissed" index="slamissed"
							title="SLA Missed" align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

					</sjg:grid>
					
					<div style="margin:10px 0px 10px 0px">
					User Based Information:
					</div>
					<sjg:grid id="userIncidentCountGrid" dataType="json"
						href="%{userIncidentCountUrl}" pager="true" gridModel="gridModelUser"
						rowList="5,10,25,50,100" rowNum="10" rownumbers="false"
						autowidth="false" shrinkToFit="true" hoverrows="false"
						cssStyle="font-size:12px;" viewrecords="true" footerrow="true"
						listenTopics="submitCycle" userDataOnFooter="true" altRows="true"
						formIds="incidentCycleForm" altClass="myAltRowClassEven"
						reloadTopics="reloadIncidentUserGrid"
						groupField="['item']"
				    	groupColumnShow="[false]" groupCollapse="false"
				    	groupText="['<b>{0} - {1} Support User(s)</b>']">
						<%-- <sjg:grid id="userIncidentSubGridTable"
							subGridUrl="%{incidentUserCountByAppUrl}" gridModel="gridModelUser"
							rowNum="10" formIds="incidentCycleForm" footerrow="true"
							autowidth="true" shrinkToFit="true" userDataOnFooter="true">
							<sjg:gridColumn name="appname" title="App Name" width="150" />
							<sjg:gridColumn name="total" title="total" />
						</sjg:grid> --%>
						<sjg:gridColumn name="itemAssignee" index="itemAssignee" title="Item & Assignee" align="left" hidden="true"
							 key="true" resizable="true" sortable="false" />
						
						<sjg:gridColumn name="item" index="item" title="Item" align="left"
							 resizable="true" sortable="false" />
							 	
						<sjg:gridColumn name="assignee" index="assignee" title="Assignee" align="left"
							  resizable="true" sortable="false" />

						<sjg:gridColumn name="total" index="total" title="Total Incident Count"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

					</sjg:grid>
				</div>
			</div>
			<div id="workOrderTabContent" class="tab-pane fade">
				<div class="panel panel-body" style="padding: 0px; width: '100%'">
					<div class="btn-group" data-toggle="buttons">
						<s:form id="workOrderCycleForm" theme="simple">
							<s:radio id="radiobuttonsetWorkOrder"
								tooltip="Choose Work Order Cycle" labelposition="inline"
								list="#{'2':'Weekly','3':'Monthly','4':'Yearly','1':'Last '}"
								name="cycle" listCssStyle="color:red;" />
							<s:select list="#{'7':'7', '15':'15', '30':'30', '90':'90'}"
								name="days" cssStyle="background-color: #eee;" />
							<label> days</label>
						</s:form>
					</div>
					<sjg:grid id="workOrderSummaryResultGrid" dataType="json"
						href="%{woRemoteUrl}" pager="true" gridModel="gridModel"
						rowList="5,10,25,50,100" rowNum="10" rownumbers="false"
						autowidth="true" shrinkToFit="true" hoverrows="false"
						cssStyle="font-size:12px;" viewrecords="true" footerrow="true"
						listenTopics="workOrderSubmitCycle" userDataOnFooter="true"
						altRows="true" formIds="workOrderCycleForm"
						altClass="myAltRowClassEven" reloadTopics="reloadWorkOrderGrid">
						<sjg:grid id="workordersubgridtable"
							subGridUrl="%{workorderdetailsurl}" gridModel="gridModel"
							rowNum="10" formIds="workOrderCycleForm" footerrow="true"
							autowidth="true" shrinkToFit="true" userDataOnFooter="true">
							<%-- <sjg:gridColumn name="item" title="item" width="100"/> --%>
							<sjg:gridColumn name="appname" title="App Name" width="150" />
							<sjg:gridColumn name="total" title="Total" />
							<sjg:gridColumn name="open" title="Assigned" />
							<sjg:gridColumn name="pending" title="Pending" />
							<sjg:gridColumn name="closed" title="Completed" />
							<sjg:gridColumn name="cancelled" title="Cancelled" />
							<sjg:gridColumn name="slamissed" title="Sla Missed" />
						</sjg:grid>
						<sjg:gridColumn name="item" index="item" title="Item" align="left"
							key="true" resizable="true" sortable="false" />

						<sjg:gridColumn name="total" index="total" title="Total Reported"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="open" index="open"
							title="Assigned/In-Progress" align="left" resizable="true"
							sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="pending" index="pending" title="Pending"
							align="left" resizable="true" sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="closed" index="closed" title="Completed"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="cancelled" index="cancelled"
							title="Cancelled" align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="slamissed" index="slamissed"
							title="SLA Missed" align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

					</sjg:grid>
				</div>
			</div>
			<div id="changeRequestTabContent" class="tab-pane fade">
				<div class="panel panel-body" style="padding: 0px; width: '100%'">
					<div class="btn-group" data-toggle="buttons">
						<s:form id="changeRequestCycleForm" theme="simple">
							<s:radio id="radiobuttonsetChangeRequest"
								tooltip="Choose Change Request Cycle" labelposition="inline"
								list="#{'2':'Weekly','3':'Monthly','4':'Yearly','1':'Last '}"
								name="cycle" listCssStyle="color:red;" />
							<s:select list="#{'7':'7', '15':'15', '30':'30', '90':'90'}"
								name="days" cssStyle="background-color: #eee;" />
							<label> days</label>
						</s:form>
					</div>
					<sjg:grid id="changeRequestSummaryResultGrid" dataType="json"
						href="%{crRemoteUrl}" pager="true" gridModel="gridModel"
						rowList="5,10,25,50,100" rowNum="10" rownumbers="false"
						autowidth="true" shrinkToFit="true" hoverrows="false"
						cssStyle="font-size:12px;" viewrecords="true" footerrow="true"
						listenTopics="changeRequestSubmitCycle" userDataOnFooter="true"
						altRows="true" formIds="changeRequestCycleForm"
						altClass="myAltRowClassEven"
						reloadTopics="reloadChangeRequestGrid">
						<sjg:grid id="changerequestsubgridtable"
							subGridUrl="%{changerequestdetailsurl}" gridModel="gridModel"
							rowNum="10" formIds="changeRequestCycleForm" footerrow="true"
							autowidth="true" shrinkToFit="true" userDataOnFooter="true">
							<%-- <sjg:gridColumn name="item" title="item" width="100"/> --%>
							<sjg:gridColumn name="appname" title="App Name" width="150" />
							<sjg:gridColumn name="total" title="Total" />
							<sjg:gridColumn name="open" title="Assigned" />
							<sjg:gridColumn name="pending" title="Pending" />
							<sjg:gridColumn name="closed" title="Completed" />
							<sjg:gridColumn name="cancelled" title="Cancelled" />
						</sjg:grid>
						<sjg:gridColumn name="item" index="item" title="Item" align="left"
							key="true" resizable="true" sortable="false" />

						<sjg:gridColumn name="total" index="total" title="Total Reported"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="open" index="open"
							title="Assigned/In-Progress" align="left" resizable="true"
							sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="pending" index="pending" title="Pending"
							align="left" resizable="true" sortable="false">
						</sjg:gridColumn>

						<sjg:gridColumn name="closed" index="closed" title="Completed"
							align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

						<sjg:gridColumn name="cancelled" index="cancelled"
							title="Cancelled" align="left" resizable="true" sortable="true">
						</sjg:gridColumn>

					</sjg:grid>
				</div>
			</div>


		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$("#DashTab li:eq(0) a").tab('show');

		$("#DashTab a").click(function(e) {
			e.preventDefault();
			$(this).tab('show');
		});

	});
</script>
