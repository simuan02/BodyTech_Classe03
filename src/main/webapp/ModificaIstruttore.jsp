<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.entity.Istruttore" %>
<html>
<head>
    <title>BodyTech - Modifica Istruttore ${istruttore.matricolaIstruttore}</title>
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
        <label for="NomeIstruttore" class="FormLabel">Nome: </label>
            <input type="text" class="FormInput" value="<%=istr.getNome()%>" name="NomeIstruttore" id="NomeIstruttore" required>
    </div>
    <div id="LastName" class="InfoProfile">
        <label for="CognomeIstruttore" class="FormLabel">Cognome: </label>
            <input type="text" class="FormInput" value="<%=istr.getCognome()%>" name="CognomeIstruttore" id="CognomeIstruttore" required>
    </div>

    <div id="Matricola" class="InfoProfile">
        <label for="MatricolaIstruttore" class="FormLabel">Matricola Istruttore: </label>
        <input type="text" class="FormInput" value="<%=istr.getMatricolaIstruttore()%>" name="MatricolaIstruttore"
               id="MatricolaIstruttore" required>
    </div>

    <div id="Specializzazione" class="InfoProfile">
        <label for="SpecializzazioneIstruttore" class="FormLabel">Specializzazione: </label>
        <input type="text" class="FormInput" value="<%=istr.getSpecializzazione()%>" name="SpecializzazioneIstruttore"
               id="SpecializzazioneIstruttore">
    </div><br>
    <input type="submit" id="EditButton" value="Invia le modifiche">
</div>
</form>
</body>
</html>

