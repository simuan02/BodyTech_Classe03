<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.dao.UtenteDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="bodyTech.model.dao.SchedaAllenamentoDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>BodyTech - Utenti</title>
    <link rel="stylesheet" href="css/listaUtentiPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

    <style>
        .title {
            font-size: 15pt;
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <%

    %>

    <%@include file="jsp/header.jsp"%>

    <%
        Profilo p = session.getAttribute("Profilo");
        if (p.loggedUserLevel().equals("Istruttore")){
    %>

    <p class="title">I tuoi Utenti</p>
    <div class="container_utenti">

        <c:forEach items="${listaTuoiAssociati}" var="utente">
            <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${utente.codiceFiscale}&id=1" class="noDecoration">
                    <div>
                        <h4>${utente.codiceFiscale}</h4>
                        <h4>${utente.cognome} ${utente.nome}</h4>
                        <p class="positive_istruttore" style="color: green">L'utente è associato a te</p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>

    <p class="title">Altri Utenti</p>

    <div class="container_utenti">
        <c:forEach items="${listaAssociati}" var="utente">
            <div class="utente">
                <div>
                    <h4>${utente.codiceFiscale}</h4>
                    <h4>${utente.cognome} ${utente.nome}</h4>
                    <p class="negative_istruttore" style="color: darkred">L'utente è associato già ad un'istruttore</p>
                </div>
            </div>
        </c:forEach>

        <c:forEach items="${listaNonAssociati}" var="utente">
            <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${utente.codiceFiscale}&id=2" class="noDecoration">
                    <div>
                        <h4>${utente.codiceFiscale}</h4>
                        <h4>${utente.cognome} ${utente.nome}</h4>
                        <p class="negative_istruttore" style="color: darkred">L'utente non è associato a nessun istruttore</p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>

    <%
        }
        else if (p.loggedUserLevel().equals("Amministratore"))
        {
    %>
    <p class="title">Lista Utenti</p>
    <div class="container_utenti">

        <c:forEach items="${listaUtenti}" var="utente">
            <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${utente.codiceFiscale}&id=1" class="noDecoration">
                    <div>
                        <h4>${utente.codiceFiscale}</h4>
                        <h4>${utente.cognome} ${utente.nome}</h4>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
    <%
        }
    %>

</body>
</html>
