<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Informazioni Utente</title>
    <link rel="stylesheet" href="css/informazioniUtente.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

    <style>
        #eliminaUtente {
            width: 30%;
            font-size: 13pt;
            padding: 6px;
            background-color: #F7C70F;
            border-radius: 10px;
            border: 2px solid #F7C70F;
            text-align: center;
            margin-top: 20px;
            color: black;
            margin-left: 5%;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <%@include file="jsp/header.jsp"%>

    <div class="card">
        <p>Nome: ${utente.nome}</p>
        <p>Cognome: ${utente.cognome}</p>
        <p>Codice Fiscale. ${utente.codiceFiscale}</p>

        <c:choose>
            <c:when test="${scheda != null}">
                <a href="${pageContext.request.contextPath}/VisualizzaSchedaAllenamentoServlet?cf=${utente.codiceFiscale}"><div class="button">
                    Visualizza Scheda d'allenamento
                </div></a>
            </c:when>

            <c:otherwise>
                <a href="${pageContext.request.contextPath}/CreazioneSchedaServlet?cf=${utente.codiceFiscale}&id=1"><div class="button">
                    Aggiungi una Scheda d'allenamento
                </div></a>
            </c:otherwise>
        </c:choose>


        <div class="richieste">
            <p id="title_richieste">Richieste</p>

            <c:choose>
                <c:when test="${richieste.size() > 0}">
                    <!-- FOREACH -->
                    <c:forEach items="${richieste}" var="richiesta">
                        <p>${richiesta.messaggio}</p>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <p style="color: darkred">L'utente non ha fatto nessuna richiesta</p>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="buttons">
            <a href="ModificaUtente"><div id="modificaDati">
                Modifica i dati
            </div></a>
        </div>

        <a href="${pageContext.request.contextPath}/EliminaUtenteServlet?cf=${utente.codiceFiscale}">
            <div id="eliminaUtente">
                Elimina ${utente.cognome}
            </div>
        </a>
    </div>
</body>
</html>
