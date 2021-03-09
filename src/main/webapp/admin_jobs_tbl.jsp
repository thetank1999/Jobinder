<%-- 
    Document   : admin_jobs_tbl
    Created on : Mar 8, 2021, 10:16:51 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="utils.CurrencyUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="d-flex justify-content-between">
    <h3>Các công việc của tôi</h3>
    <div>
        <a style="text-decoration: none" href="admin?action=company" class="btn btn-dark">Doanh nghiệp <i class="fas fa-edit"></i></a>
        <a style="text-decoration: none" href="admin?action=createjob" class="btn btn-dark">Thêm mới <i class="fas fa-plus"></i></a>
    </div>
</div>
<table class="table table-responsive bg-dark mt-3" style="font-weight: 400; overflow: hidden">
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
    <c:forEach var="job" items="${jobs}">
        <tr style="padding: 0 20px">
            <td><a style="text-decoration: none; color: black;" href="jobs?jobId=${job.jobId}"><b><c:out value="${job.title}"/></b></a></td>
            <td>${job.views}</td>
            <td>
                <form action="admin?action=switchJobStatus&jobId=${job.jobId}" method="POST">
                    <button style="border: none; background-color: transparent;" type="submit">
                        <c:if test="${job.status}">
                            <i class="fas fa-eye"></i> Hiện
                        </c:if>
                        <c:if test="${not job.status}">
                            <i class="fas fa-eye-slash"></i> Ẩn
                        </c:if>
                    </button>
                </form>
            </td>
            <td><c:out value="${job.description}"/></td>
        <td><c:out value="${CurrencyUtils.formatVND(job.startingSalary)}"/></td>
        <td><c:out value="${job.lastModified}"/></td>
        <td>
            <a href="admin?action=editjob&jobId=${job.jobId}" style="margin-right: 10px; text-decoration: none"><i class="fas fa-edit"></i> Sửa</a>
            <form style="display: inline" action="admin?action=deletejob&jobId=${job.jobId}" method="POST">
                <button style="border: none; background-color: transparent;" type="button" onclick="confirmDelete(this.parentElement)" class="text-danger"><i class="fas fa-trash-alt"></i> Xóa</button>
            </form>
        </td>
        </tr>
    </c:forEach>
</tbody>
</table>
<c:if test="${empty jobs}">
    <p class="text-center">Bạn chưa có công việc nào</p>
</c:if>
