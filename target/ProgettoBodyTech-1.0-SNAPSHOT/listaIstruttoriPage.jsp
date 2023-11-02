<%@ page import="java.util.List" %>
<%@ page import="bodyTech.model.entity.Istruttore" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>BodyTech - Istruttori</title>
    <link rel="stylesheet" href="css/listaUtentiPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
    <%@include file="jsp/controlloAggiuntaIstruttoreCorretta.jsp"%>

    <%@include file="jsp/header.jsp"%>

    <%
        if (p.loggedUserLevel().equals("Amministratore")){
    %>

    <p class="title">Tutti gli istruttori</p>
    <div class="container_utenti">

        <%
            List<Istruttore> listaIstruttori = (List<Istruttore>) request.getAttribute("listaIstruttori");
            for (Istruttore istr: listaIstruttori)
            { %>
                <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniIstruttoreServlet?mat=<%=istr.getMatricolaIstruttore()%>" class="noDecoration">
                    <div>
                        <h4><%=istr.getMatricolaIstruttore()%></h4>
                        <h4><%=istr.getCognome()%> <%=istr.getNome()%></h4>
                    </div>
                </a>
            </div>
            <%
                }
            %>
    </div>
    <a href="AggiungiIstruttore.jsp"><button id="AggiungiIstruttore">Aggiungi un nuovo istruttore</button></a>

    <%
        }
        else {
            response.sendError(403, "Operazione non consentita!");
        }
    %>

</body>
</html>
