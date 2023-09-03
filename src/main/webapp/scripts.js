$(document).ready(function () {
    $(".editIcon").click(function () {
        if ($(this).attr("id") == "editFirstName") {
            $(".editIcon").hide();
            $("#FirstName").css("display", "block");
            $("#FirstName").append("<br><div id='ModificaDato'><label for='NewName'>Inserisci nuovo nome </label> " +
                "<input type='text' id='NewName' name='nome'> " +
                "<button onclick='modificaDato(0)'>Modifica</button></div>");
        }
        else if ($(this).attr("id") == "editLastName") {
            $(".editIcon").hide();
            $("#LastName").css("display", "block");
            $("#LastName").append("<br><div id='ModificaDato'><label for='NewLastName'>Inserisci nuovo cognome </label> " +
                "<input type='text' id='NewLastName' name='cognome'> " +
                "<button onclick='modificaDato(1)'>Modifica</button></div>");
        }
    })
})

function modificaDato(valoreModifica) {
    if (valoreModifica == 0){
        let nuovoNome = $("#NewName").val();
        $.ajax({
            url: "EditData", data: {
                newValue: nuovoNome,
                datoModificato: "nome"
            },
            success: function(){
                alert ("Modifica del nome riuscita!");
                restorePage(nuovoNome, "nome");
            },
            error: function () {
                alert ("Errore nella modifica dei dati");
                restorePage(null, "nome");
            }
        })
    }
    else if (valoreModifica == 1){
        let nuovoCognome = $("#NewLastName").val();
        $.ajax({
            url: "EditData", data: {
                newValue: nuovoCognome,
                datoModificato: "cognome"
            },
            success: function(){
                alert ("Modifica del cognome riuscita!");
                restorePage(nuovoCognome, "cognome");
            },
            error: function () {
                alert ("Errore nella modifica dei dati");
                restorePage(null, "cognome");
            }
        })
    }
}

function restorePage(valoreModificato, tipoValore){
    $("#ModificaDato").remove();
    $(".editIcon").show();

    if (tipoValore == "nome"){
        $("#FirstName").css("display", "flex");
    }
    else if (tipoValore == "cognome"){
        $("#LastName").css("display", "flex");
    }

    if (valoreModificato != null) {
        if (tipoValore == "nome"){
            $("#Nome").text("Nome: " + valoreModificato);
        }
        else if (tipoValore == "cognome"){
            $("#Cognome").text("Cognome: " + valoreModificato);
        }
    }
}


$(document).ready(function(){
    $("#editRequestButton").click(function () {
        $("#editRequestButton").hide();
        $("body").append("" +
            "<form action='openRequest' id='requestForm'>" +
                "<label for='richiestaModifica'>Scrivi la tua richiesta nella casella di testo sottostante</label><br>" +
                "<textarea id='richiestaModifica' name='richiesta'>Scrivi la tua richiesta qui...</textarea><br>" +
                "<input type='submit' value='Invia Richiesta' id='openRequestButton' class='requestButtons'>" +
            "</form>");
    })
})