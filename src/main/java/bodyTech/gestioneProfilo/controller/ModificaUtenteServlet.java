package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Amministratore o Istruttore di modificare le informazioni di un Utente tramite un form.
 * Viene creato un nuovo oggetto Utente con le informazioni aggiornate da sostituire a quelle dell'oggetto Utente presente nel DB.
 * L'operazione non procede se il nuovo codice fiscale è attribuito ad un Utente già presente, o se la sua lunghezza è errata.
 */
@WebServlet(name = "ModificaUtenteServlet", urlPatterns = {"/ModificaUtente"})
public class ModificaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.modificaUtenteMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
