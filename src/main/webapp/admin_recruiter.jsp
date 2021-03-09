<%-- 
    Document   : admin_recruiter.jsp
    Created on : Feb 13, 2021, 9:30:35 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/confirmDelete.js"></script>
        <title>Admin</title>
        <style>
            .table>:not(caption)>*>* {
                border-bottom-width: 0!important;
            }
            td {
                padding-top: 20px !important;
                padding-bottom: 20px !important;
            }
        </style>
        <script>
            function confirmDelete(form) {
                if (confirm("Are you sure you want to delete?")) {
                    form.submit();
                }
            }
        </script>
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <div>
            <%@include file="header.jsp" %>
            <%@include file="admin_panel.jsp" %>
            <div class="p-4">
                <c:choose>
                    <c:when test="${empty action}">
                        <%@include file="admin_jobs_tbl.jsp" %>
                    </c:when>
                    <c:when test="${action == 'applications'}">
                        <%@include file="admin_applications_tbl.jsp" %>
                    </c:when>
                    <c:when test="${action == 'account'}">
                        <%@include file="admin_account.jsp" %>
                    </c:when>
                    <c:when test="${action == 'help'}">
                        <%@include file="help.jsp" %>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>

