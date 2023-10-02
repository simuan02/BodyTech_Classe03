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
        <label for="NomeIstruttore" class="FormLabel">Nome: </label>
            <input type="text" class="FormInput" name="NomeIstruttore" id="NomeIstruttore" required>
    </div>
    <div id="LastName" class="InfoProfile">
        <label for="CognomeIstruttore" class="FormLabel">Cognome: </label>
            <input type="text" class="FormInput" name="CognomeIstruttore" id="CognomeIstruttore" required>
    </div>

    <div id="Matricola" class="InfoProfile">
        <label for="MatricolaIstruttore" class="FormLabel">Matricola Istruttore (deve essere di 10 caratteri alfanumerici): </label>
        <input type="text" class="FormInput" name="MatricolaIstruttore" id="MatricolaIstruttore" required>
    </div>

    <div id="Password" class="InfoProfile">
        <label for="PasswordIstruttore" class="FormLabel">Password: </label>
        <input type="text" class="FormInput" name="PasswordIstruttore" id="PasswordIstruttore" required>
    </div>

    <div id="Specializzazione" class="InfoProfile">
        <label for="SpecializzazioneIstruttore" class="FormLabel">Specializzazione: </label>
        <input type="text" class="FormInput" name="SpecializzazioneIstruttore" id="SpecializzazioneIstruttore">
    </div><br>
    <input type="submit" id="EditButton" value="Aggiungi l'istruttore">
</div>
</form>
</body>
</html>

