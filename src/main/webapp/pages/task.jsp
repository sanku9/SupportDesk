<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<style>
.kbase.table th.cell-width {
	width: 6em;
}

.kbase.table th.cell-width-2x {
	width: 12em;
}

.kbase.table th.cell-width-1x {
	width: 10em;
}

.kbase.table th.cell-width-5x {
	width: 30em;
}

.modal-header, .close {
	background-color: #4682B4;
	color: white !important;
	/* text-align: center; */
	font-size: 30px;
}

.modal-footer {
	background-color: #f9f9f9;
}

.modal-dialog {
	width: 60%; /* New width for default modal */
}

.well {
	min-height: 10px;
	padding: 5px;
	margin: 0px;
}

.update {
	color: #333;
	margin-right: 5px;
}

.remove {
	color: red;
	margin-left: 5px;
}
.addComment{
	color: gray;
	margin-right: 10px;
}
.alert {
	padding: 0 14px;
	margin-bottom: 0;
	display: inline-block;
}
.pre {
	white-space: pre-wrap; /* CSS 3 */
	white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	display: inline;
	padding: 0px;
	margin: 0px;
	font-size: 12px;
	color: #333;
	word-break: break-all;
	white-space: pre-wrap; 
	word-wrap: break-word;
}
</style>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="themes" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<script type="text/javascript">
	$(document).ready(
			function() {
				
				 
				 
				var valid='true';
				$('#saveTask').on(
						'click',
						function(e) {
							e.preventDefault();
							valid = true;
							var startDate  = $("#startDate").val(); //2013-09-5
							var endDate    = $("#endDate").val();
							if (!$('#task').val()) {
								valid = false;
								$('#modalMsg').html("Task cannot be blank");
								$('#modalMsg').show();
							} 
							
							if (!$('#status').val()) {
								valid = false;
								$('#modalMsg').html("Please select status.");
								$('#modalMsg').show();
							}
							
							if(new Date(endDate) < new Date(startDate))
							{
								valid = false;
								$('#modalMsg').html("Planned Start Date must be greater than or equal to Planned end Date");
								$('#modalMsg').show();
							}
							
							if(!valid)
								return;
							
							$.post('updateTask', $('#newTaskForm')
									.serialize(), function(data, status, xhr) {
								$('#newTaskModal').modal('hide');
								var $table = $('#table');
								$table.bootstrapTable('refresh');
								$('#taskId').val('');
								$('#task').val('');
								$('#startDate').val('');
								$('#endDate').val('');
								//$('#status').val('New');
								$('#assignee').val('');
								$('#effortHours').val('');
								$('#modalMsg').html('');
							});
						});
				 $('.create').click(function () {
					             showModal($(this).text());
					             $('#modalMsg').html('');
					         });
			});
</script>
<div id="dialog-form" title="Add/Update Comments"
		style="display: none;">
	</div>
<!-- Modal HTML -->
<div id="newTaskModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title">Create Task</h4>
			</div>
			<div class="modal-body">
				<span id="modalMsg" style="display:none"></span>
				<form id="newTaskForm" role="form">
					<div class="form-group">
						<label for="task" class="control-label">Task:</label> <input
							type="hidden" class="form-control" name="taskId" ><input
							type="text" class="form-control" name="task" id="task">
					</div>
				    <div class="form-group">
				    <label class="control-label" for="status">Status</label>
				    <s:select
							list="#{'New':'New', 'In Progress':'In Progress', 'Completed':'Completed', 'Cancelled':'Cancelled'}"
							name="status" emptyOption="false"
							cssClass=" form-control input-sm" />
					</div>
				    <div class="form-group">
						<label for="assignee" class="control-label">Assignee:</label>
						<input type="text" class="form-control" name="assignee" id="assignee">
					</div>
					<div class="form-group"> <!-- Date input -->
				        <label class="control-label" for="startDate">Planned Start Date</label>
				        <input class="form-control datepicker" id="startDate" name="startDate" placeholder="MM/DD/YYY" type="text"/>
				    </div>
				    <div class="form-group"> <!-- Date input -->
				        <label class="control-label" for="endDate">Planned End Date</label>
				        <input class="form-control datepicker" id="endDate" name="endDate" placeholder="MM/DD/YYY" type="text"/>
				    </div>
					<div class="form-group">
						<label for="effortHours" class="control-label">Effort Hours:</label>
						<input type="text" class="form-control" name="effortHours" id="effortHours">
					</div> 
					<!-- <div class="form-group">
						<label for="details" class="control-label">Comments:</label>
						<textarea class="form-control" rows='10' name="details" id="details"></textarea>
					</div> -->
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" id="saveTask">Submit</button>
			</div>
		</div>
	</div>
</div>
<span style="font-size: x-large"><s:property
		value="getText('sub.header.task')" /></span><span class="alertMsg pull-right" style="text-align: center"></span>
<!-- <button type="button"
	class="btn btn-default btn-2x pull-right  show-createtopic-modal"
	data-backdrop="static" data-toggle="modal" data-target="#newTaskModal"
	data-title="Create Topic">
	<i class="fa fa-plus-square"></i> New Topic
</button> -->
<div class="page-sub-header" style="margin: 0px 0px 20px 0px;"></div>

<div id="toolbar">
	<div class="form-inline" role="form">
		<div class="form-group">
			<input name="search" class="form-control" style="width: 30em;" type="text"
				placeholder="Search">
		</div>
		<button id="ok" type="submit" class="btn btn-primary">Search</button>
		<a class="create btn btn-primary" href="javascript:"><i class="fa fa-plus-square"></i> New Task</a>
	</div>
</div>
<table id="table" data-toggle="table" data-detail-view="false" data-toolbar-align="right"
	data-detail-formatter="detailFormatter" data-toolbar="#toolbar"
	data-query-params="queryParams" data-response-handler="responseHandler"
	data-url="loadTask"
	data-classes="kbase table table-sm table-no-bordered"
	data-striped="true" data-height="0" data-pagination="true">
	<thead>
		<tr>
			<th data-field="taskId" data-visible="false">TaskId</th>
			<th data-field="task" class="cell-width-5x">Task</th>
			<!-- <th data-field="details" data-visible="false">details</th> -->
			<th data-field="status" class="cell-width-1x"
				data-sortable="true">Status</th>
			<th data-field="startDate" class="cell-width-2x" data-formatter="dateFormatter" 
				data-sortable="true">Planned Start Date</th>
			<th data-field="endDate" class="cell-width-2x" data-formatter="dateFormatter" 
				data-sortable="true">Planned End Date</th>
			<!-- <th data-field="modifiedDate" class="cell-width-2x"
				data-sortable="true">Actual Start Date</th>
			<th data-field="modifiedDate" class="cell-width-2x"
				data-sortable="true">Actual End Date</th> -->
			<th data-field="assignee" class="cell-width-2x"
				data-sortable="true">Assignee</th>
			<th data-field="createdBy.fullName" class="cell-width-2x"
				data-sortable="true">Created By</th>
			<!-- <th data-field="createdDate" class="cell-width-2x"
				data-sortable="true">Created on</th> -->
			<th data-field="action" class="cell-width" data-align="center"
				data-formatter="actionFormatter" data-events="actionEvents">Action</th>
		</tr>
	</thead>
</table>
<script>
	var $modal = $('#newTaskModal'),
	$alert = $('.alertMsg').hide();
	var dialog;
	function closeCommentDialog() {
		var $div = $('#comment');
		$div.prop('contenteditable', false);
		$div.removeClass('editable');
		$('#editableButton').removeClass('display');
		dialog.dialog("close");
	}
	
	function detailFormatter(index, row) {
		var html = [],details, modDate, upduser;
		
		$.each(row, function(key, value) {
			if (key == 'details') {
				details = value;
			} else if (key == 'updateUserId'){
				updUser = value.fullName;
			} else if (key == 'modifiedDate'){
				modDate = value;
			}
		});
		html.push('<div class="well"><span class="pull-right">');
		html.push(updUser);
		html.push(', ' + modDate);
		html.push('</span><div  class="pre">'+details + '');
		html.push('</div>');
		html.push('</div>');
		return html.join('');
	}
	
	function dateFormatter(value){
		return value !=null ? value.split("T")[0]:"";
	}

	function actionFormatter(value) {
		return [
				'<a class="addComment" href="javascript:" title="Add/View Comment"><i class="glyphicon glyphicon-comment"></i></a>',
				'<a class="update" href="javascript:" title="Update Task"><i class="glyphicon glyphicon-edit"></i></a>',
				'<a class="remove" href="javascript:" title="Delete Task"><i class="glyphicon glyphicon-remove"></i></a>']
				.join('');
	}

	// update and delete events
	window.actionEvents = {
		'click .addComment' : function(e, value, row) {
			//showModal($(this).attr('title'), row);
			var id = row.taskId;
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
							title : "Comments: " + row.task,
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
		},
		'click .update' : function(e, value, row) {
			showModal($(this).attr('title'), row);
		},
		'click .remove' : function(e, value, row) {
			if (confirm('Are you sure to delete this item?')) {
				
				$.ajax({
					url : 'deleteTask?taskId='+row.taskId,
					type : 'delete',
					success : function() {
						$table.bootstrapTable('refresh');
						showAlert('Delete item successful!', 'success');
					},
					error : function() {
						showAlert('Delete item error!', 'danger');
					}
				})
			}
		}
	};

	function showModal(title, row) {
		row = row || {
			name : '',
			stargazers_count : 0,
			forks_count : 0,
			description : ''
		}; // default row value
		
		$modal.data('id', row.id);
		$modal.find('.modal-title').text(title);
		$modal.find('input').val('');
		$modal.find('select').val('New');
		$modal.find('textarea').val('');
		
		for ( var name in row) {
			$modal.find('select[name="' + name + '"]').val(row[name]);
			$modal.find('input[name="' + name + '"]').val(row[name]);
			$modal.find('textarea[name="' + name + '"]').val(row[name]);
		}
		$modal.modal('show');
	}

	function showAlert(title, type) {
		$alert.attr('class', 'alert alert-' + type || 'success').html(
				'<i class="glyphicon glyphicon-check"></i> ' + title).show();
		setTimeout(function() {
			$alert.hide();
		}, 3000);
	}
	var $table = $('#table'), $ok = $('#ok');

	$(function() {
		$('.datepicker').datepicker({
			format: 'dd/MM/yyyy'
        });
		
		$ok.click(function() {
			$table.bootstrapTable('refresh');
		});
	});

	function queryParams() {
		var params = {};
		$('#toolbar').find('input[name]').each(function() {
			params[$(this).attr('name')] = $(this).val();
		});
		return params;
	}

	function responseHandler(res) {
		/* return {
		    rows: res.data;
		    total: res.data.length
		} */
		return res.data;
	}
</script>
