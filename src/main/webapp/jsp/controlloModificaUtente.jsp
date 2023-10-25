<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <%
        Object o = request.getAttribute("LunghezzaCodiceErrata");
        if (o != null){
    %>
<script>alert("Codice Fiscale inserito di lunghezza errata")</script>
    <%
        ;}
        Object o2 = request.getAttribute("CodiceGiaPresente");
        if (o2 != null)
        {
    %>
<script>alert("Codice Fiscale gia' registrato alla piattaforma")</script>
<%
        };
        Object o3 = request.getAttribute("LunghezzaNomeErrata");
        if (o3 != null){
    %>
    <script>alert("Nome inserito di lunghezza errata")</script>
<%
        ;}
        Object o4 = request.getAttribute("LunghezzaCognomeErrata");
        if (o4 != null){
    %>
    <script>alert("Cognome inserito di lunghezza errata")</script>
<%
        ;}
%>
</body>
</html>
