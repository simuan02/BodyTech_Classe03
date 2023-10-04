<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%
    Object examinedRequest = request.getAttribute("richiestaGiaEsaminata");
    if (examinedRequest != null){
%>
<script>alert("Richiesta di Modifica Scheda già esaminata. Impossibile procedere")</script>
<%
        ;}
%>
<%
    Object requestApproved = request.getAttribute("valutazioneRichiesta");
    if (requestApproved != null){
        if ((Boolean)requestApproved == true) {
%>
<script>alert("Richiesta approvata con successo")</script>
<%
        }
        else {
%>
<script>alert("Richiesta respinta")</script>
<%
        ;}
    }
%>
</body>
</html>
