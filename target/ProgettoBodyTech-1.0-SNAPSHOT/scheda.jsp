<%@ page import="bodyTech.model.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Scheda di Allenamento</title>
    <link rel="icon" href="images/logo.jpg" sizes="any">
    <link rel="stylesheet" type="text/css" href="css/profiloPage.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="scripts.js"></script>
</head>
<body>
<%@include file="jsp/header.jsp"%>
<%
    if ((p==null) || !(p.loggedUserLevel().equals("Utente")))
        response.sendError(403, "Richiesta non autorizzata");
    SchedaAllenamento sa = (SchedaAllenamento) request.getAttribute("SchedaAllenamento");
    if (sa == null)
    {
%>
        <h1 id="profileTitle">Nessuna Scheda Allenamento Presente</h1>
<%
    }
    else
    {
%>
        <h1 id="profileTitle">Dettagli Scheda Allenamento</h1>
        <div id="StartDate" class="InfoProfile">
            <h2 id="DataInizio">Data Inizio: <%=sa.getDataInizio()%></h2>
        </div>
        <div id="FinishDate" class="InfoProfile">
            <h2 id="DataCompletamento">Data Completamento: <%=sa.getDataCompletamento()%></h2>
        </div>
        <div id="TrainingType" class="InfoProfile">
            <h2>Tipo: <%=sa.getTipo()%></h2>
        </div>
        <div id="AssociatedInstructor" class="InfoProfile">
            <h2 id="IstruttoreAssociato">Istruttore Associato: <%=sa.getIstruttore().getNome()%> <%=sa.getIstruttore().getCognome()%></h2>
        </div>
        <%
            if (sa.getListaEsercizi().size() > 0)
            {
        %>
                <div id="ExercisesList" class="ExercisesAndRequestsList">
                    <table id="ExercisesTable" class="ExercisesAndRequestsTable">
                        <caption id="ListaEserciziTitle" class="ListaEserciziAndRichiesteTitle">Lista Esercizi</caption>
                        <tr>
                            <th class="NomeEsercizioCell CellTable">Nome Esercizio</th>
                            <th class="DescrizioneCell CellTable">Descrizione</th>
                            <th class="VolumeCell CellTable">Volume</th>
                        </tr>
                        <%
                            for (EsercizioAllenamento es: sa.getListaEsercizi())
                            {
                        %>
                        <tr>
                            <td class="NomeEsercizioCell CellTable"><%=es.getNomeEsercizio()%></td>
                            <td class="DescrizioneCell CellTable"><%=es.getDescrizione()%></td>
                            <td class="VolumeCell CellTable"><%=es.getVolume()%></td>
                        </tr>
                            <%}%>
                    </table>
                </div>
        <%
            }
        %>
        <div class="CentralButtons">
            <button id="editRequestButton" class="requestButtons">Apri una richiesta di modifica</button>
            <button id="showRequestButton" class="requestButtons">Visualizza le richieste di modifica effettuate</button>
        </div>
<%
    }
%>
</body>
</html>
