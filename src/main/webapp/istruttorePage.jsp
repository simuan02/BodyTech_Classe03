<%@ page import="bodyTech.model.entity.Istruttore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Profilo</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

    <style>
        .cards_account {
            display: flex;
            flex-direction: row;
        }

        .card {
            width: 25%; margin: 2%;
        }

        .card img {
            width: 100%;
        }
    </style>
</head>
<body>
    <%
        Object o = session.getAttribute("Istruttore");
        if (o != null) {
            Istruttore i = (Istruttore) o;
            request.setAttribute("istruttore", i);
        }
        else {
            response.sendError(403, "ACCESSO NON AUTORIZZATO");
        }
    %>

    <%@include file="jsp/header.jsp"%>

    <div class="cards_account">

        <div class="card">
            <a href="infoProfilo" style="text-decoration: none">
            <img src="images/utente.png">
            <div class="container">
                <h4><b>${istruttore.nome} ${istruttore.cognome}</b></h4>
                <p>${istruttore.matricolaIstruttore}</p>
            </div>
            </a>
        </div>

        <div class="card">
            <a href="listaUtenti">
                <img src="images/pt.png">
                <div class="container">
                    <h4><b>Istruttore</b></h4>
                    <p>Visualizza o aggiungi i tuoi utenti.</p>
                </div>
            </a>
        </div>

            <div class="card">
                <a href="showTrainingCards" class="noDecoration">
                    <img src="images/fitness.png">
                    <div class="container">
                        <h4><b>Schede Allenamento</b></h4>
                        <p>Visualizza le schede di allenamento.</p>
                    </div>
                </a>
            </div>

    </div>

    <script>
        function openListaUtenti() {
            window.open("listaUtentiPage.jsp?id=${istruttore.matricolaIstruttore}");
            /*var xhr = new XMLHttpRequest();


            xhr.open('GET', '${pageContext.request.contextPath}/ListaUtentiServlet', true);
            xhr.send(null);*/
        }
    </script>
</body>
</html>
