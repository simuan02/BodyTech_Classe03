<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Profilo</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

</head>
<body>
    <%@include file="jsp/controlloUtente.jsp"%>

    <%@include file="jsp/header.jsp"%>

    <div class="cards_account">

        <div class="card">
            <a href="infoProfilo" class="noDecoration">
            <img src="images/utente.png">
            <div class="container">
                <h4><b>${Utente.nome} ${Utente.cognome}</b></h4>
                <p>${Utente.codiceFiscale}</p>
            </div>
            </a>
        </div>


        <div class="card">
            <a href="visualizzaScheda" class="noDecoration">
            <img src="images/fitness.png">
            <div class="container">
                <h4><b>Scheda</b></h4>
                <p>Visualizza la tua scheda d'allenamento</p>
            </div>
            </a>
        </div>

    </div>


</body>
</html>
