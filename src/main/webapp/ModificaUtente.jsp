<%@ page import="bodyTech.model.entity.Utente" %>
<html>
<head>
    <title>BodyTech - Modifica Utente ${Utente.codiceFiscale}</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
<%@include file="jsp/header.jsp"%>
<h1 id="profileTitle">DETTAGLI UTENTE DA MODIFICARE</h1>

<%
    Utente u = (Utente)request.getAttribute("Utente");
%>

<form action="ModificaUtente" method="post">
    <input type="hidden" name="CodiceFiscaleVecchio" value="<%=u.getCodiceFiscale()%>">
    <div id="FirstName" class="InfoProfile">
        <label for="NomeUtente" class="FormLabel">Nome: </label>
            <input type="text" class="FormInput" value="<%=u.getNome()%>" name="NomeUtente" id="NomeUtente" required>
    </div>
    <div id="LastName" class="InfoProfile">
        <label for="CognomeUtente" class="FormLabel">Cognome: </label>
            <input type="text" class="FormInput" value="<%=u.getCognome()%>" name="CognomeUtente" id="CognomeUtente" required>
    </div>

    <div id="CF" class="InfoProfile">
        <label for="CodiceFiscaleUtente" class="FormLabel">Codice Fiscale: </label>
        <input type="text" class="FormInput" value="<%=u.getCodiceFiscale()%>" name="CodiceFiscaleUtente"
               id="CodiceFiscaleUtente" required>
    </div><br>
    <input type="submit" id="EditButton" value="Invia le modifiche">
</div>
</form>
</body>
</html>

