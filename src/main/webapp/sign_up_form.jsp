<%-- 
    Document   : SignUpForm
    Created on : Feb 10, 2021, 8:59:19 PM
    Author     : Admin
--%>

<%@page import="java.util.Map"%>
<%@page import="models.account.Account"%>
<%@page import="java.util.List"%>
<%@page import="models.account.AccountType"%>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
    Account account = (Account) request.getAttribute("account");
    List<AccountType> accountTypes = (List<AccountType>) request.getAttribute("accountTypes");
    Map<String, String> constraints = (Map<String, String>) request.getAttribute("constraints");

    boolean invalidEmail = constraints != null && constraints.containsKey("email");
    boolean invalidPassword = constraints != null && constraints.containsKey("password");
    boolean invalidPhoneNumber = constraints != null && constraints.containsKey("phoneNumber");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobinder</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white  col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Đăng kí</h3>
                    <form action="signup" method="POST">
                        <fieldset class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control<%=invalidEmail ? " is-invalid" : ""%>" id="email" name="email" value="<%=account != null ? account.getEmail() : ""%>" required /> 
                            <%if (invalidEmail) {%>
                            <div class="invalid-feedback">
                                <%=constraints.get("email")%>
                            </div>
                            <%}%>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="password">Mật khẩu</label>
                            <input type="password" class="form-control<%=invalidPassword ? " is-invalid" : ""%>" id="password" name="password" value="<%=account != null ? account.getPassword() : ""%>" required />
                            <%if (invalidPassword) {%>
                            <div class="invalid-feedback">
                                <%=constraints.get("password")%>
                            </div>
                            <%}%>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="phoneNumber">Số điện thoại</label>
                            <input type="text" class="form-control<%=invalidPhoneNumber ? " is-invalid" : ""%>" id="phoneNumber" name="phoneNumber" value="<%=account != null ? account.getPhoneNumber() : ""%>" required />
                            <%if (invalidPhoneNumber) {%>
                            <div class="invalid-feedback">
                                <%=constraints.get("phoneNumber")%>
                            </div>
                            <%}%>
                        </fieldset>
                        <fieldset class="form-group mt-3">
                            <label for="name">Họ tên</label>
                            <input type="text" class="form-control" id="name" name="name" value="<%=account != null ? account.getName() : ""%>" required />
                        </fieldset>
                        <label class="mt-3" for="accountType">Bạn là</label>
                        <select id="accountType" class="form-select" name="accountType">
                            <%for (AccountType type : accountTypes) {%>
                            <option <%=account != null && account.getAccountTypeId() == type.getAccountTypeId() ? "selected" : ""%> value="<%=type.getAccountTypeId()%>"><%=type.getName()%></option>
                            <%}%>
                        </select>
                        <%@include file="notification.jsp" %>
                        <button type="submit" class="mt-2 btn btn-primary" >Tạo tài khoản</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
    </body>
</html>
