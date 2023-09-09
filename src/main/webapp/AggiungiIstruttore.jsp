<html>
<head>
    <title>BodyTech - Aggiungi Istruttore</title>
    <link rel="stylesheet" href="css/profiloPage.css">
    <link rel="icon" href="images/logo.jpg" sizes="any">
</head>
<body>
<%@include file="jsp/header.jsp"%>
<h1 id="profileTitle">AGGIUNGI UN NUOVO ISTRUTTORE</h1>

<form action="AggiungiIstruttore" method="post">
    <div id="FirstName" class="InfoProfile">
        <label for="NomeIstruttore" id="Nome">Nome: </label>
            <input type="text" class="volumeEsercizio" name="NomeIstruttore" id="NomeIstruttore">
    </div>
    <div id="LastName" class="InfoProfile">
        <label for="CognomeIstruttore" id="Cognome">Cognome: </label>
            <input type="text" class="volumeEsercizio" name="CognomeIstruttore" id="CognomeIstruttore">
    </div>

    <div id="Matricola" class="InfoProfile">
        <label for="MatricolaIstruttore">Matricola Istruttore (deve essere di 10 caratteri alfanumerici): </label>
        <input type="text" class="volumeEsercizio" name="MatricolaIstruttore" id="MatricolaIstruttore">
    </div>

    <div id="Password" class="InfoProfile">
        <label for="PasswordIstruttore">Password: </label>
        <input type="text" class="volumeEsercizio" name="PasswordIstruttore" id="PasswordIstruttore">
    </div>

    <div id="Specializzazione" class="InfoProfile">
        <label for="SpecializzazioneIstruttore">Specializzazione: </label>
        <input type="text" class="volumeEsercizio" name="SpecializzazioneIstruttore" id="SpecializzazioneIstruttore">
    </div><br>
    <input type="submit" id="EditButton" value="Aggiungi l'istruttore">
</div>
</form>
</body>
</html>

