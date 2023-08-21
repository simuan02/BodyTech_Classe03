<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.dao.UtenteDAO" %>
<%--
  Created by IntelliJ IDEA.
  User: jacop
  Date: 18/08/2023
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Utenti</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%
        List<Utente> listaUtenti = UtenteDAO.visualizzaUtenti();
        request.setAttribute("listaUtenti", listaUtenti);
        System.out.println("LEN UTENTI: " + listaUtenti.size());
    %>

    <script>
        function ricerca() {
            /*var dataList = document.getElementById('ricerca-list');
            if (str.length == 0) {
                // rimuove elementi <option> (suggerimenti) esistenti
                dataList.innerHTML = '';
                return;
            }

            var xmlHttpReq = new XMLHttpRequest();
            xmlHttpReq.responseType = 'json';
            xmlHttpReq.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    // rimuove elementi <option> (suggerimenti) esistenti
                    dataList.innerHTML = '';

                    for ( var i in this.response) {
                        // crea un elemento option
                        var option = document.createElement('option');
                        // setta il valore
                        option.value = this.response[i];
                        // aggiunge elemento <option> a datalist
                        dataList.appendChild(option);
                    }
                }
            }
            xmlHttpReq.open("GET", "RicercaAjaxServlet?search=" + encodeURIComponent(str), true);
            xmlHttpReq.send();*/
            var text = document.getElementById("search").value; //FUNZIONA

            var utente = new bodyTech.model.dao.UtenteDAO.findByCodiceFiscale(text);
            
        }
    </script>

    <%@include file="jsp/header.jsp"%>

    <div id="search-bar">
        <input autocomplete="off" type="text" placeholder="Cerca prodotti..." name="search" id="search">
        <img src="images/search.png" onclick="ricerca()">
    </div>

    <div class="container_utenti">
        <c:foreach items="${listaUtenti}" var="utente">
            <div class="utente">
                <h4>${utente.cognome} ${utente.nome}</h4>
                <h4>${utente.codiceFiscale}</h4>
            </div>
        </c:foreach>
    </div>
</body>
</html>
