package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Questa Servlet consente di modificare i dati del profilo corrente all'interno del DB.
 * Se il profilo corrente è null (non si è effettuato l'accesso), oppure se il profilo corrente è di un tipo diverso da
 * Utente, Istruttore ed Amministratore, lancia una pagina di errore con codice 403.
 * La modifica effettiva avverrà tramite il metodo ProfiloService.modificaDati(oldProfile, newProfile).
 * Se la modifica ha avuto esito positivo, ossia il metodo ha restituito true, allora il nuovo profilo diventerà il profilo
 * corrente e la risposta avrà come stato il codice 200.
 * Se, invece, la modifica ha avuto esito negativo, allora la risposta lancerà una pagina di errore con codice 400.
 */
@WebServlet(name = "EditDataServlet", value = "/EditData")
public class EditDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.editDataMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
