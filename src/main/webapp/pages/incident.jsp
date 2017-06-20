<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="themes" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<div>
	<div id="dialog-form"></div>
	<div>
		<script>
			var dialog;

			function closeCommentDialog() {
				var $div = $('#comment');
				$div.prop('contenteditable', false);
				$div.removeClass('editable');
				$('#editableButton').removeClass('display');
				dialog.dialog("close");
			}

			function loadCommentInDialog(id) {
				$.post('loadComment', {
					entityId : id
				}, function(data) {
					dialog = $("#dialog-form").html(data).dialog(
							{
								autoOpen : false,
								height : 310,
								width: "60%",
								maxWidth: "768px",
								modal : true,
								title : "Comments: " + id,
								buttons : {},
								close : function() {
									closeCommentDialog();
								},
								open : function() {
									jQuery('.ui-widget-overlay').bind('click',
											function() {
												closeCommentDialog();
											});
								}
							});
					dialog.dialog('open');
				});
			}

			function displayButtons(cellvalue, options, rowObject) {
				var myLineBreak;
				/* if (rowObject['comments'] != null)
					myLineBreak = rowObject['comments'].replace(/\r\n|\r|\n/g,
							"<br></br>"); */
				var edit = "<a title='Update Comments' onclick=\"loadCommentInDialog('"
						+ options.rowId
						+ "');\"  ><i class='fa fa-comment-o fa-1x'></i></a>";
				return edit;
			}
		</script>
		<span style="font-size: x-large"><s:property
				value="getText('sub.header.incident')" /></span>
		<s:form action="IncidentSLMFileUpload" theme="simple"
			cssClass="form-inline pull-right" method="post"
			enctype="multipart/form-data">

			<span class="input-group form-inline "> <span
				class="input-group-btn"> <span
					class="btn btn-primary btn-file"> Browse&hellip; <s:file
							name="incidentFile" />
				</span>
			</span> <s:textfield readonly="false" cssClass="btn btn-default"
					placeholder="Upload Incidents Excel File" /> &nbsp; <s:submit
					id="fileSubmit" cssClass="btn btn-default" value="Upload" /> <s:hidden
					id="fileExtension" name="fileExtension" />
			</span>
		</s:form>
	</div>

	<div class="page-sub-header" style="margin: 0px 0px 20px 0px;"></div>
	<div class="panel panel-primary" style="position: relative;">
		<div class="panel-heading">Incident Details</div>
		<div class="panel-body" style="padding: 5px 1px">
			<div style="padding: 5px 0px;">
				<s:form id="searchFormId" theme="simple">
					<fieldset>
						<!-- <label for="stat">Incident Status</label> -->
						<s:select
							list="#{'-1':'--Select Status--', 'open':'Open', 'resolved':'Resolved', 'cancelled':'Cancelled'}"
							name="incStat" emptyOption="false" value="%{incStat}"
							cssClass="input-sm" />


						<!-- <label for="application">Application</label> -->
						<%-- <s:textfield name="appl" id="appl" placeholder="Application" cssClass="input-sm"/> --%>
						<s:select name="appl" list="appList" listKey="applId"
							listValue="applName" headerKey="-1"
							headerValue="--Select Application--" cssClass="input-sm" />
						<!-- <label for="incidentId">Incident Id</label> -->
						<s:textfield name="incId" id="incId" placeholder="Incident Id"
							cssClass="input-sm" />
						<!-- <label for="reportDate">Reported Date</label> -->
						<sj:datepicker id="reportDate" name="reportDate"
							label="Reported Date" cssClass="input-sm"
							placeholder="Reported Date" showOn="focus" changeMonth="true"
							changeYear="true" tooltip="Reported Date"
							inputAppendIcon="calendar" />

						<s:submit id="searchSubmit" cssClass="input-sm btn-primary"
							value="Search" indicator="indicator" />
					</fieldset>
				</s:form>
			</div>
			<s:url id="remoteUrl" action="incidentResult" />
			<s:url id="editUrl" action="incidentUpdate" />
			<sjg:grid id="IncResultGrid" dataType="json" href="%{remoteUrl}"
				pager="true" gridModel="gridModel" rowList="5,10,25,50,100"
				rowNum="10" rownumbers="false" autowidth="true" hoverrows="true"
				cssStyle="font-size:12px;" viewrecords="true" loadonce="false"
				gridview="true" formIds="searchFormId"
				reloadTopics="reloadIncidentGrid">

				<sjg:gridColumn name="incidentId" index="incidentId" key="true"
					title="%{getText('sjGrid.incidentid')}" align="left"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="incidentSummary" index="incidentSummary"
					title="%{getText('sjGrid.incidentsummary')}" align="left"
					resizable="true" sortable="true" width="7"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="incidentStatus" index="incidentStatus"
					title="%{getText('sjGrid.incidentstatus')}" align="left"
					resizable="true" sortable="true" width="2"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="priority" index="priority"
					title="%{getText('sjGrid.priority')}" align="left" resizable="true"
					sortable="true" width="2"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="assigneeName" index="assigneeName"
					title="%{getText('sjGrid.assigneeName')}" align="left"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="applicationId.applName"
					index="applicationId.applName"
					title="%{getText('sjGrid.applicationName')}" align="left"
					resizable="true" sortable="true" width="3"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="reportedDate" index="reportedDate"
					title="%{getText('sjGrid.assigneddate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}">
				</sjg:gridColumn>
				<sjg:gridColumn name="responseSlaDueDate" index="responseSlaDueDate"
					title="%{getText('sjGrid.responsedate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}" />
				<sjg:gridColumn name="resolveSlaDueDate" index="resolveSlaDueDate"
					title="%{getText('sjGrid.resolvedate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}" />
				<sjg:gridColumn name="action" index="action" title="Comments"
					align="center" resizable="true" sortable="true" width="2"
					cssStyle="font-family:Arial; font-size:8;"
					formatter="displayButtons" />
				<sjg:gridColumn name="ageing" index="ageing"
					title="%{getText('sjGrid.assigneeName')}" align="left"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
			</sjg:grid>
		</div>
	</div>
</div>
<script>
	$(document).on(
			'change',
			'.btn-file :file',
			function() {
				var input = $(this), numFiles = input.get(0).files ? input
						.get(0).files.length : 1, label = input.val().replace(
						/\\/g, '/').replace(/.*\//, '');
				input.trigger('fileselect', [ numFiles, label ]);
			});

	$(document)
			.ready(
					function() {
						var $grid = $("#IncResultGrid");

						$("#searchSubmit").click(function() {
							$.publish('reloadIncidentGrid');
							return false;
						});

						$('#fileSubmit').attr('disabled', 'disabled');

						$('.btn-file :file')
								.on(
										'fileselect',
										function(event, numFiles, label) {
											//alert(label);
											var varSplitResult = label
													.split(".");
											var fileExtension = varSplitResult[1];
											if (label == "") {
												alert('Please choose a file');
												return false;
											} else if (fileExtension != "xls"
													&& fileExtension != "xlsx") {
												alert('Invalid File type. Only excel documents are allowed');
												return false;
											} else {
												$('#fileExtension').val(
														fileExtension);
												;
												$('#fileSubmit').removeAttr(
														'disabled');
											}

											var input = $(this).parents(
													'.input-group').find(
													':text'), log = numFiles > 1 ? numFiles
													+ ' files selected'
													: label;
											//alert(log);
											if (input.length) {
												input.val(log);
											} else {
												if (log)
													alert(log);
											}

										});
					});

	
</script>
