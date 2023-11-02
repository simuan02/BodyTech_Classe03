<%@ page import="bodyTech.model.entity.Amministratore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object o = session.getAttribute("Amministratore");
    if (o == null) {
        response.sendError(403, "ACCESSO NON AUTORIZZATO");
    }
%>

</body>
</html>
