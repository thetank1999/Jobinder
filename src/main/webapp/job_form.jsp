<%-- 
    Document   : job_form
    Created on : Feb 28, 2021, 9:48:17 PM
    Author     : Admin
--%>

<%@page import="models.job.JobType"%>
<%@page import="models.job.Job"%>
<%@page import="java.util.Map"%>
<%@page import="models.common.AcademicLevel"%>
<%@page import="models.common.Field"%>
<%@page import="models.common.Location"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="models.common.Language"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
    String action = (String) request.getAttribute("action");
    Job job = (Job) request.getAttribute("job");
    List<JobType> jobTypes = (List<JobType>) request.getAttribute("jobTypes");
    List<Language> languages = (List<Language>) request.getAttribute("languages");
    List<Location> locations = (List<Location>) request.getAttribute("locations");
    List<Field> fields = (List<Field>) request.getAttribute("fields");
    List<AcademicLevel> academicLevels = (List<AcademicLevel>) request.getAttribute("academicLevels");
    Map<String, String> constraints = (Map<String, String>) request.getAttribute("constraints");

    boolean invalidDescription = constraints != null && constraints.containsKey("description");
    boolean invalidImage = constraints != null && constraints.containsKey("image");
    boolean isEdit = action.equals("editjob");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <script src="js/imagePreview.js"></script>
        <title>Job</title>
    </head>
    <body class="bg-light">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white col-lg-6 col-md-8">
                <div class="card-body">
                    <h3 class="card-title"><%=isEdit ? "Chỉnh sửa" : "Thêm mới"%> công việc</h3>
                    <form action="<%=request.getContextPath() + "/admin" + "?action=" + action%><%=isEdit ? "&jobId=" + job.getJobId() : ""%>" enctype="multipart/form-data" method="POST">
                        <fieldset class="form-group mb-3">
                            <label for="title">Tiêu đề</label>
                            <input type="text" class="form-control" id="title" name="title" value="<%=job != null ? job.getTitle() : ""%>" required /> 
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="description">Miêu tả</label>
                            <textarea class="form-control <%=invalidDescription ? "is-invalid" : ""%>" id="description" name="description" cols="5" required><%=job != null ? job.getDescription() : ""%></textarea>
                            <%if (invalidDescription) {%>
                            <div class="invalid-feedback">
                                <%=constraints.get("description")%>
                            </div>
                            <%}%>
                        </fieldset>
                        <fieldset class="form-group mb-3">
                            <label for="startingSalary">Lương khởi điểm (VNĐ)</label>
                            <input type="number" min="0" step="any" class="form-control" id="startingSalary" name="startingSalary" value="<%=job != null ? job.getStartingSalary() : ""%>" required />
                        </fieldset>
                        <label for="jobType">Loại công việc</label>
                        <select id="jobType" class="form-select mb-3" name="jobType">
                            <%for (JobType type : jobTypes) {%>
                            <option <%=job != null && job.getJobTypeId() == type.getJobTypeId() ? "selected" : ""%> value="<%=type.getJobTypeId()%>"><%=type.getName()%></option>
                            <%}%>
                        </select>
                        <fieldset class="form-group">
                            <details class="my-2" open="true">
                                <summary class="p-2 border rounded">Ngôn ngữ thành thạo</summary>
                                    <%for (Language lang : languages) {%>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" <%=job != null && job.getWorkDetails().getLanguageIds().contains(lang.getLanguageId()) ? "checked" : ""%> name="language" value="<%=lang.getLanguageId()%>" id="<%=lang.getLanguageId()%>">
                                    <label class="form-check-label" for="<%=lang.getLanguageId()%>">
                                        <%=lang.getName()%>
                                    </label>
                                </div>
                                <%}%>
                            </details>
                        </fieldset>
                        <label class="form-label mt-3" for="image">Ảnh công việc</label>
                        <input accept="image/*" onchange="showPreview(this)" name="image" type="file" class="form-control <%=invalidImage ? "is-invalid" : ""%>" value="<%=job != null ? job.getWorkDetails().getImageUri() : ""%>" id="image" required />
                        <%if (invalidImage) {%>
                        <div class="invalid-feedback">
                            <%=constraints.get("image")%>
                        </div>
                        <%}%>
                        <div class="w-50 mt-2" id="img-preview"></div>
                        <label class="mt-3" for="location">Nơi làm việc</label>
                        <select id="location" class="form-select" name="location">
                            <%for (Location location : locations) {%>
                            <option <%=job != null && job.getWorkDetails().getLocationId() == location.getLocationId() ? "selected" : ""%> value="<%=location.getLocationId()%>"><%=location.getName()%></option>
                            <%}%>
                        </select>
                        <label class="mt-3" for="field">Lĩnh vực</label>
                        <select id="field" class="form-select" name="field">
                            <%for (Field field : fields) {%>
                            <option <%=job != null && job.getWorkDetails().getFieldId() == field.getFieldId() ? "selected" : ""%> value="<%=field.getFieldId()%>"><%=field.getName()%></option>
                            <%}%>
                        </select>
                        <label class="mt-3" for="level">Trình độ học vấn</label>
                        <select id="level" class="form-select" name="level">
                            <%for (AcademicLevel level : academicLevels) {%>
                            <option <%=job != null && job.getWorkDetails().getLevelId() == level.getLevelId() ? "selected" : ""%> value="<%=level.getLevelId()%>"><%=level.getTitle()%></option>
                            <%}%>
                        </select>
                        <fieldset class="form-group mt-3">
                            <label>Tình trạng</label>
                            <div class="form-check">
                                <input class="form-check-input" value="true" <%=job == null || job.getStatus() ? "checked" : ""%> type="radio" name="status" id="status_true" >
                                <label class="form-check-label" for="status_true">
                                    Hiện
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" value="false" <%=job != null && !job.getStatus() ? "checked" : ""%> type="radio" name="status" id="status_false" >
                                <label class="form-check-label" for="status_false">
                                    Ẩn
                                </label>
                            </div>
                        </fieldset>
                        <%@include file="notification.jsp" %>
                        <button type="submit" class="mt-2 btn btn-primary w-100 d-inline-block text-center" >Lưu</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
