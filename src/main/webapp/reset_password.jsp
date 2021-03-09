<%-- 
    Document   : reset_password
    Created on : Mar 9, 2021, 8:39:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
        <title>JSP Page</title>
    </head>
    <body>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Đặt lại mật khẩu</h3>
                    <form action="resetpassword" method="POST">
                        <input hidden name="accountId" value="${accountId}" />
                        <input hidden name="encryptedCode" value="${encryptedCode}" />
                        <fieldset class="form-group">
                            <label for="code">Mật mã</label>
                            <input type="text" class="form-control mb-3" id="code" name="code" required /> 
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="password">Mật khẩu mới</label>
                            <input type="password" class="form-control mb-3 <c:if test="${not empty constraints.password}">is-invalid</c:if>" id="password" name="password" id="password" value="<c:out value="${password}"/>" required />
                            <c:if test="${not empty constraints.password}">
                                <div class="invalid-feedback">
                                    ${constraints.password}
                                </div>
                            </c:if>    
                        </fieldset>
                        <fieldset id="repeatPasswordField" class="form-group">
                            <label for="passwordRepeat">Nhập lại mật khẩu mới</label>
                            <input type="password" class="form-control mb-3" id="passwordRepeat" name="passwordRepeat" id="passwordRepeat" required />
                        </fieldset>
                        <%@include file="notification.jsp" %>
                        <button type="button" onclick="onSubmit()" class="mt-2 btn btn-primary" >Gửi yêu cầu</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
    <script>
        function onSubmit() {
            const password = document.getElementById("password");
            const passwordRepeat = document.getElementById("passwordRepeat");
            if (password.value !== passwordRepeat.value) {
                passwordRepeat.classList.add("is-invalid");
                const repeatPasswordField = document.getElementById("repeatPasswordField");
                const errorDiv = document.createElement("div")
                errorDiv.classList.add("invalid-feedback")
                errorDiv.innerHTML = "Mật khẩu nhập lại không khớp";
                repeatPasswordField.appendChild(errorDiv);
            } else {
                document.forms[0].requestSubmit();
            }
        }
    </script>
</html>
