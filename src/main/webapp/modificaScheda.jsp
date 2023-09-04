<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="bodyTech.model.entity.EsercizioAllenamento" %>
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
    SchedaAllenamento sa = (SchedaAllenamento) request.getAttribute("SchedaAllenamento");
%>

<h1 id="profileTitle">INFO SCHEDA</h1>
<div id="UserInfo" class="InfoProfile">
    <h2 id="Utente">Utente: <%=sa.getUtente().getNome()%> <%=sa.getUtente().getCognome()%></h2>
</div>
<div id="InstructorInfo" class="InfoProfile">
    <h2 id="Istruttore">Istruttore: <%=sa.getIstruttore().getNome()%> <%=sa.getIstruttore().getCognome()%></h2>
</div>
<form action="editScheda" method="post" id="FormModificaScheda">
    <input type="hidden" value="<%=sa.getIdScheda()%>" name="idScheda">
    <label for="TipoScheda">Tipo Scheda: </label><input type="text" value="<%=sa.getTipo()%>" id="TipoScheda" name="TipoScheda"><br>
    <label for="DataCompletamento">Data Completamento: </label>
    <input type="date" value="<%=sa.getDataCompletamento()%>" name="DataCompletamento" id="DataCompletamento">
    <h2 align="center">Modifica Volume Esercizi</h2>
    <input type="hidden" value="<%=sa.getListaEsercizi()%>" name="listaEsercizi">
    <%
        for (EsercizioAllenamento ea: sa.getListaEsercizi()){
    %>
    <label for="<%=ea.getNomeEsercizio()%>"><%=ea.getNomeEsercizio()%> - Volume: </label>
    <input type="text" value="<%=ea.getVolume()%>" class="VolumeEsercizio" name="<%=ea.getNomeEsercizio()%>"><br>
    <%
        }
    %>
    <input type="submit" value="Cambia Le Informazioni della Scheda" id="SubmitFormModifica"><br>
</form>

<div id="ExercisesList" class="ExercisesAndRequestsList">
    <table id="ExercisesTable" class="ExercisesAndRequestsTable">
        <caption id="ListaEserciziTitle" class="ListaEserciziAndRichiesteTitle">Lista Esercizi</caption>
        <tr>
            <th class="NomeEsercizioCell CellTable">Nome Esercizio</th>
            <th class="DescrizioneCell CellTable">Descrizione</th>
            <th class="VolumeCell CellTable">Volume</th>
            <th class="CellTable deleteCell">EliminaEsercizio</th>
        </tr>
        <%
            for (EsercizioAllenamento es: sa.getListaEsercizi())
            {
        %>
        <tr>
            <td class="NomeEsercizioCell CellTable"><%=es.getNomeEsercizio()%></td>
            <td class="DescrizioneCell CellTable"><%=es.getDescrizione()%></td>
            <td class="VolumeCell CellTable"><%=es.getVolume()%></td>
            <td class="deleteCell CellTable">
                <a href="deleteExercise?idScheda=<%=sa.getIdScheda()%>&nomeEsercizio=<%=es.getNomeEsercizio()%>" class="noDecoration">
                    Elimina questo esercizio dalla scheda</a>
            </td>
        </tr>
        <%}%>
    </table>
</div>

</body>
</html>
