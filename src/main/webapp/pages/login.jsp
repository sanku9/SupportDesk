<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><s:property value="getText('App.Name')" /></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" id="favicon" type="image/png" href="images/favicon.ico" >
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" media="all">	
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<c:url value="/"/>"><s:property value="getText('App.Name')" /></a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li><a id="signuplink" href="<c:url value="/SignUp"/>"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;Sign Up</a><a id="signinlink" href="login"
						 style="display: none;"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;Existing User</a></li>
			</ul>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row content">
			<s:if test="hasActionErrors()">
			   <div class="error" style="text-align:center">
			      <s:actionerror theme="bootstrap"/>
			   </div>
			</s:if>
			<s:if test="hasActionMessages()">
			   <div class="message" style="text-align:center">
			      <s:actionmessage theme="bootstrap"/>
			   </div>
			</s:if>
			<div id="msg" class="alert alert-danger" style="text-align:center;display:none"></div>
			<s:textfield type ="hidden" name="clickAction" id="action"/>
			<div id="loginbox"  class="col-sm-offset-3 col-sm-6">
				<h3 style="border-bottom: 1px solid #eee; padding-bottom: 9px">Sign
					In to Your Account</h3>
				<s:form validate="true" theme="simple" method="post" action="Login">
					
					<div class="form-group">
						<label for="username">Username:</label>
						<s:textfield name="username"  cssClass="form-control required alphaNumeric(_.) alphaNumeric sameWidth" 
									 id="username" autocomplete="off" placeholder="User Name"></s:textfield>
					</div>
					<div class="form-group">
						<label for="password">Password:</label>
						<s:password name="password"  cssClass="form-control required alphaNumeric(_.@#$%&*) alphaNumeric sameWidth" 
									 id="password" autocomplete="off" placeholder="Password"></s:password>
					</div>
					
					<button type="submit" class="btn btn-primary">Sign In</button>
					<!-- <a href="forgotPassword" class="btn btn-primary">Forgot Password?</a> -->
				</s:form>
			</div>
			
			<div id="signupbox"  class="col-sm-offset-3 col-sm-6" style="display: none;">
				<h3 style="border-bottom: 1px solid #eee; padding-bottom: 9px">New User Sign-Up</h3>
				<form id="signupform" class="form-horizontal" role="form"  validate="true" theme="simple" method="post" action="AddUser">
					
					<div id="signupalert" style="display: none"
						class="alert alert-danger">
						<p>Error:</p>
						<span></span>
					</div>

					<div class="form-group">
						<label for="username" class="col-md-3 control-label">Username</label>
						<div class="col-md-9">
							<input id="usrname" type="text" class="form-control" name="username"
								placeholder="User Name">
						</div>
					</div>

					<div class="form-group">
						<label for="firstName" class="col-md-3 control-label">First
							Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="firstName"
								placeholder="First Name">
						</div>
					</div>
					<div class="form-group">
						<label for="lastName" class="col-md-3 control-label">Last
							Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="lastName"
								placeholder="Last Name">
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-md-3 control-label">Email</label>
						<div class="col-md-9">
							<input type="text" class="form-control" name="email"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-md-3 control-label">Password</label>
						<div class="col-md-9">
							<input id="passwd" type="password" class="form-control" name="password" maxLength="20" 
								placeholder="Password">
						</div>
					</div>

					<div class="form-group">
						<label for="confirmPassword" class="col-md-3 control-label">Re-Enter Password</label>
						<div class="col-md-9">
							<input id="confirmPasswd"  type="password" class="form-control" name="confirmPassword" maxLength="20" 
								placeholder="Confirm Password">
						</div>
					</div>

					<div class="form-group">
						<!-- Button -->
						<div class="col-md-offset-3 col-md-9">
							<button id="btn-signup" type="submit" class="btn btn-primary">
								<i class="icon-hand-right"></i> &nbsp; Sign Up
							</button>
						</div>
					</div>
				</form>
			</div> <!-- signup form End -->
		</div>
	</div>
	<script>
$(function () {
//alert($('#action').val());
	if ($('#action').val() == 'signUp') {
		$('#loginbox').hide(); 
		$('#signuplink').hide(); 
		$('#signinlink').show(); 
		$('#signupbox').show();
	} else {
		$('#loginbox').show(); 
		$('#signuplink').show(); 
		$('#signinlink').hide(); 
		$('#signupbox').hide();
	}  
	
	$('#signupform').submit(function(evt) {
		//$('.alert').hide();
		if (!$('#usrname').val() || $.trim($('#usrname').val())=='') {
			evt.preventDefault();
			$('#msg').html("User Name cannot be blank");
			$('#msg').show();
		} else if (!$('#passwd').val() &&  !$('#confirmPasswd').val()) {
			evt.preventDefault();
			$('#msg').html("Password cannot be blank");
			$('#msg').show();
		} else if ($('#passwd').val() != $('#confirmPasswd').val()) {
			evt.preventDefault();
			$('#msg').html("Password does not match");
			$('#msg').show();
		}
	});
});
</script>
</body>
</html>
