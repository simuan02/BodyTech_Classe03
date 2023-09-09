<%@ page import="bodyTech.model.entity.Istruttore" %>
<%@ page import="bodyTech.model.entity.Amministratore" %>
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
        Object o = session.getAttribute("Amministratore");
        Amministratore a = null;
        if (o != null) {
            a = (Amministratore) o;
            request.setAttribute("Amministratore", a);
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
                    <h4><b><%=a.getNome()%> <%=a.getCognome()%></b></h4>
                    <p><%=a.getCodice()%></p>
                </div>
            </a>
        </div>

        <div class="card">
            <a href="listaUtenti">
                <img src="images/pt.png">
                <div class="container">
                    <h4><b>Gestione Utenti</b></h4>
                    <p>Gestisci gli utenti iscritti alla piattaforma.</p>
                </div>
            </a>
        </div>

            <div class="card">
                <a href="showTrainingCards" class="noDecoration">
                    <img src="images/fitness.png">
                    <div class="container">
                        <h4><b>Gestione Istruttori</b></h4>
                        <p>Gestisci gli istruttori presenti sulla piattaforma.</p>
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
