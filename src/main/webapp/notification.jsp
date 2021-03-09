<%-- 
    Document   : notification
    Created on : Jan 16, 2021, 11:52:42 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty notification}">
    <div class="alert alert-${notificationType} center mt-1" role="alert">
        <p style="margin-bottom: 0">${notification}</p>
    </div>
</c:if>