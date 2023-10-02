<%@ page import="bodyTech.model.entity.Esercizio" %>
<%@ page import="java.util.List" %>
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
        <p id="title">Creazione Scheda</p>
        <div id="informazioni">
            <p>Cognome: ${utente.cognome}</p>
            <p>Nome: ${utente.nome}</p>
            <p>Codice Fiscale: ${utente.codiceFiscale}</p>
        </div>
        <form action="CreazioneSchedaServlet?cf=${utente.codiceFiscale}" method="post">

            <div class="date">
                <p id="tipo">Tipo Scheda: </p>
                <input type="text" id="tipo_input" name="tipo_input" required>
            </div>

            <div class="date">
                <div id="data_inizio">
                    <p>Data inizio: </p>
                    <input type="date" id="dataInizio" name="dataInizio" required>
                </div>

                <div id="data_fine">
                    <p>Data fine: </p>
                    <input type="date" id="dataFine" name="dataFine" required>
                </div>
            </div>

            <div class="esercizi">
                <%
                    List<Esercizio> esercizi = (List<Esercizio>)request.getAttribute("esercizi");
                    for (Esercizio esercizio: esercizi) {
                %>
                    <div class="esercizio">
                        <input type="checkbox" id="<%=esercizio.getNomeEsercizio()%>" name="esercizio"
                               value="<%=esercizio.getNomeEsercizio()%>">
                        <label for="<%=esercizio.getNomeEsercizio()%>"><%=esercizio.getNomeEsercizio()%></label>
                    </div>
                <%
                    }
                %>
            </div>
            <input type="submit" value="CONTINUA">
        </form>
    </div>
</body>
</html>
