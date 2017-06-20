<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="changePassword" class="col-sm-offset-3 col-sm-6">
	<h3 style="border-bottom: 1px solid #eee; padding-bottom: 9px">Change
		Password</h3>
	<s:form id="updatePassword" validate="true" theme="simple" method="post"
		action="updatePassword">

		<div class="form-group">
			<label for="password">New Password:</label>
			<s:password name="password"
				cssClass="form-control required alphaNumeric(_.@#$%&*) alphaNumeric sameWidth"
				id="password" autocomplete="off" placeholder="New Password"></s:password>
		</div>

		<div class="form-group">
			<label for="password">Re-Enter Password:</label>
			<s:password name="confirmPassword"
				cssClass="form-control required alphaNumeric(_.@#$%&*) alphaNumeric sameWidth"
				id="confirmPassword" autocomplete="off"
				placeholder="Confirm Password"></s:password>
		</div>

		<button type="submit" class="btn btn-primary">Change Password</button>
	</s:form>
</div>
<script>
	$(document).ready(function() {
		
		$('#updatePassword').submit(function(evt) {
			$('.alert').hide();
			if (!$('#password').val() &&  !$('#confirmPassword').val()) {
				evt.preventDefault();
				$('#msg').html("Password cannot be blank");
				$('#msg').show();
			} else if ($('#password').val() != $('#confirmPassword').val()) {
				evt.preventDefault();
				$('#msg').html("Password does not match");
				$('#msg').show();
			}
		});
	});
</script>
