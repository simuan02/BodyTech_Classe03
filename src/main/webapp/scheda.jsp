<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Scheda di Allenamento</title>
    <link rel="stylesheet" type="text/css" href="css/profiloPage.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="scripts.js"></script>
</head>
<body>
<%@include file="jsp/header.jsp"%>
<%
    if ((p==null) || !(p.loggedUserLevel().equals("Utente")))
        response.sendError(403, "Richiesta non autorizzata");
    SchedaAllenamento sa = (SchedaAllenamento) request.getAttribute("SchedaAllenamento");
    if (sa == null)
    {
%>
        <h1 id="profileTitle">Nessuna Scheda Allenamento Presente</h1>
<%
    }
    else
    {
%>
        <h1 id="profileTitle">Dettagli Scheda Allenamento</h1>
        <div id="StartDate" class="InfoProfile">
            <h2 id="DataInizio">Data Inizio: <%=sa.getDataInizio()%></h2>
        </div>
        <div id="FinishDate" class="InfoProfile">
            <h2 id="DataCompletamento">Data Completamento: <%=sa.getDataCompletamento()%></h2>
        </div>
        <div id="TrainingType" class="InfoProfile">
            <h2 id="TipoScheda">Tipo: <%=sa.getTipo()%></h2>
        </div>
        <div id="AssociatedInstructor" class="InfoProfile">
            <h2 id="IstruttoreAssociato">Istruttore Associato: <%=sa.getIstruttore().getNome()%> <%=sa.getIstruttore().getCognome()%></h2>
        </div>
<div class="CentralButtons"><button id="editRequestButton" class="requestButtons">Apri una richiesta di modifica</button></div>
<%
    }
%>
</body>
</html>
