<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" id="favicon" type="image/png"
	href="images/favicon.ico">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script
	src="<c:url value="/js/jquery.timepicker.js"/>"></script>
<%-- <sb:head/> --%>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<%-- <!-- Adding CSS files  -->
<tiles:insertAttribute name="commonCssFile" ignore="true" />
<tiles:insertAttribute name="pageSpecificCssFile" ignore="true" />
<!-- Adding Js files  -->
<tiles:insertAttribute name="commonJsFile" ignore="true" />
<tiles:insertAttribute name="pageSpecificJsFile" ignore="true" /> --%>
<%-- <sb:head/> --%>
<link href="<c:url value="/css/jquery.timepicker.css"/>" rel="stylesheet" type="text/css" media="all">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css" media="all">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="HandheldFriendly" content="true">
</head>
<body>
	<div class="wrapper">
		<jsp:include page="topHeader.jsp" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2 col-lg-2 sidebar" id="sidebar" style="padding-right: 0px;">
					<ul class="nav nav-stacked">
						<li class="nav-item"><a href="<c:url value="/Admin/ManageUser"/>"><s:property value="getText('leftmenu.user')" /></a></li>
						<li class="nav-item"><a href="<c:url value="/Admin/ManageQueue"/>"><s:property value="getText('leftmenu.queue')" /></a></li>
						<li class="nav-item"><a href="<c:url value="/Admin/ManageApp"/>"><s:property value="getText('leftmenu.app')" /></a></li>
						<li class="nav-item"><a href="<c:url value="/Admin/ManageUserQueue"/>"><s:property value="getText('leftmenu.userQueue')" /></a></li>						
						<li class="nav-item"><a href="<c:url value="/Admin/ManageClassType"/>"><s:property value="getText('leftmenu.serviceLevel')" /></a></li>
						<%-- <li class="nav-item"><a href="<c:url value="/Admin/SLA"/>"><s:property value="getText('leftMenu.sla')" /></a></li> --%>
					</ul>
				</div>
				<!--/col-->

				<div class="col-md-10 col-lg-10">
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
				
					<tiles:insertAttribute name="body" ignore="true" />
				</div>
			</div>
		</div>
		
	</div>
	<footer class="footer">
	<div class="container-fluid">
		<p class="text-muted"><s:property value="getText('app.footer')" /></p>
	</div>
	</footer>	
<script>
$(function () {
    setNavigation();
});

function setNavigation() {
	var ctx = "${pageContext.request.contextPath}"+"/";
    var path = window.location.pathname;
    path = path.replace(/\/+$/, "").replace(ctx,"");
    path = decodeURIComponent(path);

    $(".topnav li > a").each(function () {
        var href = $(this).attr('href').replace(ctx,"");
        //alert(path + " hello " +href);
        if (path.substring(0,href.length) === href) {
            $(this).closest('li').addClass('active');
        }
    });
    
    $(".row li > a").each(function () {
        var href = $(this).attr('href').replace(ctx,"");
        var start = path.indexOf("/")+1;
        var end = href.length + start;
        //alert(path + " hello " +href);
        if (path === href) { 
            $(this).closest('li').addClass('active');
        } 
    }); 
}
</script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
