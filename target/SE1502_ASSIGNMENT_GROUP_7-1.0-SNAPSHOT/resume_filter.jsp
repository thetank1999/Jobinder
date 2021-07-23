<%-- 
    Document   : resume_filter
    Created on : Feb 20, 2021, 9:38:59 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<div class="shadow card col-lg-4 p-0 mt-2">
    <style scoped>
        @media all and (max-width: 1200px) {
            .card-body {
                display: flex;
                flex-direction: column;
                align-content: center;
            }
        }
    </style>
    <article class="card-group-item">
        <header class="card-header bg-light">
            <h6 class="title">Bộ lọc</h6>
        </header>
        <div class="filter-content">
            <div class="card-body row">
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label class="form-label d-block" for="accountId">Tài khoản ID</label>
                        <input type="number" id="accountId" value="<c:out value="${accountId}"/>" name="accountId" class="form-control w-100" />
                    </div>
                </div>
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label class="form-label d-block" for="keyword">Từ khóa</label>
                        <input type="search" value="<c:out value="${keyword}"/>" id="keyword" name="keyword" class="form-control w-100" />
                    </div>
                </div>
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label for="minYearOfExperience">Năm kinh nghiệm tối thiểu</label>
                        <input type="number" step="1" min="0" name="minYearOfExperience" value="<c:out value="${minYearOfExperience}"/>" type="number" class="form-control" id="minYearOfExperience">
                    </div>
                </div>
                <%@include file="workDetails_filter.jsp" %>
                <div class="form-row col-md-10 mt-2">
                    <button onclick="submitFilterForm()" class="btn btn-dark w-100 text-center">Tìm kiếm <i class="fas fa-search"></i></button>
                </div>
            </div> 
        </div>
    </article>
</div>
<script>
    function submitFilterForm() {
        const accountId = document.getElementById("accountId").value;
        const keyword = document.getElementById("keyword").value.replace(/^\s+|\s+$/g, '');
        const minYearOfExp = document.getElementById("minYearOfExperience").value;

        const searchParams = new URLSearchParams();
        buildWorkDetailUSP(searchParams);

        if (accountId) {
            searchParams.append("accountId", accountId);
        }

        if (keyword) {
            searchParams.append("keyword", keyword);
        }

        if (minYearOfExp && parseInt(minYearOfExp) > 0) {
            searchParams.append("minYearOfExperience", minYearOfExp);
        }



        const searchString = searchParams.toString();

        window.location.replace("resumes" + (searchString ? "?" : "") + searchString);
    }

</script>
