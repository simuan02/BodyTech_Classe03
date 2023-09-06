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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>BodyTech - Utenti</title>
    <link rel="stylesheet" href="css/listaUtentiPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%
        List<Utente> listaUtenti = UtenteDAO.visualizzaUtenti();
        request.setAttribute("listaUtenti", listaUtenti);
        System.out.println("LEN UTENTI: " + listaUtenti.size());
    %>

    <%@include file="jsp/header.jsp"%>

    <div class="container_utenti">
        <c:forEach items="${listaUtenti}" var="utente">
            <c:choose>
                <c:when test="${utente.matricolaIstruttore != null}">
                    <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${utente.codiceFiscale}&id=1">
                        <div class="utente">
                            <h4>${utente.codiceFiscale}</h4>
                            <h4>${utente.cognome} ${utente.nome}</h4>
                            <p id="positive_istruttore">L'utente è associato già ad un'istruttore</p>
                        </div>
                    </a>
                </c:when>

                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${utente.codiceFiscale}&id=1">
                        <div class="utente">
                            <h4>${utente.codiceFiscale}</h4>
                            <h4>${utente.cognome} ${utente.nome}</h4>
                            <p id="negative_istruttore">L'utente non è associato a nessun istruttore</p>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose></a>
            <!--<a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=${codiceFiscale}&id=1"><div class="utente">
                <h4>${utente.codiceFiscale}</h4>
                <h4>${utente.cognome} ${utente.nome}</h4>
                <c:choose>
                    <c:when test="${utente.matricolaIstruttore != null}">
                        <p id="positive_istruttore">L'utente è associato già ad un'istruttore</p>
                    </c:when>
                    <c:otherwise>
                        <p id="negative_istruttore">L'utente non è associato a nessun istruttore</p>
                    </c:otherwise>
                </c:choose>
            </div></a>-->
        </c:forEach>
    </div>



    <script>
        function associati(codiceFiscale) {
            var txt;
            if (confirm("Vuoi associarti all'utente ${codiceFiscale}?")) {
                //associati
            }
        }
    </script>
</body>
</html>
