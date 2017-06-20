<%@ taglib uri="/struts-tags" prefix="s" %>
<div >
Some Error Occured!!<br><br>

Error Name: <s:property value="exception"/><br><br>

Error Stack Trace: <br>
<s:property value="exceptionStack"/>
</div>
