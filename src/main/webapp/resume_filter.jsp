<%-- 
    Document   : resume_filter
    Created on : Feb 20, 2021, 9:38:59 PM
    Author     : Admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Optional"%>
<%@page import="models.common.AcademicLevel"%>
<%@page import="models.common.Field"%>
<%@page import="models.common.Location"%>
<%@page import="models.common.Language"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Integer ftAccountId = (Integer) request.getAttribute("accountId");
    String ftKeyword = (String) request.getAttribute("keyword");
    Integer ftMinYearOfExperience = (Integer) request.getAttribute("minYearOfExperience");
%>
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
                        <input type="number" id="accountId" value="<%=ftAccountId != null ? ftAccountId : ""%>" name="accountId" class="form-control w-100" />
                    </div>
                </div>
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label class="form-label d-block" for="keyword">Từ khóa</label>
                        <input type="search" value="<%=ftKeyword != null ? ftKeyword : ""%>" id="keyword" name="keyword" class="form-control w-100" />
                    </div>
                </div>
                <div class="form-row col-md-10">
                    <div class="form-group">
                        <label for="minYearOfExperience">Năm kinh nghiệm tối thiểu</label>
                        <input type="number" step="1" min="0" name="minYearOfExperience" value="<%=ftMinYearOfExperience != null ? ftMinYearOfExperience : ""%>" type="number" class="form-control" id="minYearOfExperience">
                    </div>
                </div>
                <%@include file="workDetail_filter.jsp" %>
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
