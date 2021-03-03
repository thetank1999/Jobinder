<%-- 
    Document   : login
    Created on : Jan 12, 2021, 9:26:30 AM
    Author     : Admin
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"%>
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
            <div class="card bg-white col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Đăng nhập</h3>
                    <form action="signin" method="POST">
                        <fieldset class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control mb-3" id="email" name="email" required /> 
                        </fieldset>
                        <fieldset class="form-group">
                            <label for="password">Mật khẩu</label>
                            <input type="password" class="form-control" id="password" name="password" required />
                        </fieldset>

                        <%@include file="notification.jsp" %>
                        <button type="submit" class="mt-2 btn btn-primary" >Đăng nhập</button>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
    </body>
</html>