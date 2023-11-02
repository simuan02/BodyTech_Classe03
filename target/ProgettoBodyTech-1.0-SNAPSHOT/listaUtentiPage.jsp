<%@ page import="bodyTech.model.entity.Utente" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>BodyTech - Utenti</title>
    <link rel="stylesheet" href="css/listaUtentiPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">

    <style>
        .title {
            font-size: 18pt;
            color: red;
            text-align: center;
            font-weight: 700;
        }
    </style>
</head>
<body>
    <%@include file="jsp/controlloModificaUtente.jsp"%>
    <%@include file="jsp/controlloRichiestaEsaminata.jsp"%>

    <%@include file="jsp/header.jsp"%>

    <%
        if (p.loggedUserLevel().equals("Istruttore")){
    %>

    <p class="title">I tuoi Utenti</p>
    <div class="container_utenti">

        <%
            List<Utente> listaTuoiAssociati = (List<Utente>) request.getAttribute("listaTuoiAssociati");
            for (Utente user: listaTuoiAssociati)
            { %>
                <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=<%=user.getCodiceFiscale()%>&id=1" class="noDecoration">
                    <div>
                        <h4><%=user.getCodiceFiscale()%></h4>
                        <h4><%=user.getCognome()%> <%=user.getNome()%></h4>
                        <p class="positive_istruttore" style="color: green">L'utente è associato a te</p>
                        </div>
                </a>
            </div>
            <%
                }
            %>
    </div>

    <p class="title">Altri Utenti</p>

    <div class="container_utenti">
        <%
            List<Utente> listaAssociati = (List<Utente>) request.getAttribute("listaAssociati");
            for (Utente user : listaAssociati)
            {
        %>
            <div class="utente">
                <div>
                    <h4><%=user.getCodiceFiscale()%></h4>
                    <h4><%=user.getCognome()%> <%=user.getNome()%></h4>
                    <p class="negative_istruttore" style="color: darkred">L'utente è associato già ad un'istruttore</p>
                </div>
            </div>
        <%
            }
        %>

        <%
            List<Utente> listaNonAssociati = (List<Utente>) request.getAttribute("listaNonAssociati");
            for (Utente user : listaNonAssociati)
            {
        %>      <div class="utente">
                    <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=<%=user.getCodiceFiscale()%>&id=2" class="noDecoration">
                        <div>
                            <h4><%=user.getCodiceFiscale()%></h4>
                            <h4><%=user.getCognome()%> <%=user.getNome()%></h4>
                            <p class="negative_istruttore" style="color: darkred">L'utente non è associato a nessun istruttore</p>
                        </div>
                    </a>
                </div>
        <%
            }
        %>
        </div>
    <%
        }
        else if (p.loggedUserLevel().equals("Amministratore"))
        {
    %>
    <p class="title">Lista Utenti</p>
    <div class="container_utenti">

        <%
            List<Utente> listaUtenti = (List<Utente>) request.getAttribute("listaUtenti");
            for (Utente user : listaUtenti)
            {
        %>
            <div class="utente">
                <a href="${pageContext.request.contextPath}/InformazioniUtenteServlet?cf=<%=user.getCodiceFiscale()%>&id=1" class="noDecoration">
                    <div>
                        <h4><%=user.getCodiceFiscale()%></h4>
                        <h4><%=user.getCognome()%> <%=user.getNome()%></h4>
                    </div>
                </a>
            </div>
    <%
            }
        }
    %>
    </div>

</body>
</html>
