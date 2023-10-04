<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%
    Object o = request.getAttribute("Registrazione");
    if (o != null){
        boolean b = (boolean) o;
        if (b){
%>
<script>alert("Registrazione avvenuta con successo")</script>
<%
        ;}
        else {
%>
<script>alert("Errore nella registrazione")</script>
<%
            Object o2 = request.getAttribute("CodiceFiscaleRegistrato");
            if (o2 != null)
            {
%>
<script>alert("Codice Fiscale già presente sulla piattaforma.")</script>
<%
            };
            Object o3 = request.getAttribute("LunghezzaPWErrata");
            if (o3 != null)
            {
%>
<script>alert("<%=o3%>");</script>
<%
            }
            Object o4 = request.getAttribute("LunghezzaNomeErrata");
            if (o4 != null)
            {

%>
<script>alert("<%=o4%>");</script>
<%
            }
            Object o5 = request.getAttribute("LunghezzaCognomeErrata");
            if (o5 != null)
            {
%>
<script>alert("<%=o5%>");</script>
<%
            }
            Object o6 = request.getAttribute("LunghezzaCFErrata");
            if (o6 != null)
            {
%>
<script>alert("<%=o6%>");</script>
<%
            }
        }
    }
%>

</body>
</html>
