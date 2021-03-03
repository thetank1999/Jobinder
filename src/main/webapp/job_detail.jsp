<%-- 
    Document   : job_detail
    Created on : Mar 1, 2021, 10:00:45 PM
    Author     : Admin
--%>

<%@page import="utils.CurrencyUtils"%>
<%@page import="models.job.JobType"%>
<%@page import="models.common.Field"%>
<%@page import="models.common.AcademicLevel"%>
<%@page import="models.common.Location"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="models.common.Language"%>
<%@page import="models.job.Job"%>
<%@page import="models.account.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
    Account account = (Account) request.getAttribute("account");
    Job job = (Job) request.getAttribute("job");
    JobType jobType = (JobType) request.getAttribute("jobType");
    List<Language> languages = (List<Language>) request.getAttribute("jobLanguages");
    Location location = (Location) request.getAttribute("jobLocation");
    AcademicLevel level = (AcademicLevel) request.getAttribute("jobAcademicLevel");
    Field field = (Field) request.getAttribute("jobField");
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
                    <img class="img-fluid img-thumbnail" src="<%=job.getWorkDetails().getImageUri()%>" style="width: 15vw; height: 15vw; object-fit: cover;">
                </div>
                <div class="col-9" style="z-index: 1;">
                    <h2><%=account.getName()%></h2>
                    <a class="d-block mb-1" href="mailto:<%=account.getEmail()%>"><i class="far fa-envelope"></i> <%=account.getEmail()%></a>
                    <a class="d-block mb-1" href="tel:<%=account.getPhoneNumber()%>"><i class="fas fa-phone-alt"></i> <%=account.getPhoneNumber()%></a>
                    <p>Cập nhật lần cuối: <%=job.getLastModified()%></p>
                </div>
                <img src="svgs/job_icon.svg" style="width: 30vw; height: auto; position: absolute; transform: rotate(-45deg); right: 5vw; top: -5vw; z-index: 0;">
            </div>
            <h1 class="text-center display-4 mt-5"><%=job.getTitle()%></h1>
            <div class="row p-5" >
                <div class="col-3">
                    <h3>Thông tin công việc</h3>
                </div>
                <div class="col-9">
                    <table class="table table-responsive">
                        <tbody>
                            <tr>
                                <th>Loại công việc</th>
                                <td><%=jobType.getName()%></td>
                            </tr>
                            <tr>
                                <th>Lương khởi điểm</th>
                                <td><%=CurrencyUtils.formatVND(job.getStartingSalary())%></td>
                            </tr>
                            <tr>
                                <th>Mô tả công việc</th>
                                <td><%=job.getDescription()%></td>
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
            <div class="row p-5" >
                <div class="col-3">
                    <h3>Thông tin doanh nghiệp</h3>
                </div>
                <div class="col-9 row" style="overflow: hidden; position: relative;">
                    <div class="col-3">
                        <img class="img-fluid img-thumbnail" src="<%=job.getCompany().getImageUri()%>" style="width: 10vw; height: 10vw; object-fit: cover;">
                    </div>
                    <div class="col-9" style="z-index: 1;">
                        <h2><%=job.getCompany().getName()%></h2>
                        <p><%=job.getCompany().getDescription()%></p>
                        <p><i class="fas fa-map-marker-alt ml-2"></i> <%=job.getCompany().getAddress()%></p>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
    </body>
</html>
