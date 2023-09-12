<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object o = request.getAttribute("Registrazione");
    if (o != null){
        boolean b = (boolean) o;
        if (b){
%>
<script>alert("Registrazione avvenuta con successo")</script>
<%
    ;}
else {
%>
<script>alert("Errore al momento della registrazione")</script>
<%
            ;}
    }
    Object o2 = request.getAttribute("CodiceGiaPresente");
    if (o2 != null)
    {
%>
<script>alert("Codice Fiscale gia' registrato alla piattaforma")</script>
<%
    };
%>

</body>
</html>
