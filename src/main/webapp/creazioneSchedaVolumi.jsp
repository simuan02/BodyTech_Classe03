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
        <form action="CreazioneEserciziAllenamentoServlet?codiceFiscale=${utente.codiceFiscale}" method="post">

            <div class="esercizi">
                <%
                    List<Esercizio> listaEsercizi = (List<Esercizio>) request.getAttribute("listaEsercizi");
                    for (Esercizio esercizio : listaEsercizi){
                %>
                    <div class="esercizio">
                        <p><%=esercizio.getNomeEsercizio()%></p>
                        <input type="text" id="volume" name="volume">
                    </div>
                <%
                    }
                %>
            </div>
            <input type="submit" value="CREA">
        </form>
    </div>
</body>
</html>
