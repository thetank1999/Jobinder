<%-- 
    Document   : admin_panel
    Created on : Mar 9, 2021, 3:26:57 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="models.account.AccountType" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="d-flex justify-content-between flex-wrap px-4" style="background-color: rgba(0, 0, 0, 0.8)">
    <c:if test="${account.accountTypeId == AccountType.JOB_SEEKER}">
        <a style="font-weight: 700; color: azure; text-decoration: none" href="admin" class="btn btn-link p-2 ${action == null ? "text-white" : "text-secondary"}"><i class="fas fa-list"></i> Lý lịch</a>
    </c:if>
    <c:if test="${account.accountTypeId == AccountType.RECRUITER}">
        <a style="font-weight: 700; color: azure; text-decoration: none" href="admin" class="btn btn-link p-2 ${action == null ? "text-white" : "text-secondary"}"><i class="fas fa-list"></i> Công việc</a>
    </c:if>
    <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=applications" class="btn btn-link p-2 ${action == "applications" ? "text-white" : "text-secondary"}"><i class="far fa-clipboard"></i> Đơn ứng tuyển</a>
    <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=account" class="btn btn-link p-2 ${action == "account" ? "text-w hite" : "text-secondary"}"><i class="far fa-user"></i> Tài khoản</a>
    <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=help" class="btn btn-link p-2 ${action == "help" ? "text-w hite" : "text-secondary"}"><i class="far fa-life-ring"></i> Trợ giúp</a>
</div>
<div class="bg-white text-white m-0 p-4" style="background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('images/search.png');
     background-position:center top; background-size: cover">
    <div class="p-0">
        <img height="100" style="object-fit: cover" width="100" class="rounded-circle" src="${account.imageUri}">
        <h5 class="mt-2"><c:out value="${account.name}"/></h5>
    </div>
</div>