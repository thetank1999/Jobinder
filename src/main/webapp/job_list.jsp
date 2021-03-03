<%-- 
    Document   : job_list
    Created on : Mar 1, 2021, 10:49:23 AM
    Author     : Admin
--%>

<%@page import="utils.CurrencyUtils"%>
<%@page import="models.job.JobType"%>
<%@page import="java.util.List"%>
<%@page import="models.job.Job"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <title>Jobs</title>
    </head>
    <body class="bg-light w-100 d-flex flex-column justify-content-between" style="min-height: 100vh">
        <div style="overflow: hidden">
            <%@include  file="header.jsp"%>
            <div class="row mb-3">
                <%@include file="job_filter.jsp" %>
                <div class="container d-flex col-lg-8 flex-column justify-content-start align-items-center">
                    <%for (Job job : jobs) {%>
                    <div class="card shadow min mt-2" style="padding: 0; width: 95%;">
                        <div class="card-body" style="padding: 20px 20px 0 20px">
                            <div class="d-flex">
                                <img class="img-fluid img-thumbnail" style="width: 12vw; height: 12vw; object-fit: cover;" src="<%=job.getWorkDetails().getImageUri()%>">
                                <div class="px-3">
                                    <h4 class="card-text mx-2"><a style="text-decoration: none; color: black;" href="jobs?jobId=<%=job.getJobId()%>"><%=job.getTitle()%></a></h4>
                                    <p class="card-text text-secondary mx-2"><%=job.getDescription().substring(0, Math.min(45, job.getDescription().length()))%>...</p>
                                    <div class="d-flex">
                                        <p class="card-text text-secondary mx-2">
                                            <i class="far text-warning fa-clock"></i> <%=((List<JobType>) request.getAttribute("jobTypes")).stream().filter(jt -> jt.getJobTypeId() == job.getJobTypeId()).findFirst().get().getName()%>
                                        </p>
                                        <p class="card-text text-secondary mx-2">
                                            <i class="fas text-warning fa-map-marker-alt ml-2"></i> <%=((List<Location>) request.getAttribute("locations")).stream().filter(lc -> lc.getLocationId() == job.getWorkDetails().getLocationId()).findFirst().get().getName()%>
                                        </p>
                                    </div>
                                    <div class="d-flex">
                                        <p class="card-text text-secondary mx-2">
                                            <i class="fas text-warning fa-briefcase"></i> <%=((List<Field>) request.getAttribute("fields")).stream().filter(fi -> fi.getFieldId() == job.getWorkDetails().getFieldId()).findFirst().get().getName()%>
                                        </p>
                                        <p class="card-text text-secondary mx-2">
                                            <i class="fas text-warning fa-money-check-alt"></i> <%=CurrencyUtils.formatVND(job.getStartingSalary())%>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <hr style="margin-bottom:0;"/>
                            <div class="d-flex justify-content-between align-items-center">
                                <p class="text-secondary my-2"><i class="far text-warning fa-calendar-alt"></i> Cập nhật: <%=job.getLastModified()%></p>
                                <p class="text-secondary my-2"><i class="fas fa-chart-line text-warning"></i> Lượt xem: <%=job.getViews()%></p>
                                <a class="my-2 btn btn-link text-secondary" style="text-decoration: none;" href="<%=request.getContextPath() + "/jobs?jobId=" + job.getJobId()%>">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%if (jobs.size() == 0) {%>
                    <p class="mt-3" style="color: graytext">Không có công việc nào được tìm thấy</p>
                    <%}%>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
