<%-- 
    Document   : admin_recruiter.jsp
    Created on : Feb 13, 2021, 9:30:35 AM
    Author     : Admin
--%>

<%@page import="utils.CurrencyUtils"%>
<%@page import="models.job.Job"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Account account = (Account) request.getSession().getAttribute("account");
    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
    String action = (String) request.getAttribute("action");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/confirmDelete.js"></script>
        <title>Admin</title>
        <style>
            .table>:not(caption)>*>* {
                border-bottom-width: 0!important;
            }
            td {
                padding-top: 20px !important;
                padding-bottom: 20px !important;
            }
        </style>
        <script>
            function confirmDelete(form) {
                if (confirm("Are you sure you want to delete?")) {
                    form.submit();
                }
            }
        </script>
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <div>
            <%@include file="header.jsp" %>
            <div class="d-flex justify-content-between flex-wrap px-4" style="background-color: rgba(0, 0, 0, 0.8)">
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin" class="btn btn-link p-2 <%=action == null ? "text-white" : "text-secondary"%>"><i class="fas fa-list"></i> Công việc</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=notifications" class="btn btn-link p-2 <%=action == "notifications" ? "text-white" : "text-secondary"%>"><i class="far fa-bell"></i> Thông báo</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=applications" class="btn btn-link p-2 <%=action == "applications" ? "text-white" : "text-secondary"%>"><i class="far fa-clipboard"></i> Đơn ứng tuyển</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=work" class="btn btn-link p-2 <%=action == "jobs" ? "text-w hite" : "text-secondary"%>"><i class="fas fa-briefcase"></i> Hợp đồng</a>
            </div>
            <div class="bg-white text-white m-0 p-4" style="background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('images/search.png');
                 background-position:center top; background-size: cover">
                <div class="p-0">
                    <img height="100" width="100" class="rounded-circle" src="images/avatar.png">
                    <h5 class="mt-2"><%=account.getName()%></h5>
                </div>
            </div>
            <div class="p-4">
                <div class="d-flex justify-content-between">
                    <h3>Các công việc của tôi</h3>
                    <div>
                        <a style="text-decoration: none" href="<%=request.getContextPath() + "/admin?action=company"%>" class="btn btn-dark">Doanh nghiệp <i class="fas fa-edit"></i></a>
                        <a style="text-decoration: none" href="<%=request.getContextPath() + "/admin?action=createjob"%>" class="btn btn-dark">Thêm mới <i class="fas fa-plus"></i></a>
                    </div>
                </div>
                <table class="table table-hover table-striped table-responsive bg-dark mt-3" style="font-weight: 400; overflow: hidden">
                    <thead class="thead-primary text-white">
                        <tr>
                            <th scope="col"><i class="fas fa-info-circle text-white"></i> Tiêu đề</th>
                            <th scope="col"><i class="fas fa-chart-line text-white"></i> Lượt xem</th>
                            <th scope="col"><i class="fas fa-unlock-alt text-white"></i> Trạng thái</th>
                            <th scope="col"><i class="far fa-question-circle"></i> Miêu tả</th>
                            <th scope="col"><i class="far fa-credit-card"></i> Lương KĐ</th>
                            <th scope="col"><i class="far fa-calendar-alt text-white"></i> Cập nhật</th>
                            <th scope="col"><i class="fas fa-th-large text-white"></i> Hành động</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white">
                        <%for (Job job : jobs) {%>
                        <tr style="padding: 0 20px">
                            <td><a style="text-decoration: none; color: black;" href="<%="jobs?jobId=" + job.getJobId()%>"><b><%=job.getTitle()%></b></a></td>
                            <td><%=job.getViews()%></td>
                            <td><%=job.getStatus() ? "<i class=\"fas fa-eye\"></i> Hiện" : "<i class=\"fas fa-eye-slash\"></i> Ẩn"%></td>
                            <td><%=job.getDescription()%></td>
                            <td><%=CurrencyUtils.formatVND((job.getStartingSalary()))%></td>
                            <td><%=job.getLastModified()%></td>
                            <td>
                                <a href="<%="admin?action=editjob&jobId=" + job.getJobId()%>" style="margin-right: 10px"><i class="fas fa-edit"></i></a>
                                <form style="display: inline" action="<%="admin?action=deletejob&jobId=" + job.getJobId()%>" method="POST">
                                    <button style="border: none; background-color: transparent;" type="button" onclick="confirmDelete(this.parentElement)"><i style="color: red;" class="fas fa-trash-alt"></i></button>
                                </form>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <%if (jobs.isEmpty()) {%>
                <p class="text-center">Bạn chưa có công việc nào</p>
                <%}%>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
