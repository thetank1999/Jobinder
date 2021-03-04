<%-- 
    Document   : header
    Created on : Feb 1, 2021, 11:02:21 AM
    Author     : Admin
--%>

<%@page import="models.account.Account"%>
<%@page import="filters.AuthFilter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
    <a class="navbar-brand" href="./">Jobinder <img src="images/icon.png" width="30" height="30" alt=""></a>
    <ul class="navbar-nav navbar-collapse justify-content-end">
        <li><a id="jobs" href="jobs" class="nav-link mx-1">Việc làm</a></li>
        <li><a id="resumes" href="resumes" class="nav-link">Lý lịch</a></li>
            <%if (request.getSession().getAttribute(AuthFilter.SESSION_ACCOUNT_KEY) == null) {%>
        <li><a id="signin" href="signin" class="nav-link mx-1">Đăng nhập</a></li>
        <li><a id="signup" href="signup" class="nav-link">Đăng kí</a></li>
            <%} else {%>
        <li><a id="admin" href="admin" class="nav-link mx-1">Quản lý</a></li>
        <li><a href="signout" class="nav-link">Đăng xuất</a></li>
            <%}%>
    </ul>
    <script>
        const path = location.pathname;
        for (let route of ["jobs", "resumes", "signin", "signup", "admin"]) {
            if (path.endsWith(route)) {
                document.getElementById(route).classList.add("text-white");
                break;
            }
        }
    </script>
</nav>