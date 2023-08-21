<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Registration</title>
    <link rel="icon" href="images/logo.jpg" sizes="any">
    <link rel="stylesheet" href="./css/login.css" type="text/css">
</head>
<body>
<%@include file="jsp/header.jsp"%>

<div class="container-login">
    <div class="box">
        <h1 id="title">Registrazione Utente</h1>
        <form action="UserRegistration" method="post">
            <input type="text" id="Nome" name="Nome" placeholder="Nome" required><br>
            <input type="text" id="Cognome" name="Cognome" placeholder="Cognome" required><br>
            <input type="text" id="Identifier" name="Identifier" placeholder="Codice Fiscale" required><br>
            <input type="password" id="passwordId" name="password" maxlength="32" placeholder="Password" onchange="validatePassword()" required><br>
            <p id="registrati"><a href="login.jsp">Sei già registrato? Accedi ora!</a></p>
            <input type="submit" value="Registrati">
        </form>
    </div>

</div>
</body>
</html>
