<%-- 
    Document   : base_filter
    Created on : Mar 1, 2021, 2:35:31 PM
    Author     : Admin
--%>

<%@page import="models.common.AcademicLevel"%>
<%@page import="models.common.Field"%>
<%@page import="models.common.Location"%>
<%@page import="models.common.Language"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Language> languages = (List<Language>) request.getAttribute("languages");
    List<Location> locations = (List<Location>) request.getAttribute("locations");
    List<Field> fields = (List<Field>) request.getAttribute("fields");
    List<AcademicLevel> academicLevels = (List<AcademicLevel>) request.getAttribute("academicLevels");

    Integer ftLocationId = (Integer) request.getAttribute("locationId");
    List<Integer> ftLanguageIds = (List<Integer>) request.getAttribute("languageId");
    Integer ftFieldId = (Integer) request.getAttribute("fieldId");
    Integer ftLevelId = (Integer) request.getAttribute("levelId");
%>
<!DOCTYPE html>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="locationId">Nơi làm việc</label>
        <select id="locationId" class="form-select" name="locationId">
            <option value="-1">Tất cả</option>
            <%for (Location location : locations) {%>
            <option <%=ftLocationId != null && location.getLocationId() == ftLocationId ? "selected" : ""%> value="<%=location.getLocationId()%>"><%=location.getName()%></option>
            <%}%>
        </select>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <details <%=ftLanguageIds != null ? "open" : ""%> class="my-2">
            <summary class="p-2 border rounded">Ngôn ngữ thành thạo</summary>
                <%for (Language lang : languages) {%>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" <%=ftLanguageIds != null && ftLanguageIds.contains(lang.getLanguageId()) ? "checked" : ""%> name="languageId" value="<%=lang.getLanguageId()%>" id="<%=lang.getLanguageId()%>">
                <label class="form-check-label" for="<%=lang.getLanguageId()%>">
                    <%=lang.getName()%>
                </label>
            </div>
            <%}%>
        </details>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="fieldId">Lĩnh vực</label>
        <select id="fieldId" class="form-select" name="fieldId">
            <option value="-1">Tất cả</option>
            <%for (Field field : fields) {%>
            <option <%=ftFieldId != null && ftFieldId == field.getFieldId() ? "selected" : ""%> value="<%=field.getFieldId()%>"><%=field.getName()%></option>
            <%}%>
        </select>
    </div>
</div>
<div class="form-row col-md-10">
    <div class="form-group">
        <label for="levelId">Học vấn tối thiểu</label>
        <select id="levelId" class="form-select" name="levelId">
            <%for (AcademicLevel level : academicLevels) {%>
            <option <%=ftLevelId != null && ftLevelId == level.getLevelId() ? "selected" : ""%> value="<%=level.getLevelId()%>"><%=level.getTitle()%></option>
            <%}%>
        </select>
    </div>
</div>
<script src="js/workDetailUrlSearchParam.js"></script>