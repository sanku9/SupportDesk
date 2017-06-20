<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<sj:head loadAtOnce="true" compressed="false" jquerytheme="custom"
	customBasepath="themes" loadFromGoogle="false" debug="false"
	ajaxcache="true" />
<style>
.editable {
	width: "60%";
	height: 150px;
	border: 1px solid #ccc;
	padding: 5px;
	resize: both;
	-moz-appearance: textfield-multiline;
	-webkit-appearance: textarea;
	overflow: auto;
	font: medium -moz-fixed;
	font: -webkit-small-control;
}

.display {
	display: none;
}

.ui-widget {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
}

.well {
	min-height: 10px;
	padding: 10px;
	margin-bottom: 5px;
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
<script>
	var dialog;

	$('#editableButton').click(function() {
		$(this).toggleClass('display');
		$('#addLabel').toggle();
		$('#commentDiv').show();
	})

	function closeCommentDialog() {
		$('#commentDiv').hide();
		$('#editableButton').removeClass('display');
		dialog.dialog("close");
	}
	
	function saveComment(){
		$('#comment').val($('#comments').getPreText());
		if ($('#comment').val().length < 1)
			return false;
		else {
			var url = $('#commentForm').attr("action");
			var posting = $.post(url, $("#commentForm").serialize());
		}
		closeCommentDialog();
		
	}
</script>
<div id="addLabel" style="margin-bottom: 10px;">
	<label>&nbsp;</label> <span id="editableButton"
		class="btn btn-primary btn-sm pull-right"
		style='cursor: pointer; margin-bottom: 5px;'> <i
		class="fa fa-plus-square"></i> Add
	</span>
</div>
<div id='commentDiv'>
	<form id="commentForm" action="saveComment">
		<s:hidden name="entityId" />
		<s:hidden name="comment" />
		<label>Add Comment:</label>
		<input type="hidden" name="userId" value="${sessionScope.USER.userId}">
		<div id="comments" contenteditable="true" class="editable"
			style="white-space: pre-wrap;"></div>
		<span class="btn btn-link pull-right" onclick="saveComment();">Save</span>
	</form>
	<br />
	<br />
</div>

<div >
	<s:iterator value="commentEntityList">
		<input type="hidden" name="commentId">
		<div class="well clearfix">
			<span class="pre"><s:property value="comment" /></span>
			<span class="pull-right" style="font-size: 10px;"><s:property
					value="createdDate" /></span>
		</div>
	</s:iterator>
</div>
<script>
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
	$('#commentDiv').hide();
</script>
