<%-- 
    Document   : SignUpForm
    Created on : Feb 10, 2021, 8:59:19 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobinder</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white  col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Đăng kí</h3>
                    <form action="signup" method="POST">
                        <fieldset class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control <c:if test="${not empty constraints.email}">is-invalid</c:if>" id="email" name="email" value="<c:out value="${inputAccount.email}"/>" required /> 
                            <c:if test="${not empty constraints.email}">
                                <div class="invalid-feedback">
                                    ${constraints.email}
                                </div>
                            </c:if>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="password">Mật khẩu</label>
                            <input type="password" class="form-control <c:if test="${not empty constraints.password}">is-invalid</c:if>" id="password" name="password" value="<c:out value="${inputAccount.password}"/>" required />
                            <c:if test="${not empty constraints.password}">
                                <div class="invalid-feedback">
                                    ${constraints.password}
                                </div>
                            </c:if>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="phoneNumber">Số điện thoại</label>
                            <input type="text" class="form-control <c:if test="${not empty constraints.phoneNumber}">is-invalid</c:if>" id="phoneNumber" name="phoneNumber" value="<c:out value="${inputAccount.phoneNumber}"/>" required />
                            <c:if test="${not empty constraints.phoneNumber}">
                                <div class="invalid-feedback">
                                    ${constraints.phoneNumber}
                                </div>
                            </c:if>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="name">Họ tên</label>
                            <input type="text" class="form-control" id="name" name="name" value="<c:out value="${inputAccount.name}"/>" required />
                        </fieldset>
                        <label class="mt-3" for="accountType">Bạn là</label>
                        <select id="accountType" class="form-select" name="accountType">
                            <c:forEach var="type" items="${accountTypes}">
                                <option <c:if test="${not empty inputAccount && inputAccount.accountTypeId == type.accountTypeId}">selected</c:if>" value="${type.accountTypeId}">${type.name}</option>
                            </c:forEach>
                        </select>
                        <%@include file="notification.jsp" %>
                        <button type="submit" class="mt-2 btn btn-primary" >Tạo tài khoản</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>