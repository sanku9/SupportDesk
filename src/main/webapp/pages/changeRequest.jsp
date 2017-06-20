<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="themes" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<div>
	<div id="dialog-form" title="Add/Update Comments"
		style="display: none;">
	</div>
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
				/* if (rowObject['woComment'] != null)
					myLineBreak = rowObject['woComment'].replace(/\r\n|\r|\n/g,
							"<br></br>"); */
				var edit = "<a title='Update Comments' onclick=\"loadCommentInDialog('"
						+ options.rowId
						+ "');\"  ><i class='fa fa-comment-o fa-1x'></i></a>";
				return edit;
			}
		</script>
	<div>
		<span style="font-size: x-large"><s:property
				value="getText('sub.header.changeRequest')" /></span>
		<s:form action="ChangeRequestFileUpload" theme="simple"
			cssClass="form-inline pull-right" method="post"
			enctype="multipart/form-data">

			<span class="input-group form-inline "> <span
				class="input-group-btn"> <span
					class="btn btn-primary btn-file"> Browse&hellip; <s:file
							name="changeRequestFile" />
				</span>
			</span> <s:textfield readonly="false" cssClass="btn btn-default"
					placeholder="Upload Change Req Excel" /> &nbsp; <s:submit
					id="fileSubmit" cssClass="btn btn-default" value="Upload" /> <s:hidden
					id="fileExtension" name="fileExtension" />
			</span>
		</s:form>
	</div>
	<div class="page-sub-header" style="margin: 0px 0px 20px 0px;"></div>
	<div class="panel panel-primary" style="position: relative;">
		<div class="panel-heading">Change Request Details</div>
		<div class="panel-body" style="padding: 5px 1px">
			<div style="padding: 5px 0px;">
				<s:form id="searchFormId" theme="simple">
					<fieldset>
						<!-- <label for="stat">Incident Status</label> -->
						<s:select
							list="#{'-1':'--Select Status--', 'open':'Open', 'Completed':'Completed', 'Cancelled':'Cancelled'}"
							name="crStatus" emptyOption="false" value="%{crStatus}"
							cssClass="input-sm" />


						<!-- <label for="application">Application</label> -->
						<%-- <s:textfield name="appl" id="appl" placeholder="Application" cssClass="input-sm"/> --%>
						<s:select name="appl" list="appList" listKey="applId"
							listValue="applName" headerKey="-1"
							headerValue="--Select Application--" cssClass="input-sm" />
						<!-- <label for="incidentId">Incident Id</label> -->
						<s:textfield name="changeId" id="changeId" placeholder="Change ID"
							cssClass="input-sm" />
						<!-- <label for="reportDate">Reported Date</label> -->
						<sj:datepicker id="reportDate" name="reportDate"
							label="Submit Date" cssClass="input-sm" placeholder="Submit Date"
							showOn="focus" changeMonth="true" changeYear="true"
							tooltip="Submit Date" inputAppendIcon="calendar" />

						<s:submit id="searchSubmit" cssClass="input-sm btn-primary"
							value="Search" indicator="indicator" />
					</fieldset>
				</s:form>
			</div>
			<s:url id="remoteUrl" action="changeRequestResult" />
			<sjg:grid id="changeRequestGrid" dataType="json" href="%{remoteUrl}"
				pager="true" gridModel="gridModel" rowList="5,10,25,50,100"
				rowNum="10" rownumbers="false" autowidth="true" hoverrows="true"
				cssStyle="font-size:12px;" viewrecords="true" loadonce="false"
				gridview="true" formIds="searchFormId"
				reloadTopics="reloadChangeRequestGrid">

				<sjg:gridColumn name="changeId" index="changeId" key="true"
					title="%{getText('sjGrid.changeId')}" align="left"
					resizable="true" sortable="true" width="5"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<!-- Added by LE362 for req 5 : Start -->
				<sjg:gridColumn name="summary" index="summary"
					title="%{getText('sjGrid.crsummary')}" align="left"
					resizable="true" sortable="true" width="7"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<!-- Added by LE362 for req 5 : End -->
				<sjg:gridColumn name="status" index="status"
					title="%{getText('sjGrid.crstatus')}" align="left" resizable="true"
					sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="priority" index="priority"
					title="%{getText('sjGrid.crpriority')}" align="left"
					resizable="true" sortable="true" width="2"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="applicationId.applName"
					index="applicationId.applName"
					title="%{getText('sjGrid.crapplicationName')}" align="left"
					resizable="true" sortable="true" width="3"
					cssStyle="font-family:Arial; font-size:8;">
				</sjg:gridColumn>
				<sjg:gridColumn name="submitDate" index="submitDate"
					title="%{getText('sjGrid.crsubmitdate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}">
				</sjg:gridColumn>
				<sjg:gridColumn name="completedDate" index="completedDate"
					title="%{getText('sjGrid.crcompleteddate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}">
				</sjg:gridColumn><%-- 
				<sjg:gridColumn name="nextTargetDate" index="nextTargetDate"
					title="%{getText('sjGrid.woNextdate')}" align="center"
					resizable="true" sortable="true" width="4"
					cssStyle="font-family:Arial; font-size:8;" formatter='date'
					formatoptions="{ srcformat: 'ISO8601Long', newformat: 'd/m/Y H:i:s'}">
				</sjg:gridColumn> --%>
				<sjg:gridColumn name="action" index="action" title="Comments"
					align="center" resizable="true" sortable="true" width="2"
					cssStyle="font-family:Arial; font-size:8;"
					formatter="displayButtons" />
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
						var isFirefox = typeof InstallTrigger !== 'undefined';
					    // At least Safari 3+: "[object HTMLElementConstructor]"
						var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
						    // Internet Explorer 6-11
						var isIE = /*@cc_on!@*/false || !!document.documentMode;
						    // Edge 20+
						var isEdge = !isIE && !!window.StyleMedia;
						    // Chrome 1+
						var isChrome = !!window.chrome && !!window.chrome.webstore;
						    // Blink engine detection
						var isBlink = (isChrome || isOpera) && !!window.CSS;
					    
						$.fn.getPreText = function () {
						    var ce = $("<pre />").html(this.html());
						    if (isChrome)
						      ce.find("div").replaceWith(function() { return "\n" + this.innerHTML; });
						    if (isEdge || isIE)
						      ce.find("p").replaceWith(function() { return this.innerHTML + "<br>"; });
						    if (isFirefox || isSafari || isEdge || isIE)
						      ce.find("br").replaceWith("\n");
						
						    return ce.text();
						};
						
						$("#searchSubmit").click(function() {
							$.publish('reloadChangeRequestGrid');
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
