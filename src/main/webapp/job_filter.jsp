<%-- 
    Document   : job_filter
    Created on : Mar 1, 2021, 2:35:11 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<div class="card shadow col-lg-4 p-0 mt-2">
    <style scoped>
        @media all and (max-width: 1200px) {
            .card-body {
                display: flex;
                flex-direction: column;
                align-content: center;
            }
        }
    </style>
    <article class="card-group-item ml-2">
        <header class="card-header bg-light">
            <h6 class="title">Bộ lọc</h6>
        </header>
        <div class="filter-content">
            <div class="card-body row">
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label class="form-label d-block" for="accountId">Tài khoản ID</label>
                        <input type="number" id="accountId" value="${accountId}" name="accountId" class="form-control w-100" />
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
                        <label for="minSalary">Lương tối thiểu</label>
                        <input type="number" step="any" min="0" name="minSalary" value="<c:out value="${minSalary}"/>" type="number" class="form-control" id="minSalary">
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
        const minSalary = document.getElementById("minSalary").value;

        const searchParams = new URLSearchParams();
        buildWorkDetailUSP(searchParams);

        if (accountId) {
            searchParams.append("accountId", accountId);
        }

        if (keyword) {
            searchParams.append("keyword", keyword);
        }

        if (minSalary) {
            searchParams.append("minSalary", minSalary);
        }



        const searchString = searchParams.toString();

        window.location.replace("jobs" + (searchString ? "?" : "") + searchString);
    }

</script>
