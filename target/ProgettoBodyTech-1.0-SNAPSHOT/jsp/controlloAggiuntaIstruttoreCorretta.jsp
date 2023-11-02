<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object o = request.getAttribute("LunghezzaMatricolaErrata");
    if (o != null){
%>
<script>alert("Matricola inserita di lunghezza errata")</script>
<%
        ;}
    Object o2 = request.getAttribute("CodiceGiaPresente");
    if (o2 != null)
    {
%>
<script>alert("Matricola gia' presente nella piattaforma")</script>
<%
    };
%>
</body>
</html>
