<%-- 
    Document   : apply_job
    Created on : Mar 4, 2021, 8:01:45 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${not empty resumes}">
    <div class="row p-5" >
        <div class="col-md-3">
            <h3>Ứng tuyển công việc này</h3>
        </div>

        <div class="col-9" style="z-index: 1;">
            <fieldset class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" value="<c:out value="${account.email}"/>" readonly /> 
            </fieldset>
            <fieldset class="form-group mt-3">
                <label for="phoneNumber">Số điện thoại</label>
                <input type="text" class="form-control" value="<c:out value="${account.phoneNumber}"/>" readonly />
            </fieldset>
            <fieldset class="form-group mt-3">
                <label for="name">Họ tên</label>
                <input type="text" class="form-control" value="<c:out value="${account.name}"/>" readonly />
            </fieldset>
            <form action="admin?action=apply" method="POST">
                <input name="jobId" value="${job.jobId}" hidden />
                <label class="mt-3" for="resume">Lý lịch</label>
                <select id="resumeId" class="form-select" name="resumeId">
                    <c:forEach var="resume" items="${resumes}">
                        <option value="${resume.resumeId}"><c:out value="${resume.title}"/></option>
                    </c:forEach>
                </select>
                <fieldset class="form-group mb-3">
                    <label for="message">Lời nhắn</label>
                    <textarea class="form-control" id="message" name="message" cols="5"></textarea>
                </fieldset>
                <%@include file="notification.jsp" %>
                <button type="submit" class="mt-2 btn btn-primary w-100" >Nộp đơn ứng tuyển</button>
            </form>
        </div>
    </div>    
</c:if>
<c:if test="${empty resumes}">
    <p class="text-secondary text-center mt-3">Bạn phải có ít nhất 1 lý lịch mới có thể ứng tuyển cho công việc này</p>
</c:if>