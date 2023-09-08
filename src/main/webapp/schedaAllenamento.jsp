<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 07/09/2023
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Scheda Allenamento ${utente.cognome}</title>
    <link rel="stylesheet" href="css/schedaAllenamento.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="card">
        <p class="title">Scheda ${utente.cognome}</p>
        <div id="informazioni">
            <p>Cognome: ${utente.cognome}</p>
            <p>Nome: ${utente.nome}</p>
            <p>Codice Fiscale: ${utente.codiceFiscale}</p>
        </div>

        <p>Tipo scheda: ${scheda.tipo}</p>

        <div class="date">
            <p id="data_inizio">Data inizio: ${scheda.dataInizio}</p>
            <p id="data_fine">Data fine: ${scheda.dataCompletamento}</p>
        </div>

        <p class="title">Esercizi</p>

        <div class="esercizi">
            <c:forEach items="${scheda.listaEsercizi}" var="esercizio">
                <div class="esercizio">
                    <p>${esercizio.nomeEsercizio} : ${esercizio.volume}</p>
                    <p class="descrizione">${esercizio.descrizione}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
