<%-- 
    Document   : company_form
    Created on : Feb 28, 2021, 10:20:34 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/imagePreview.js"></script>
        <title>Company</title>
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white  col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Thông tin doanh nghiệp</h3>
                    <form action="admin?action=company" method="POST" enctype="multipart/form-data">
                        <input type="text" id="companyId" name="companyId" value="${company.companyId}" hidden />
                        <fieldset class="form-group">
                            <label for="name">Tên</label>
                            <input type="text" class="form-control" id="name" name="name" value="<c:out value="${company.name}"/>" required /> 
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="address">Địa chỉ</label>
                            <input type="text" class="form-control" id="address" name="address" value="<c:out value="${company.address}"/>" required />
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="description">Miêu tả</label>
                            <textarea class="form-control <c:if test="${not empty constraints.description}">is-invalid</c:if>" id="description" name="description" cols="5" required><c:out value="${company.description}"/></textarea>
                            <c:if test="${not empty constraints.description}">
                                <div class="invalid-feedback">
                                    <c:out value="${constraints.description}"/>
                                </div>
                            </c:if>
                        </fieldset>
                        <label class="form-label mt-3" for="image">Ảnh đại diện</label>
                        <input accept="image/*" onchange="showPreview(this)" name="image" type="file" class="form-control <c:if test="${not empty constraints.image}">is-invalid</c:if>" id="image" required />
                        <c:if test="${not empty constraints.image}">
                            <div class="invalid-feedback">
                                <c:out value="${constraints.image}"/>
                            </div>
                        </c:if>
                        <div class="w-50 mt-2" id="img-preview"></div>
                        <button type="submit" class="mt-2 btn btn-primary w-100" >Lưu thông tin</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
