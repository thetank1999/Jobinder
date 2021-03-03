<%-- 
    Document   : notification
    Created on : Jan 16, 2021, 11:52:42 PM
    Author     : Admin
--%>

<%@page import="utils.NotificationUtils"%>
<%
    String notification = (String) request.getAttribute(NotificationUtils.NOTIFICATION_KEY);
    String notificationType = (String) request.getAttribute(NotificationUtils.TYPE_KEY);
%>

<%if (notification != null) {%>
<div class="alert alert-<%=notificationType%> center mt-1" role="alert">
    <p style="margin-bottom: 0"><%=notification%></p>
</div>
<%}%>
