<%-- 
    Document   : resume_detail
    Created on : Feb 19, 2021, 9:54:09 PM
    Author     : Admin
--%>

<%@page import="models.common.Field"%>
<%@page import="models.common.AcademicLevel"%>
<%@page import="models.common.Location"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="models.common.Language"%>
<%@page import="models.resume.Resume"%>
<%@page import="models.account.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
    Account account = (Account) request.getAttribute("account");
    Resume resume = (Resume) request.getAttribute("resume");
    List<Language> languages = (List<Language>) request.getAttribute("resumeLanguages");
    Location location = (Location) request.getAttribute("resumeLocation");
    AcademicLevel level = (AcademicLevel) request.getAttribute("resumeAcademicLevel");
    Field field = (Field) request.getAttribute("resumeField");
%>
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
        <div class="container bg-white rounded shadow m-5" style="overflow: hidden;">
            <div class="row bg-dark text-white px-5 py-3" style="overflow: hidden; position: relative;">
                <div class="col-3">
                    <img class="img-thumbnail" src="<%=resume.getWorkDetails().getImageUri()%>" style="width: 15vw; height: 15vw; object-fit: cover;">
                </div>
                <div class="col-9" style="z-index: 1;">
                    <h2><%=account.getName()%></h2>
                    <a class="d-block mb-1" href="mailto:<%=account.getEmail()%>"><i class="far fa-envelope"></i> <%=account.getEmail()%></a>
                    <a class="d-block mb-1" href="tel:<%=account.getPhoneNumber()%>"><i class="fas fa-phone-alt"></i> <%=account.getPhoneNumber()%></a>
                    <p>Cập nhật lần cuối: <%=resume.getLastModified()%></p>
                </div>
                <img src="svgs/resume_icon.svg" style="width: 30vw; height: auto; position: absolute; transform: rotate(45deg); right: 5vw; top: -5vw; z-index: 0;">
            </div>
            <h1 class="text-center display-4 mt-5"><%=resume.getTitle()%></h1>
            <div class="row p-5" >
                <div class="col-3">
                    <h3>Thông tin lý lịch</h3>
                </div>
                <div class="col-9">
                    <table class="table table-responsive">
                        <tbody>
                            <tr>
                                <th>Vị trí ứng tuyển</th>
                                <td><%=resume.getPosition()%></td>
                            </tr>
                            <tr>
                                <th>Số năm kinh nghiệm</th>
                                <td><%=resume.getYearOfExperience()%></td>
                            </tr>
                            <tr>
                                <th>Giới thiệu sơ lược</th>
                                <td><%=resume.getBio()%></td>
                            </tr>
                            <tr>
                                <th>Các ngôn ngữ thành thạo</th>
                                <td><%=languages.stream().map(Language::getName).collect(Collectors.joining(", "))%></td>
                            </tr>
                            <tr>
                                <th>Nơi làm việc</th>
                                <td><%=location.getName()%></td>
                            </tr>
                            <tr>
                                <th>Trình độ học vấn</th>
                                <td><%=level.getTitle()%></td>
                            </tr>
                            <tr>
                                <th>Lĩnh vực nghề nghiệp</th>
                                <td><%=field.getName()%></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
    </body>
</html>
