/**
 * Questa funzione viene invocata al verificarsi dell'evento di click di un qualsiasi elemento della classe
 * "editIcon". Questa funzione nasconde tutti gli elementi della classe "editIcon" ed aggiunge un nuovo blocco
 * al blocco con id "FirstName", se è stata cliccata la "editIcon" corrispondente al nome, oppure aggiunge un
 * nuovo blocco al blocco con id "LastName", se è stata cliccata la "editIcon" corrispondente al cognome.
 * Questo nuovo blocco, con id "ModificaDato", contiene un elemento "input" di tipo text e un "button", che,
 * quando cliccato, invoca la funzione modificaDato(tipo di modifica).
 */
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

/**
 * Questa funzione consente, attraverso una chiamata AJAX alla servlet "EditData", di modificare i dati dell'utente
 * corrente. La modifica che deve essere fatta viene decisa dal parametro valoreModifica:
 * se esso vale 0, allora verrà modificato il nome dell'Utente, passando alla Servlet il valore
 * dell'elemento "input" con id "NewName" e la stringa datoModificato = "nome",
 * se esso vale 1, allora verrà modificato il cognome dell'Utente, passando alla Servlet il valore
 * dell'elemento "input" con id "NewLastName" e la stringa datoModificato = "cognome",
 * Sia in caso di successo nell'invocazione AJAX che in caso di errore, verrà mostrato un alert, che avvertirà
 * sull'esito della chiamata, e verrà fatta un'invocazione alla funzione restorePage(valoreModificato, tipoValore).
 * @param valoreModifica indica il tipo di dato che dovrà essere modificato dalla servlet "EditData" e può assumere
 * valore 0 oppure 1.
 */
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

/**
 * Questa funzione consente di aggiornare la pagina del profilo, rimuovendo il blocco per la modifica dei dati e
 * mostrando nuovamente tutte gli elementi di classe "editIcon".
 * Inoltre, se tipoValore = "nome", allora il blocco di id "FirstName" avrà la proprietà display: flex;
 * se tipoValore = "cognome", allora il blocco di id "LastName" avrà la proprietà display: flex.
 * Infine, se valoreModificato è diverso da null, ossia la modifica di un qualsiasi valore ha avuto successo,
 * aggiorna le informazioni contenute nell'elemento di id "Nome", se tipoValore = "nome";
 * altrimenti, aggiorna le informazioni contenute nell'elemento di id "Cognome", se tipoValore = "cognome".
 * @param valoreModificato questo parametro rappresenta il valore aggiornato
 * @param tipoValore questo parametro rappresenta il tipo di valore aggiornato
 */
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

/**
 * Questa funzione consente la visualizzazione di un form per l'apertura di una richiesta di modifica della scheda di
 * allenamento dell'utente corrente.
 */
$(document).ready(function(){
    $("#editRequestButton").click(function () {
        $(".CentralButtons").hide();
        $("body").append("" +
            "<form action='openRequest' id='requestForm' method='post'>" +
                "<label for='richiestaModifica'>Scrivi la tua richiesta nella casella di testo sottostante</label><br>" +
                "<textarea id='richiestaModifica' name='richiesta'>Scrivi la tua richiesta qui...</textarea><br>" +
                "<input type='submit' value='Invia Richiesta' id='openRequestButton' class='requestButtons'>" +
            "</form>");
    })
})

$(document).ready(function () {
    $("#showRequestButton").click(function () {
        $("#showRequestButton").hide();
        $.get("VisualizzaRichiesteModifica", function(richieste){
            $("body").append("<div id='RequestsList' class='ExercisesAndRequestsList'>" +
                "<table id='RequestsTable' class='ExercisesAndRequestsTable'>" +
                "<caption id='ListaRichiesteTitle' class='ListaEserciziAndRichiesteTitle'>Lista Richieste</caption>" +
                "<tr>" +
                "<th class='MessaggioCell CellTable'>Messaggio</th>" +
                "<th class='EsitoCell CellTable'>Esito</th>" +
                "</tr></table>" +
                "</div>")
            $.each(richieste, function(index, richiesta){
                let esito = richiesta.esito;
                if (!esito)
                    esito = "Non ancora valutata o negata";
                else
                    esito = "Concessa";
                $("#RequestsTable").append("<tr><td class='MessaggioCell CellTable'>" + richiesta.messaggio + "</td>" +
                    "<td class='EsitoCell CellTable'>" + esito + "</td></tr>")
            })
        })
    })
})


function eliminaScheda(idScheda){
    if (confirm("Sei sicuro di voler eliminare la scheda n. " + idScheda + "?")){
        $.ajax({
            url: "deleteScheda",
            data: {
                idScheda: idScheda
            },
            success: function () {
                alert ("Scheda n. " + idScheda + "eliminata con successo");
            },
            error: function () {
                alert ("Errore nell'eliminazione della scheda n. " + idScheda);
            }
        })
    }
}

function aggiungiEsercizio(idScheda, numeroScheda) {
    $(".SchedaButtonsContainer").hide();
    $("#SchedaAllenamento" + numeroScheda).append("<br><h3>Aggiungi un nuovo esercizio</h3><form action='addExercise?idScheda="+ idScheda + "' method='post'><select id='SelezionaEsercizio' name='SelezionaEsercizio'></select>" +
        "  <input class='volumeEsercizio' type='text' value='Volume' name='Volume'><br><input type='submit' value='Aggiungi esercizio' class='schedaButtons' id='addExerciseButton'></a>");
    $.get("getAllExercises", function (exercisesList) {
            $.each(exercisesList, function(index, esercizio) {
                $("<option value=''>").text(esercizio.nomeEsercizio).appendTo($("#SelezionaEsercizio")).attr("value", esercizio.nomeEsercizio);
            });
    })
}