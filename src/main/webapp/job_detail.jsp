<%-- 
    Document   : job_detail
    Created on : Mar 1, 2021, 10:00:45 PM
    Author     : Admin
--%>

<%@page import="utils.CurrencyUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <title>Jobinder</title>
    </head>
    <body class="bg-light">
        <%@include  file="header.jsp"%>
        <div class="container bg-white rounded shadow m-5 mx-auto" style="overflow: hidden;">
            <div class="row bg-dark text-white px-5 py-3" style="overflow: hidden; position: relative;">
                <div class="col-4 col-lg-3">
                    <img class="img-fluid img-thumbnail" src="${job.workDetails.imageUri}" style="width: max(15vw, 25vh); height: max(15vw, 25vh); object-fit: cover;">
                </div>
                <div class="col-8 col-lg-9" style="z-index: 1;">
                    <div><h2><c:out value="${account.name}"/></h2></div>
                    <div><a class="d-block my-1" href="mailto:<c:out value="${account.email}"/>"><i class="far fa-envelope"></i> <c:out value="${account.email}"/></a></div>
                    <div><a class="d-block my-1" href="tel:<c:out value="${account.phoneNumber}"/>><i class="fas fa-phone-alt"></i> <c:out value="${account.phoneNumber}"/></a></div>
                    <div><a class="d-block my-1" href="jobs?accountId=${account.accountId}"><i class="fas fa-list"></i> Các công việc khác của tôi</a></div>
                    <div><p class="my-1">Cập nhật lần cuối: <c:out value="${job.lastModified}"/></p></div>
                </div>
                <img src="svgs/job_icon.svg" style="width: 30vw; height: auto; position: absolute; transform: rotate(-45deg); right: 5vw; top: -5vw; z-index: 0;">
            </div>
            <h1 class="text-center display-4 mt-5"><c:out value="${job.title}"/></h1>
            <div class="row p-5" >
                <div class="col-md-3">
                    <h3>Thông tin công việc</h3>
                </div>
                <div class="col-md-9">
                    <table class="table table-responsive">
                        <tbody>
                            <tr>
                                <th>Loại công việc</th>
                                <td>${jobType.name}</td>
                            </tr>
                            <tr>
                                <th>Lương khởi điểm</th>
                                <td>${CurrencyUtils.formatVND(job.startingSalary)}</td>
                            </tr>
                            <tr>
                                <th>Mô tả công việc</th>
                                <td>${job.description}</td>
                            </tr>
                            <tr>
                                <th>Các ngôn ngữ thành thạo</th>
                                <td>${jobLanguages}</td>
                            </tr>
                            <tr>
                                <th>Nơi làm việc</th>
                                <td>${jobLocation.name}</td>
                            </tr>
                            <tr>
                                <th>Trình độ học vấn</th>
                                <td>${jobAcademicLevel.title}</td>
                            </tr>
                            <tr>
                                <th>Lĩnh vực nghề nghiệp</th>
                                <td>${jobField.name}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row p-5" >
                <div class="col-md-3">
                    <h3>Thông tin doanh nghiệp</h3>
                </div>
                <div class="col-md-9 row" style="overflow: hidden; position: relative;">
                    <div class="col-4 col-lg-3">
                        <img class="img-fluid img-thumbnail" src="${job.company.imageUri}" style="width: max(10vw, 20vh); height: max(10vw, 20vh); object-fit: cover;">
                    </div>
                    <div class="col-8 col-lg-9" style="z-index: 1;">
                        <h2><c:out value="${job.company.name}"/></h2>
                        <p><c:out value="${job.company.description}"/></p>
                        <p><i class="fas fa-map-marker-alt ml-2"></i> <c:out value="${job.company.address}"/></p>
                    </div>
                </div>
            </div>
            <c:if test="${not empty resumes}">
                <%@include file="apply_job.jsp" %>
            </c:if>
            <c:if test="${empty resumes}">
                <p class="text-secondary text-center mt-3">Bạn phải có tài khoản kiếm việc mới có thể ứng tuyển cho công việc này</p>
            </c:if>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
