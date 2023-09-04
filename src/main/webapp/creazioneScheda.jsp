<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 04/09/2023
  Time: 15:04
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
                <div id="data_inizio">
                    <p>Data inizio: </p>
                    <input type="date", hidden="gg/mm/aaaa" id="dataInizio" name="dataInizio">
                </div>

                <div id="data_fine">
                    <p>Data fine: </p>
                    <input type="date", hidden="gg/mm/aaaa" id="dataFine" name="dataFine">
                </div>
            </div>

            <div class="esercizi">
                <c:forEach items="${esercizi}" var="esercizio">
                    <div class="esercizio">
                        <input type="checkbox" id="${esercizio.nomeEsercizio}" name="esercizio">
                        <label for="${esercizio.nomeEsercizio}">${esercizio.nomeEsercizio}</label>
                    </div>
                </c:forEach>
            </div>
            <input type="submit" value="CONTINUA">
        </form>
    </div>
</body>
</html>
