<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.entity.Istruttore" %>
<%@ page import="bodyTech.model.entity.Amministratore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Informazioni Profilo</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="scripts.js"></script>
</head>
<body>
    <%@include file="jsp/header.jsp"%>
    <%
        Object servletLanciata = request.getAttribute("ServletMostraProfiloLanciata");
        if (servletLanciata == null) {
            response.sendError(403, "Operazione non consentita!");
            request.setAttribute("ServletMostraProfiloLanciata", null);
        }
    %>
    <h1 id="profileTitle">DETTAGLI PROFILO</h1>
    <div id="FirstName" class="InfoProfile">
        <h2 id="Nome">Nome: <%=p.getNome()%></h2>
        <img src="images/matitaIcon.png" class="editIcon" id="editFirstName">
    </div>
    <div id="LastName" class="InfoProfile">
        <h2 id="Cognome">Cognome: <%=p.getCognome()%></h2>
        <img src="images/matitaIcon.png" class="editIcon" id="editLastName">
    </div>
    <%
        if (p.loggedUserLevel().equals("Utente")){
    %>
        <h2 id="Identifier" class="InfoProfile">Codice Fiscale: <%=((Utente) p).getCodiceFiscale()%></h2>
    <%
        }
        else if (p.loggedUserLevel().equals("Istruttore")){
    %>
        <h2 id="Identifier" class="InfoProfile">Matricola: <%=((Istruttore) p).getMatricolaIstruttore()%></h2>
    <%
        }
        else if (p.loggedUserLevel().equals("Amministratore")){
    %>
            <h2 id="Identifier" class="InfoProfile">Codice ID: <%=((Amministratore) p).getCodice()%></h2>
                    <%
        }
    %>

</body>
</html>
