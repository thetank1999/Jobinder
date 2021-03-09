<%-- 
    Document   : admin_account
    Created on : Mar 8, 2021, 11:25:29 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="js/imagePreview.js"></script>
<div class="card bg-white col-lg-5 col-md-8 mx-auto">
    <div class="card-body">
        <h3 class="card-title">Tài khoản</h3>
        <form action="admin?action=account" enctype="multipart/form-data" method="POST">
            <input type="text" class="form-control" name="accountId" hidden value="${inputAccount.accountId}" readonly required /> 
            <fieldset class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="<c:out value="${inputAccount.email}"/>" readonly required /> 
            </fieldset>
            <fieldset class="form-group mt-3">
                <label for="password">Mật khẩu</label>
                <input type="password" class="form-control <c:if test="${not empty constraints.password}">is-invalid</c:if>" id="password" name="password" value="placeholder" readonly />
                <a style="text-decoration: none" href="resetpassword?email=${inputAccount.email}">Gửi yêu cầu đặt lại mật khẩu</a>
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
            <label class="form-label mt-3" for="image">Ảnh đại diện</label>
            <input accept="image/*" onchange="showPreview(this)" name="image" type="file" class="form-control <c:if test="${not empty constraints.image}">is-invalid</c:if>" id="image" required />
            <c:if test="${not empty constraints.image}">
                <div class="invalid-feedback">
                    ${constraints.image}
                </div>
            </c:if>
            <div class="w-50 mt-2" id="img-preview"></div>
            <fieldset class="form-group mt-3">
                <label for="name">Họ tên</label>
                <input type="text" class="form-control" id="name" name="name" value="<c:out value="${inputAccount.name}"/>" required />
            </fieldset>
            <input type="text" class="form-control" name="accountType" hidden value="${inputAccount.accountTypeId}" readonly required /> 
            <%@include file="notification.jsp" %>
            <button type="submit" class="mt-2 btn btn-primary w-100" >Cập nhật thông tin</button>
        </form>
    </div>
</div>