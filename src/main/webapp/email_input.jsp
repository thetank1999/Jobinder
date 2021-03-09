<%-- 
    Document   : email_input
    Created on : Mar 9, 2021, 9:55:44 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <title>Reset Password</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body class="bg-light d-flex flex-column justify-content-between" style="min-height: 100vh">
        <%@include file="header.jsp" %>
        <div class="container row my-3 d-flex justify-content-center m-auto">
            <div class="card bg-white col-lg-5 col-md-8">
                <div class="card-body">
                    <h3 class="card-title">Nhập email của tài khoản</h3>
                    <form action="resetpassword">
                        <fieldset class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control mb-3" id="email" name="email" required /> 
                        </fieldset>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="mt-2 w-100 btn btn-primary" >Tiếp tục</button>
                        </div>
                        <%@include file="notification.jsp" %>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="back_to_top.html" %>
        <%@include file="footer.html" %>
    </body>
</html>
