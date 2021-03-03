<%-- 
    Document   : error
    Created on : Feb 20, 2021, 2:16:44 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style="height: 100vh; display: flex; justify-content: center; align-items: center; flex-direction: column">
        <h1 class="display-2">500</h1>
        <h4>Oops! Server Error</h4>
        <p style="max-width: 40vw; text-align: center">Hmm... Server crashed. Apologies.</p>
        <a class="btn text-white btn-primary" style="min-width: 10vw" href=".">Back to home</a>
    </body>
</html>
