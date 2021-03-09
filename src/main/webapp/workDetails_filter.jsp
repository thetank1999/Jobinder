<%-- 
    Document   : base_filter
    Created on : Mar 1, 2021, 2:35:31 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="locationId">Nơi làm việc</label>
        <select id="locationId" class="form-select" name="locationId">
            <option value="-1">Tất cả</option>
            <c:forEach var="location" items="${locations}">
                <option <c:if test="${not empty locationId && locationId == location.locationId}">selected</c:if> value="${location.locationId}">${location.name}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <details <c:if test="${not empty languageId}">open</c:if> class="my-2">
                <summary class="p-2 border rounded">Ngôn ngữ thành thạo</summary>
                <c:forEach var="lang" items="${languages}">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" <c:if test="${not empty languageId && languageId.contains(lang.languageId)}">checked</c:if> name="languageId" value="${lang.languageId}" id="${lang.languageId}">
                    <label class="form-check-label" for="${lang.languageId}">
                        ${lang.name}
                    </label>
                </div>
            </c:forEach>
        </details>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="fieldId">Lĩnh vực</label>
        <select id="fieldId" class="form-select" name="fieldId">
            <option value="-1">Tất cả</option>
            <c:forEach var="field" items="${fields}">
                <option <c:if test="${not empty fieldId and fieldId == field.fieldId}">selected</c:if> value="${field.fieldId}">${field.name}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="levelId">Học vấn tối thiểu</label>
        <select id="levelId" class="form-select" name="levelId">
            <c:forEach var="level" items="${academicLevels}">
                <option <c:if test="${not empty levelId && levelId == level.levelId}">selected</c:if> value="${level.levelId}">${level.title}</option>
            </c:forEach>
        </select>
    </div>
</div>
<script src="js/workDetailUrlSearchParam.js"></script>