<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.dao.UtenteDAO" %><%--
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

    <%@include file="jsp/header.jsp"%>

    <div class="barra_di_ricerca">
        <form id="search_form" action="/..." method="post">
            <label>Ricerca <input id="search_input" size="30" name="keyword" /></label>
            <input type="image" src="images/search.png" />
        </form>
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
