<%-- 
    Document   : admin_seeker.jsp
    Created on : Feb 13, 2021, 9:30:50 AM
    Author     : Admin
--%>

<%@page import="java.util.List"%>
<%@page import="models.resume.Resume"%>
<%@page import="models.account.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Account account = (Account) request.getSession().getAttribute("account");
    List<Resume> resumes = (List<Resume>) request.getAttribute("resumes");
    String action = (String) request.getAttribute("action");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
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
        <script src="js/confirmDelete.js"></script>
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <div>
            <%@include file="header.jsp" %>
            <div class="d-flex justify-content-between flex-wrap px-4" style="background-color: rgba(0, 0, 0, 0.8)">
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin" class="btn btn-link p-2 <%=action == null ? "text-white" : "text-secondary"%>"><i class="fas fa-list"></i> Lý lịch</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=notifications" class="btn btn-link p-2 <%=action == "notifications" ? "text-white" : "text-secondary"%>"><i class="far fa-bell"></i> Thông báo</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=applications" class="btn btn-link p-2 <%=action == "applications" ? "text-white" : "text-secondary"%>"><i class="far fa-clipboard"></i> Đơn ứng tuyển</a>
                <a style="font-weight: 700; color: azure; text-decoration: none" href="admin?action=work" class="btn btn-link p-2 <%=action == "jobs" ? "text-w hite" : "text-secondary"%>"><i class="fas fa-briefcase"></i> Hợp đồng</a>
            </div>
            <div class="bg-white text-white m-0 p-4" style="background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('images/work.jpg');
                 background-position:center top; background-size: cover">
                <div class="p-0">
                    <img height="100" width="100" class="rounded-circle" src="images/avatar.png">
                    <h5 class="mt-2"><%=account.getName()%></h5>
                </div>
            </div>
            <div class="p-4">
                <div class="d-flex justify-content-between">
                    <h3>Các lý lịch của tôi</h3>
                    <a style="text-decoration: none" href="<%=request.getContextPath() + "/admin?action=createresume"%>" class="btn btn-dark">Thêm mới <i class="fas fa-plus"></i></a>
                </div>
                <table class="table table-hover table-striped table-responsive bg-dark mt-3" style="font-weight: 400; overflow: hidden">
                    <thead class="thead-primary text-white">
                        <tr>
                            <th scope="col"><i class="fas fa-info-circle text-white"></i> Tiêu đề</th>
                            <th scope="col"><i class="fas fa-chart-line text-white"></i> Lượt xem</th>
                            <th scope="col"><i class="fas fa-unlock-alt text-white"></i> Trạng thái</th>
                            <th scope="col"><i class="fab fa-black-tie text-white"></i> Chức vụ</th>
                            <th scope="col"><i class="fas fa-layer-group text-white"></i> Năm KN</th>
                            <th scope="col"><i class="far fa-calendar-alt text-white"></i> Cập nhật</th>
                            <th scope="col"><i class="fas fa-th-large text-white"></i> Hành động</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white">
                        <%for (Resume resume : resumes) {%>
                        <tr style="padding: 0 20px">
                            <td><a style="text-decoration: none; color: black;" href="<%="resumes?resumeId=" + resume.getResumeId()%>"><b><%=resume.getTitle()%></b></a></td>
                            <td><%=resume.getViews()%></td>
                            <td><%=resume.getStatus() ? "<i class=\"fas fa-eye\"></i> Hiện" : "<i class=\"fas fa-eye-slash\"></i> Ẩn"%></td>
                            <td><%=resume.getPosition()%></td>
                            <td><%=resume.getYearOfExperience()%></td>
                            <td><%=resume.getLastModified()%></td>
                            <td class="d-flex">
                                <a href="<%="admin?action=editresume&resumeId=" + resume.getResumeId()%>" style="margin-right: 10px"><i class="fas fa-edit"></i></a>
                                <form action="<%="admin?action=deleteresume&resumeId=" + resume.getResumeId()%>" method="POST">
                                    <button style="border: none; background-color: transparent;" type="button" onclick="confirmDelete(this.parentElement)"><i style="color: red;" class="fas fa-trash-alt"></i></button>
                                </form>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <%if (resumes.isEmpty()) {%>
                <p class="text-center">Bạn chưa có lý lịch nào</p>
                <%}%>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
