
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Login</title>
    <link rel="icon" href="images/logo.jpg" sizes="any">
    <link rel="stylesheet" href="./css/login.css" type="text/css">
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="container-login">
        <div class="box">
            <h1 id="title">Login</h1>
            <form action="ProfileLogin" method="post">
                <input type="text" id="identifier" name="identifier" placeholder="Identificativo" required><br>
                <input type="password" id="passwordId" name="password" placeholder="Password" required><br>
                <p id="registrati"><a href="userRegistration.jsp">Non sei ancora registrato? Registrati ora</a></p>
                <input type="submit" value="Login">
            </form>
        </div>

    </div>
</body>
</html>
