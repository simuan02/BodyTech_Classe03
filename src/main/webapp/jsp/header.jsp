<%@ page import="bodyTech.model.entity.Profilo" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/header.css"/>
  <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
  <header class="header">
    <div class="logo_div">
      <a href="index.jsp">
        <img src="images/logoBodytech.png">
      </a>
    </div>
    <h1 id="titolo">BODYTECH</h1>

    <ul class="nav_menu">

      <li class="nav-item">
      <%
        Profilo p = (Profilo)session.getAttribute("Profilo");
        if (p == null) {
          %>
          <a class="nav-link" href="login.jsp">LOGIN</a>
        <%
        }
          else {
        %>
            <a href="profiloPage.jsp" class="nav-link"><%=p.getNome()%> <%=p.getCognome()%></a>
      <%
        }
      %>
      </li>
    </ul>

  </header>
</body>
</html>
