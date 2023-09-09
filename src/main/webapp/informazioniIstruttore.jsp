<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BodyTech - Informazioni Utente</title>
    <link rel="stylesheet" href="css/informazioniUtente.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
<%@include file="jsp/header.jsp"%>

<div class="card">
    <p>Nome: ${istruttore.nome}</p>
    <p>Cognome: ${istruttore.cognome}</p>
    <p>Codice Fiscale. ${istruttore.matricolaIstruttore}</p>

    <%
        Istruttore istruttore = (Istruttore)request.getAttribute("istruttore");
        List<SchedaAllenamento> listaSchede = istruttore.getListaSchedeCreate();
        if (listaSchede.size() > 0){
    %>
            <a href="${pageContext.request.contextPath}/VisualizzaSchedeIstruttoreServlet?mat=${istruttore.matricolaIstruttore}"><div class="button">
                Visualizza Schede d'allenamento create
            </div></a>
    <%
        }
        else if (p.loggedUserLevel().equals("Istruttore")){
    %>

            <div class="button">
                Nessuna scheda di allenamento creata.
            </div>
    <%
        }
    %>

        <div class="buttons">
            <a href="TrovaIstruttore?matricola=${istruttore.matricolaIstruttore}">
                <div class="UtenteButtons">
                Modifica i dati
            </div></a>
        </div>

        <a href="${pageContext.request.contextPath}/EliminaIstruttoreServlet?mat=${istruttore.matricolaIstruttore}">
            <div class="UtenteButtons">
                Elimina ${istruttore.cognome}
            </div>
        </a>
    </div>
</body>
</html>
