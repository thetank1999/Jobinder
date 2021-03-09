<%-- 
    Document   : job_form
    Created on : Feb 28, 2021, 9:48:17 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

<c:set var="isEdit" value="${action == 'editjob'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/imagePreview.js"></script>
        <title>Job</title>
    </head>
    <body class="bg-light d-flex justify-content-between flex-column" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white col-lg-6 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">${isEdit ? "Chỉnh sửa" : "Thêm mới"} công việc</h3>
                    <form action="admin?action=${action}<c:if test="${isEdit}">&jobId=${job.jobId}</c:if>" enctype="multipart/form-data" method="POST">
                        <fieldset class="form-group mb-3">
                            <label for="title">Tiêu đề</label>
                            <input type="text" class="form-control" id="title" name="title" value="<c:out value="${job.title}"/>" required /> 
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="description">Miêu tả</label>
                            <textarea class="form-control <c:if test="${not empty constraints.description}">is-invalid</c:if>" id="description" name="description" cols="5" required><c:out value="${job.description}"/></textarea>
                            <c:if test="${not empty constraints.description}">
                                <div class="invalid-feedback">
                                    ${constraints.description}
                                </div>
                            </c:if>
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="startingSalary">Lương khởi điểm (VNĐ)</label>
                            <input type="number" min="0" step="any" class="form-control" id="startingSalary" name="startingSalary" value="<c:out value="${job.startingSalary}"/>" required />
                        </fieldset>
                        <label for="jobType">Loại công việc</label>
                        <select id="jobType" class="form-select mb-3" name="jobType">
                            <c:forEach var="type" items="${jobTypes}">
                                <option <c:if test="${not empty job && job.jobTypeId == type.jobTypeId}">selected</c:if> value="${type.jobTypeId}">${type.name}</option>
                            </c:forEach>
                        </select>
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
                            <button type="submit" class="mt-2 btn btn-primary w-100 d-inline-block text-center" >Lưu</button>
                        </form>
                    </div>
                </div>
            </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
