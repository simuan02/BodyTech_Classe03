<html>
<head>
    <title>BodyTech - HomePage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/index.css"/>
    <link rel="icon" href="images/logo.jpg" sizes="any">
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
                        <script>alert("Errore al momento della registrazione")</script>
            <%
                        ;}
                }
                Object o2 = request.getAttribute("CodiceGiaPresente");
                if (o2 != null)
                {
            %>
                    <script>alert("Codice Fiscale gia' registrato alla piattaforma")</script>
            <%
                };
            %>

        <%@include file="jsp/header.jsp"%>


        <div class="home">
            <img src="images/immagine_homepage.jpg" id="img_home">

            <h1>Supera i tuoi limiti</h1>
            <p>
                Grazie a questo sito potrai allenarti al meglio e raggiungere finalmente i tuoi obiettivi!<br>Effettua l'accesso e
                associati ad un istruttore e inizia a seguire un piano d'allenamento.
            </p>
            
        </div>




    </body>
</html>