<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="bodyTech.model.entity.SchedaAllenamento" %>
<%@ page import="bodyTech.model.entity.RichiestaModificaScheda" %>
<%@ page import="java.util.List" %>
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
    <p>Nome: ${utente.nome}</p>
    <p>Cognome: ${utente.cognome}</p>
    <p>Codice Fiscale. ${utente.codiceFiscale}</p>

    <%
        SchedaAllenamento scheda = (SchedaAllenamento) request.getAttribute("scheda");
        if (scheda != null){
    %>
            <a href="${pageContext.request.contextPath}/VisualizzaSchedaUtenteServlet?cf=${utente.codiceFiscale}"><div class="button">
                Visualizza Scheda d'allenamento
            </div></a>
    <%
        }
        else if (p.loggedUserLevel().equals("Istruttore")){
    %>

            <a href="${pageContext.request.contextPath}/CreazioneSchedaServlet?cf=${utente.codiceFiscale}&id=1"><div class="button">
                Aggiungi una Scheda d'allenamento
            </div></a>
    <%
        }
    %>


    <div class="richieste">
        <p id="title_richieste">Richieste</p>

        <%
            List<RichiestaModificaScheda> richieste = (List<RichiestaModificaScheda>)request.getAttribute("richieste");
            if (richieste.size() > 0){
                for (RichiestaModificaScheda richiesta: richieste){
        %>
                    <p><%=richiesta.getMessaggio()%></p>
        <%
                }
            }
            else
            {
        %>
                <p style="color: darkred">L'utente non ha fatto nessuna richiesta</p>
        <%
            }
        %>

    </div>

        <div class="buttons">
            <a href="TrovaUtente?codiceFiscale=${utente.codiceFiscale}">
                <div class="UtenteButtons">
                Modifica i dati
            </div></a>
        </div>

        <a href="${pageContext.request.contextPath}/EliminaUtenteServlet?cf=${utente.codiceFiscale}">
            <div class="UtenteButtons">
                Elimina ${utente.cognome}
            </div>
        </a>
    </div>
</body>
</html>
