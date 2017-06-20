<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <sb:head/> --%>
<link rel="shortcut icon" id="favicon" type="image/png" href="images/favicon.ico" >
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> 
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
 <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.css">

	 <sj:head /> 
 <%-- <sb:head/> --%>	 
<link href="<c:url value="css/main.css"/>" rel="stylesheet" type="text/css" media="all">
<link href="<c:url value="themes/custom/jquery-ui.css"/>" rel="stylesheet" type="text/css" media="all">
<link href="<c:url value="themes/custom/jquery-ui.theme.css"/>" rel="stylesheet" type="text/css" media="all">		
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<%-- <!-- Adding CSS files  -->
<tiles:insertAttribute name="commonCssFile" ignore="true" />
<tiles:insertAttribute name="pageSpecificCssFile" ignore="true" />
<!-- Adding Js files  -->
<tiles:insertAttribute name="commonJsFile" ignore="true" />
<tiles:insertAttribute name="pageSpecificJsFile" ignore="true" /> --%>
<meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="HandheldFriendly" content="true">
</head>
<body>
<div class="wrapper">
	<jsp:include page="topHeader.jsp" />
	<div class="container-fluid">
		<div class="row content">
		<div class="col-md-12 col-lg-12">
		<!--    Page Body Content Started -->
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
		<div id="msg" class="alert alert-info" style="text-align:center;display:none">
	   </div>
		<tiles:insertAttribute name="body" ignore="true" />
		</div>
		<!--    Page Body Content End -->
		<!--    Page Footer Content Started -->
		<%-- <div id="footer">
			<!--    Page Footer Copyright -->
			<tiles:insertAttribute name="copyright" ignore="true" />
			<!--    Page Session Timeout -->
			<tiles:insertAttribute name="sessionTimeout" ignore="true" />
		</div> --%>
		<!--    Page Footer Content End -->
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
    path = path.replace(/\/+$/, "");
    path = decodeURIComponent(path);
    
    $(".topnav li > a").each(function () {
        var href = $(this).attr('href');
        //alert(path.substring(0, href.length)+" "+href);
        if (path.substring(0, href.length) == href) {
            $(this).closest('li').addClass('active');
        }
    });
}
</script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
</body>
</html>
