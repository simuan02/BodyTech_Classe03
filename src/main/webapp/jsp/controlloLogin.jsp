<%--
  Created by IntelliJ IDEA.
  User: Limon
  Date: 12/09/2023
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object o = request.getAttribute("logged");
    if (o != null)
    {
%>
<script>alert("Identificativo e/o password errato")</script>
<%
    }
%>
</body>
</html>
