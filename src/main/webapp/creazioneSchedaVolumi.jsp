<%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 04/09/2023
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Creazione Scheda</title>
    <link rel="stylesheet" href="css/creazioneScheda.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="card">
        <p id="title">Crazione Scheda</p>
        <div id="informazioni">
            <p>Cognome: ${utente.cognome}</p>
            <p>Nome: ${utente.nome}</p>
            <p>Codice Fiscale: ${utente.codiceFiscale}</p>
        </div>
        <form action="CreazioneSchedaServlet", method="post">

            <div class="date">
                <p id="tipo">Tipo Scheda: </p>
                <input type="text" id="tipo_input" name="tipo_input">
            </div>

            <div class="esercizi">
                <c:forEach items="${esercizi}" var="esercizio">
                    <div class="esercizio">
                        <p>${esercizio.nomeEsercizio}</p>
                        <input type="text" id="volume" name="volume">
                    </div>
                </c:forEach>
            </div>
            <input type="submit" value="CREA">
        </form>
    </div>
</body>
</html>
