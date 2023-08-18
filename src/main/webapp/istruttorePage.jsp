<%@ page import="bodyTech.model.entity.Istruttore" %><%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 18/08/2023
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
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
            width: 20%; margin: 2%;
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
    %>

    <%@include file="jsp/header.jsp"%>

    <div class="cards_account">

        <div class="card">
            <img src="images/utente.png">
            <div class="container">
                <h4><b>${istruttore.nome} ${istruttore.cognome}</b></h4>
                <p>${istruttore.matricolaIstruttore}</p>
            </div>
        </div>

        <div class="card">
            <img src="images/pt.png">
            <div class="container">
                <h4><b>Istruttore</b></h4>
                <p>Visualizza o aggiungi i tuoi utenti.</p>
            </div>
        </div>

    </div>




    <script>
        function openListaUtenti() {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                /*if (xhr.readyState == 4) {
                    var data = xhr.responseText;
                    alert(data);
                }*/
            }

            xhr.open('GET', '${pageContext.request.contextPath}/ListaUtentiServlet', true);
            xhr.send(null);
        }
    </script>
</body>
</html>
