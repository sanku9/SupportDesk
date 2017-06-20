<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>

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
				$('#saveTopic').on(
						'click',
						function(e) {
							e.preventDefault();
							valid = true;
							if (!$('#topic').val()) {
								valid = false;
								$('#modalMsg').html("Topic title cannot be blank");
								$('#modalMsg').show();
							} 
							if(!valid)
								return;
							
							$.post('updateTopic', $('#newTopicForm')
									.serialize(), function(data, status, xhr) {
								$('#newTopicModal').modal('hide');
								var $table = $('#table');
								$table.bootstrapTable('refresh');
								$('#topic').val('');
								$('#details').val('');
								$('#modalMsg').html('');
							});
						});
				 $('.create').click(function () {
					 			$('#modalMsg').html('');
					             showModal($(this).text());
					         });
			});
</script>
<!-- Modal HTML -->
<div id="newTopicModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title">Create Topic</h4>
			</div>
			<div class="modal-body">
				<span id="modalMsg" style="display:none"></span>
				<form id="newTopicForm" role="form">
					<div class="form-group">
						<label for="topic" class="control-label">Topic:</label> <input
							type="hidden" class="form-control" name="topicId" ><input
							type="text" class="form-control" name="topic" id="topic">
					</div>
					<div class="form-group">
						<label for="details" class="control-label">Details:</label>
						<textarea class="form-control" rows='10' name="details" id="details"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary" id="saveTopic">Submit</button>
			</div>
		</div>
	</div>
</div>
<span style="font-size: x-large"><s:property
		value="getText('sub.header.knowledgeBase')" /></span><span class="alertMsg pull-right" style="text-align: center"></span>
<!-- <button type="button"
	class="btn btn-default btn-2x pull-right  show-createtopic-modal"
	data-backdrop="static" data-toggle="modal" data-target="#newTopicModal"
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
		<a class="create btn btn-primary" href="javascript:"><i class="fa fa-plus-square"></i> New Topic</a>
	</div>
</div>
<table id="table" data-toggle="table" data-detail-view="true" data-toolbar-align="right"
	data-detail-formatter="detailFormatter" data-toolbar="#toolbar"
	data-query-params="queryParams" data-response-handler="responseHandler"
	data-url="loadTopic"
	data-classes="kbase table table-sm table-no-bordered"
	data-striped="true" data-height="0" data-pagination="true">
	<thead>
		<tr>
			<th data-field="topicId" data-visible="false">TopicId</th>
			<th data-field="topic">Topic</th>
			<th data-field="details" data-visible="false">details</th>
			<th data-field="userId.fullName" class="cell-width-2x"
				data-sortable="true">Created By</th>
			<th data-field="createdDate" class="cell-width-2x"
				data-sortable="true">Created on</th>
			<th data-field="userId.fullName" class="cell-width-2x"
				data-sortable="true">Last Updated By</th>
			<th data-field="modifiedDate" class="cell-width-2x"
				data-sortable="true">Last Update</th>
			<th data-field="action" class="cell-width" data-align="center"
				data-formatter="actionFormatter" data-events="actionEvents">Action</th>
		</tr>
	</thead>
</table>
<script>
	var $modal = $('#newTopicModal'),
	$alert = $('.alertMsg').hide();

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

	function actionFormatter(value) {
		return [
				'<a class="update" href="javascript:" title="Update Topic"><i class="glyphicon glyphicon-edit"></i></a>',
				'<a class="remove" href="javascript:" title="Delete Topic"><i class="glyphicon glyphicon-remove"></i></a>', ]
				.join('');
	}

	// update and delete events
	window.actionEvents = {
		'click .update' : function(e, value, row) {
			showModal($(this).attr('title'), row);
		},
		'click .remove' : function(e, value, row) {
			if (confirm('Are you sure to delete this item?')) {
				
				$.ajax({
					url : 'deleteTopic?topicId='+row.topicId,
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
		for ( var name in row) {
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
