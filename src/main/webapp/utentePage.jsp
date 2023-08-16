<%@ page import="bodyTech.model.entity.Profilo" %>
<%@ page import="bodyTech.model.entity.Utente" %><%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 16/08/2023
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Profilo</title>
    <link rel="stylesheet" href="css/profiloPage.css"/>
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%
        Object o = session.getAttribute("Utente");
        if (o != null) {
            Utente u = (Utente) o;
            System.out.println("UTENTE " + u.getNome());
            request.setAttribute("utente", u);
        }
    %>

    <%@include file="jsp/header.jsp"%>

    <div class="cards_account">

        <div class="card">
            <img src="images/utente.png">
            <div class="container">
                <h4><b>${utente.nome} ${utente.cognome}</b></h4>
                <p>${utente.codiceFiscale}</p>
            </div>
        </div>


        <div class="card">
            <img src="images/fitness.png">
            <div class="container">
                <h4><b>Scheda</b></h4>
                <p>Visualizza la tua scheda d'allenamento</p>
            </div>
        </div>

        <div class="card">
            <img src="images/pt.png">
            <div class="container">
                <h4><b>Istruttore</b></h4>
                <p>Visualizza  o contatta il tuo istruttore, se non ne hai uno associati.</p>
            </div>
        </div>


    </div>
</body>
</html>
