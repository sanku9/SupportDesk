<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span> 
			    </button>
				<a class="navbar-brand" href="<c:url value="/"/>"><s:property value="getText('App.Name')" /></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="topnav nav navbar-nav">
					<li id="DashBoard"><a href="<c:url value="/DashBoard"/>"><s:property value="getText('topMenu.DashBoard')" /></a></li>
					<li id="Incident"><a href="<c:url value="/Incident"/>"><s:property value="getText('topMenu.Incident')" /></a></li>
					<li id="SRequest"><a href="<c:url value="/WorkOrder"/>"><s:property value="getText('topMenu.ServiceRequest')" /></a></li>
					<li id="CRequest"><a href="<c:url value="/ChangeRequest"/>"><s:property value="getText('topMenu.ChangeRequest')" /></a></li>
					<li id="Task"><a href="<c:url value="/Task"/>"><s:property value="getText('topMenu.TaskManager')" /></a></li>
					<li id="KnowledgeBase"><a href="<c:url value="/KnowledgeBase"/>"><s:property value="getText('topMenu.KnowledgeBase')" /></a></li>
					<%-- <li id="MeetUp"><a href="<c:url value="/MeetUp"/>"><s:property value="getText('topMenu.MeetUp')" /></a></li> --%>
					<c:if test="${sessionScope.USER.isAdmin eq true}">
					 <li id="Admin" ><a href="<c:url value="/Admin"/>"><s:property value="getText('topMenu.Administration')" /></a></li>
					</c:if>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<%-- <li><span style="padding-top:15px;color: #9d9d9d;display:block;position: relative;">Welcome <c:out value="${sessionScope.USER.username }"/></span></li>
					<li><span style="padding:15px 0px 0px 15px;color: #9d9d9d;display:block;position: relative;">|</span></li>
					<li><a id="signuplink" href="logOut" onClick=""><span class="glyphicon glyphicon-user"></span>Log Out</a></li> --%>
					<li class="dropdown userdropdown">
						<a href="" class="dropdown-toggle"  data-toggle="dropdown">
							<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;<c:out value="${sessionScope.USER.fullName}" /> 
							<b class="caret"></b></a>
							<ul class="dropdown-menu">						
								<li><a href="<c:url value="/changePassword"/>"><span class="fa fa-sign-out"></span>Change Password</a></li>
								<li><a href="<c:url value="/logOut"/>"><span class="fa fa-sign-out"></span>Log Out</a></li>
							</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
