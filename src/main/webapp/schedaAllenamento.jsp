<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="bodyTech.model.entity.EsercizioAllenamento" %>
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
            <%
                SchedaAllenamento sa = (SchedaAllenamento)request.getAttribute("scheda");
                List<EsercizioAllenamento> esercizi = sa.getListaEsercizi();
                for (EsercizioAllenamento esercizio : esercizi){
            %>

                <div class="esercizio">
                    <p><%=esercizio.getNomeEsercizio()%> : <%=esercizio.getVolume()%></p>
                    <p class="descrizione"><%=esercizio.getDescrizione()%></p>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
