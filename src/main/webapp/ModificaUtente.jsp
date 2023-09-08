<%@ page import="bodyTech.model.entity.Utente" %>
<html>
<head>
    <title>BodyTech - Informazioni Utente</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
<%@include file="jsp/header.jsp"%>
<h1 id="profileTitle">DETTAGLI UTENTE DA MODIFICARE</h1>

<form action="ModificaUtente" method="post">
<div id="FirstName" class="InfoProfile">
    <label for="NomeUtente" id="Nome">Nome: </label>
        <input type="text" class="volumeEsercizio" value="<%=p.getNome()%>" name="NomeUtente" id="NomeUtente">
</div>
<div id="LastName" class="InfoProfile">
    <label for="CognomeUtente" id="Cognome">Cognome: </label>
        <input type="text" class="volumeEsercizio" value="<%=p.getCognome()%>" name="CognomeUtente" id="CognomeUtente">
</div>

<h2 id="Identifier" class="InfoProfile">Codice Fiscale: <%=p.getCodiceFiscale()%></h2>
</div>
</form>
</body>
</html>

