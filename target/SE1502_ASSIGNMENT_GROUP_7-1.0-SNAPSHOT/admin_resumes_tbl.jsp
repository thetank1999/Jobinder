<%-- 
    Document   : admin_resumes_tbl
    Created on : Mar 8, 2021, 8:42:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="d-flex justify-content-between">
    <h3>Các lý lịch của tôi</h3>
    <a style="text-decoration: none" href="admin?action=createresume" class="btn btn-dark">Thêm mới <i class="fas fa-plus"></i></a>
</div>
<table class="table table-responsive bg-dark mt-3" style="font-weight: 400; overflow: hidden">
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
    <c:forEach var="resume" items="${resumes}">
        <tr style="padding: 0 20px">
            <td><a style="text-decoration: none; color: black;" href="resumes?resumeId=${resume.resumeId}"><b><c:out value="${resume.title}"/></b></a></td>
            <td>${resume.views}</td>
            <td>
                <form action="admin?action=switchResumeStatus&resumeId=${resume.resumeId}" method="POST">
                    <button style="border: none; background-color: transparent;" type="submit">
                        <c:if test="${resume.status}">
                            <i class="fas fa-eye"></i> Hiện
                        </c:if>
                        <c:if test="${not resume.status}">
                            <i class="fas fa-eye-slash"></i> Ẩn
                        </c:if>
                    </button>
                </form>
            </td>
            <td><c:out value="${resume.position}"/></td>
        <td><c:out value="${resume.yearOfExperience}"/></td>
        <td><c:out value="${resume.lastModified}"/></td>
        <td class="d-flex">
            <a href="admin?action=editresume&resumeId=${resume.resumeId}" style="margin-right: 10px; text-decoration: none"><i class="fas fa-edit"></i> Sửa</a>
            <form action="admin?action=deleteresume&resumeId=${resume.resumeId}" method="POST">
                <button style="border: none; background-color: transparent;" class="text-danger" type="button" onclick="confirmDelete(this.parentElement)"><i class="fas fa-trash-alt"></i> Xóa</button>
            </form>
        </td>
        </tr>
    </c:forEach>
</tbody>
</table>
<c:if test="${empty resumes}">
    <p class="text-center">Bạn chưa có lý lịch nào</p>
</c:if>