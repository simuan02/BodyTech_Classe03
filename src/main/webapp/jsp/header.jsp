<%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 09/08/2023
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/header.css"/>
</head>
<body>
  <header class="header">
    <!--<div class="hamburger">
      <img src="./images/menu.png">
    </div>-->
    <div class="logo_div">
      <img src="images/logo.jpg">
    </div>
    <h1 id="titolo">BODYTECH</h1>

    <ul class="nav_menu">
      <!--<c:choose>
        <c:when test="${profilo == null}">

        </c:when>
        <c:otherwise>
          <li class="nav-item">
            <a href="${pageContext.request.contextPath}/LogServlet" class="nav-link">Nome Account</a>
          </li>
        </c:otherwise>

      </c:choose>-->

      <li class="nav-item">
        <a href="http://localhost:8080/ProgettoBodyTech_war_exploded/LogServlet" class="nav-link">Login</a>
      </li>
    </ul>

  </header>
</body>
</html>
