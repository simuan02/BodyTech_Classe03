
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HomeGym - Login</title>
    <link rel="icon" href="./images/icons/logo.ico">
    <link rel="stylesheet" href="./css/login.css" type="text/css">
    <script type="application/javascript" src="./scripts/validation.js"></script>
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="container-login">
        <div class="box">
            <h1 id="title">Login</h1>
            <form action="LoginServlet" method="post" onsubmit="return validate()">
                <input type="email" id="emailId" name="email" placeholder="E-mail" required><br>
                <input type="password" id="passwordId" name="password" placeholder="Password" required><br>
                <p id="registrati"><a href="${pageContext.request.contextPath}/RegServlet">Non sei ancora registrato? Registrati ora</a></p>
                <input type="submit" value="Login">
            </form>
        </div>

    </div>

<script>

    function validate() {
        var email = document.getElementById("emailId").value;
        var password = document.getElementById("passwordId").value;

        return emailValidate(email) && passwordValidate(password);
    }

</script>
</body>
</html>
