<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Object servletLanciata = request.getAttribute("ServletMostraProfiloLanciata");
    String richiestaEffettuata = (String)request.getAttribute("richiestaEffettuata");
    if (servletLanciata == null && richiestaEffettuata == null) {
        response.sendError(403, "Operazione non consentita!");
        request.setAttribute("ServletMostraProfiloLanciata", null);
    }
    if (richiestaEffettuata != null){
        if (richiestaEffettuata.equalsIgnoreCase("YES")){
%>
<script>alert("La richiesta è stata correttamente aperta");</script>
<%;
}
else if (richiestaEffettuata.equalsIgnoreCase("NO")){

%>
<script>alert("Errore nell'apertura della richiesta");</script>
<%;
}
}
%>
</body>
</html>
