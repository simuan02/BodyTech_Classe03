<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.entity.EsercizioAllenamento" %>
<%@ page import="bodyTech.model.entity.Istruttore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Lista Schede</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="scripts.js"></script>
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <%
        List<SchedaAllenamento> listaSchede = (List<SchedaAllenamento>) request.getAttribute("listaSchede");
    %>
    <div id="ListaSchedeContainer">
        <%
            int i = 0;
            for (SchedaAllenamento sa: listaSchede)
            {
        %>
        <div class="SchedaAllenamento" id="SchedaAllenamento<%=i%>">
            <h2>Scheda N°: <%=sa.getIdScheda()%></h2>
            <h4>Utente: <%=sa.getUtente().getNome()%> <%=sa.getUtente().getCognome()%> - <%=sa.getUtente().getCodiceFiscale()%><br>
                Istruttore: <%=sa.getIstruttore().getNome()%> <%=sa.getIstruttore().getCognome()%>
            </h4>
            <div id="OtherCardInfo">
                <h4>
                    Tipo Scheda: <%=sa.getTipo()%><br>
                    Data Inizio: <%=sa.getDataInizio()%><br>
                    Data di Completamento Prevista: <%=sa.getDataCompletamento()%><br>
                </h4>
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
                    if (p.loggedUserLevel().equals("Amministratore") ||
                            ((Istruttore)p).getMatricolaIstruttore().equalsIgnoreCase(sa.getIstruttore().getMatricolaIstruttore())) {
                %>
                <div class="SchedaButtonsContainer">
                    <a href="FindScheda?idScheda=<%=sa.getIdScheda()%>" class="noDecoration">
                        <button class="schedaButtons">Modifica Info Scheda</button></a>
                        <button id="addExercisesButton" class="schedaButtons" onclick="aggiungiEsercizio(<%=sa.getIdScheda()%>, <%=i%>)">Aggiungi Altri Esercizi alla Scheda</button>
                    <button class="schedaButtons" onclick="eliminaScheda(<%=sa.getIdScheda()%>)">Elimina Scheda Allenamento</button>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>
