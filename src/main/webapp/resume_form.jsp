<%-- 
    Document   : resume_form
    Created on : Feb 13, 2021, 10:09:13 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

<c:set var="isEdit" value="${action == 'editresume'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/imagePreview.js"></script>
        <title>Resume</title>
    </head>
    <body class="bg-light">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white col-lg-6 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">${isEdit ? "Chỉnh sửa" : "Thêm mới"} lý lịch</h3>
                    <form action="admin?action=${action}<c:if test="${isEdit}">&resumeId=${resume.resumeId}</c:if>" enctype="multipart/form-data" method="POST">
                        <fieldset class="form-group mb-3">
                            <label for="title">Tiêu đề</label>
                            <input type="text" class="form-control" id="title" name="title" value="<c:out value="${resume.title}"/>" required /> 
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="position">Vị trí ứng tuyển</label>
                            <input type="text" class="form-control" id="position" name="position" value="<c:out value="${resume.position}"/>" required /> 
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="yearOfExp">Năm kinh nghiệm</label>
                            <input type="number" min="0" step="1" class="form-control <c:if test="${not empty constraints.yearOfExp}">is-invalid</c:if>" id="yearOfExp" name="yearOfExp" value="<c:out value="${resume.yearOfExperience}"/>" required />
                            <c:if test="${not empty constraints.yearOfExp}">
                                <div class="invalid-feedback">
                                    ${constraints.yearOfExp}
                                </div>
                            </c:if>
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="bio">Giới thiệu sơ lược</label>
                            <textarea class="form-control" id="bio" name="bio" cols="5" required><c:out value="${resume.bio}"/></textarea>
                        </fieldset>
                        <%@include file="workDetails_form.jsp" %>
                        <fieldset class="form-group mt-3">
                            <label>Tình trạng</label>
                            <div class="form-check">
                                <input class="form-check-input" value="true" <c:if test="${empty job || job.status}">checked</c:if> type="radio" name="status" id="status_true" >
                                    <label class="form-check-label" for="status_true">
                                        Hiện
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" value="false" <c:if test="${not empty job && !job.status}">checked</c:if> type="radio" name="status" id="status_false" >
                                    <label class="form-check-label" for="status_false">
                                        Ẩn
                                    </label>
                                </div>
                            </fieldset>
                        <%@include file="notification.jsp" %>
                        <button type="submit" class="mt-2 btn btn-primary w-100 d-inline-block text-center" >Lưu</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
