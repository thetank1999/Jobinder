<%-- 
    Document   : admin_applications_tbl
    Created on : Mar 8, 2021, 8:44:28 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="models.account.AccountType" %>
<%@page import="models.application.ApplicationStatus" %>

<table class="table table-responsive bg-dark mt-3" style="font-weight: 400; overflow: hidden">
    <thead class="thead-primary text-white">
        <tr>
            <th scope="col"><i class="fas fa-briefcase text-white"></i> Công việc</th>
            <th scope="col"><i class="far fa-clipboard text-white"></i> Lý lịch</th>
            <th scope="col"><i class="far fa-calendar-alt text-white"></i> Ngày nộp</th>
            <th scope="col"><i class="far fa-comments text-white"></i> Lời nhắn</th>
            <th scope="col"><i class="far fa-question-circle text-white"></i> Trạng thái</th>
            <th scope="col"><i class="fas fa-th-large text-white"></i> Hành động</th>
        </tr>
    </thead>
    <tbody class="bg-white">
        <c:forEach var="application" items="${applications}">
            <tr style="padding: 0 20px">
                <td><a target="_blank" style="text-decoration: none; color: black;" href="jobs?jobId=${application.jobId}">
                        <b><c:out value="${jobMappings[application.jobId].title}"/></b>
                    </a>
                </td>
                <td><a target="_blank" style="text-decoration: none; color: black;" href="resumes?resumeId=${application.resumeId}">
                        <b><c:out value="${resumeMappings[application.resumeId].title}"/></b>
                    </a>
                </td>
                <td><p>${application.createdDate}</p></td>
                <td><p><c:out value="${application.message}"/></p></td>
                <td><p class="text-<c:choose><c:when test="${application.statusId == ApplicationStatus.PENDING}">warning</c:when><c:when test="${application.statusId == ApplicationStatus.REJECTED || application.statusId == ApplicationStatus.CANCELED}">danger</c:when><c:when test="${application.statusId == ApplicationStatus.ACCEPTED}">success</c:when></c:choose>"><c:out value="${applicationStatuses[application.statusId - 1].name}"/></p></td>
                        <td class="d-flex">
                    <c:if test="${application.statusId == ApplicationStatus.PENDING}">
                        <c:if test="${account.accountTypeId == AccountType.RECRUITER}">
                            <form action="admin?action=updateapplicationstatus" method="POST">
                                <input hidden name="statusId" value="${ApplicationStatus.ACCEPTED}" />
                                <input hidden name="applicationId" value="${application.applicationId}" />
                                <button style="border: none; background-color: transparent;" class="text-success"><i class="fas fa-check"></i> Nhận</button>
                            </form>
                            <form action="admin?action=updateapplicationstatus" method="POST">
                                <input hidden name="statusId" value="${ApplicationStatus.REJECTED}" />
                                <input hidden name="applicationId" value="${application.applicationId}" />
                                <button style="border: none; background-color: transparent;" class="text-danger"><i class="fas fa-ban"></i> Loại</button>
                            </form>
                        </c:if>
                        <c:if test="${account.accountTypeId == AccountType.JOB_SEEKER}">
                            <form action="admin?action=updateapplicationstatus" method="POST">
                                <input hidden name="statusId" value="${ApplicationStatus.CANCELED}" />
                                <input hidden name="applicationId" value="${application.applicationId}" />
                                <button style="border: none; background-color: transparent;" class="text-danger"><i class="fas fa-times"></i> Hủy</button>
                            </form>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<c:if test="${empty applications}">
    <p class="text-center">Bạn chưa có đơn ứng tuyển nào</p>
</c:if>