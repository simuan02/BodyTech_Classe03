<%@ page import="bodyTech.model.entity.RichiestaModificaScheda" %>
<%@ page import="bodyTech.model.dao.RichiestaModificaSchedaDAO" %>
<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.dao.UtenteDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Valutazione Richiesta</title>
    <link rel="stylesheet" href="css/valutazioneRichiesta.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="card">
        <p class="title">Richiesta ${utente.cognome}</p>
        <div id="informazioni">
            <p>Cognome: ${utente.cognome}</p>
            <p>Nome: ${utente.nome}</p>
            <p>Codice Fiscale: ${utente.codiceFiscale}</p>
        </div>

        <p class="title">Informazioni Richiesta</p>
        <div class="informazioni_richiesta">
            <p>ID : ${richiesta.idRichiesta}</p>
            <p>Messaggio : ${richiesta.messaggio}</p>
        </div>

        <div class="buttons">
                <div class="button">
                    <a href="valutaRichiesta?id=${richiesta.idRichiesta}&valutazione=true" class="noDecoration">
                        <p>Accetta Richiesta</p>
                    </a>
                </div>

                <div class="button" onclick="rifiutaRichiesta(${richiesta.idRichiesta}">
                    <a href="valutaRichiesta?id=${richiesta.idRichiesta}&valutazione=false" class="noDecoration">
                        <p>Respingi Richiesta</p>
                    </a>
                </div>
        </div>
    </div>

</body>


</html>
