<%-- 
    Document   : work_details_form
    Created on : Mar 9, 2021, 12:42:24 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<fieldset class="form-group">
    <details class="my-2" open="true">
        <summary class="p-2 border rounded">
            Ngôn ngữ thành thạo
        </summary>
        <c:forEach var="lang" items="${languages}">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" <c:if test="${not empty job && job.workDetails.languageIds.contains(lang.languageId)}">checked</c:if> name="language" value="${lang.languageId}" id="${lang.languageId}">
                <label class="form-check-label" for="${lang.languageId}">
                    ${lang.name}
                </label>
            </div>
        </c:forEach>
    </details>
</fieldset>
<label class="form-label mt-3" for="image">Ảnh chân dung</label>
<input accept="image/*" onchange="showPreview(this)" name="image" type="file" class="form-control <c:if test="${not empty constraints.image}">is-invalid</c:if>" id="image" required />
<c:if test="${not empty constraints.image}">
    <div class="invalid-feedback">
        ${constraints.image}
    </div>
</c:if>
<div class="w-50 mt-2" id="img-preview"></div>
<label class="mt-3" for="location">Nơi làm việc</label>
<select id="location" class="form-select" name="location">
    <c:forEach var="location" items="${locations}">
        <option <c:if test="${not empty job && job.workDetails.locationId == location.locationId}">selected</c:if> value="${location.locationId}">${location.name}</option>
    </c:forEach>
</select>
<label class="mt-3" for="field">Lĩnh vực</label>
<select id="field" class="form-select" name="field">
    <c:forEach var="field" items="${fields}">
        <option <c:if test="${not empty job && job.workDetails.fieldId == field.fieldId}">selected</c:if> value="${field.fieldId}">${field.name}</option>
    </c:forEach>
</select>
<label class="mt-3" for="level">Trình độ học vấn</label>
<select id="level" class="form-select" name="level">
    <c:forEach var="level" items="${academicLevels}">
        <option <c:if test="${not empty job && job.workDetails.levelId == level.levelId}">selected</c:if> value="${level.levelId}">${level.title}</option>
    </c:forEach>
</select>
