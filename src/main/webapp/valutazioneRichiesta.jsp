<%@ page import="bodyTech.model.entity.RichiestaModificaScheda" %>
<%@ page import="bodyTech.model.dao.RichiestaModificaSchedaDAO" %>
<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.dao.UtenteDAO" %><%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 08/09/2023
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Valutazione Richiesta</title>
    <link rel="stylesheet" href="css/valutazioneRichiesta.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

</head>
<body>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        String codiceFiscale = request.getParameter("cf");
        Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
        RichiestaModificaScheda richiesta = RichiestaModificaSchedaDAO.findById(id);
        request.setAttribute("richiesta", richiesta);
        request.setAttribute("utente", utente);
    %>

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
            <a href="${pageContext.request.contextPath}/showTrainingCards" onclick="">
                <div class="button">
                    <p>Accetta</p>
                </div>
            </a>

            <a href="#" onclick="">
                <div class="button">
                    <p>Rifiuta</p>
                </div>
            </a>
        </div>
    </div>

    <script>
        function accetta(id) {
            <%

            %>
        }
    </script>
</body>


</html>
