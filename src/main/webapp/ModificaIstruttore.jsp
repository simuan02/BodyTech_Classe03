<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.entity.Istruttore" %>
<html>
<head>
    <title>BodyTech - Informazioni Utente</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
<%@include file="jsp/header.jsp"%>
<h1 id="profileTitle">DETTAGLI ISTRUTTORE DA MODIFICARE</h1>

<%
    Istruttore istr = (Istruttore) request.getAttribute("istruttore");
%>

<form action="ModificaIstruttore" method="post">
    <input type="hidden" name="MatricolaVecchia" value="<%=istr.getMatricolaIstruttore()%>">
    <div id="FirstName" class="InfoProfile">
        <label for="NomeIstruttore" id="Nome">Nome: </label>
            <input type="text" class="volumeEsercizio" value="<%=istr.getNome()%>" name="NomeIstruttore" id="NomeIstruttore">
    </div>
    <div id="LastName" class="InfoProfile">
        <label for="CognomeIstruttore" id="Cognome">Cognome: </label>
            <input type="text" class="volumeEsercizio" value="<%=istr.getCognome()%>" name="CognomeIstruttore" id="CognomeIstruttore">
    </div>

    <div id="Matricola" class="InfoProfile">
        <label for="MatricolaIstruttore">Matricola Istruttore: </label>
        <input type="text" class="volumeEsercizio" value="<%=istr.getMatricolaIstruttore()%>" name="MatricolaIstruttore"
               id="MatricolaIstruttore">
    </div>

    <div id="Specializzazione" class="InfoProfile">
        <label for="SpecializzazioneIstruttore">Specializzazione: </label>
        <input type="text" class="volumeEsercizio" value="<%=istr.getSpecializzazione()%>" name="SpecializzazioneIstruttore"
               id="SpecializzazioneIstruttore">
    </div><br>
    <input type="submit" id="EditButton" value="Invia le modifiche">
</div>
</form>
</body>
</html>

