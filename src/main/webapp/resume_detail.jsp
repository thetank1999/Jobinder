<%-- 
    Document   : resume_detail
    Created on : Feb 19, 2021, 9:54:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>

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
                    <img class="img-thumbnail" src="${resume.workDetails.imageUri}" style="width: max(15vw, 25vh); height: max(15vw, 25vh); object-fit: cover;">
                </div>
                <div class="col-8 col-lg-9" style="z-index: 1;">
                    <h2><c:out value="${account.name}"/></h2>
                    <div><a class="my-1" href="mailto:<c:out value="${account.email}"/>"><i class="far fa-envelope"></i> <c:out value="${account.email}"/></a></div>
                    <div><a class="d-block my-1" href="tel:<c:out value="${account.phoneNumber}"/>"><i class="fas fa-phone-alt"></i> <c:out value="${account.phoneNumber}"/></a></div>
                    <div><a class="d-block my-1" href="resumes?accountId=${account.accountId}"><i class="fas fa-list"></i> Các lý lịch khác của tôi</a></div>
                    <div><p class="my-1">Cập nhật lần cuối: ${resume.lastModified}</p></div>
                </div>
                <img src="svgs/resume_icon.svg" style="width: 30vw; height: auto; position: absolute; transform: rotate(45deg); right: 5vw; top: -5vw; z-index: 0;">
            </div>
            <h1 class="text-center display-4 mt-5"><c:out value="${resume.title}"/></h1>
            <div class="row p-5" >
                <div class="col-lg-3">
                    <h3>Thông tin lý lịch</h3>
                </div>
                <div class="col-lg-9">
                    <table class="table table-responsive">
                        <tbody>
                            <tr>
                                <th>Vị trí ứng tuyển</th>
                                <td><c:out value="${resume.position}"/></td>
                            </tr>
                            <tr>
                                <th>Số năm kinh nghiệm</th>
                                <td><c:out value="${resume.yearOfExperience}"/></td>
                            </tr>
                            <tr>
                                <th>Giới thiệu sơ lược</th>
                                <td><c:out value="${resume.bio}"/></td>
                            </tr>
                            <tr>
                                <th>Các ngôn ngữ thành thạo</th>
                                <td>${resumeLanguages}</td>
                            </tr>
                            <tr>
                                <th>Nơi làm việc</th>
                                <td>${resumeLocation.name}</td>
                            </tr>
                            <tr>
                                <th>Trình độ học vấn</th>
                                <td>${resumeAcademicLevel.title}</td>
                            </tr>
                            <tr>
                                <th>Lĩnh vực nghề nghiệp</th>
                                <td>${resumeField.name}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>

