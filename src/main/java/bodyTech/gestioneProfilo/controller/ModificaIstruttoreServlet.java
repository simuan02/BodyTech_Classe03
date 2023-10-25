package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Amministratore di modificare le informazioni di un Istruttore tramite un form. Viene creato
 * un nuovo oggetto Istruttore con le informazioni aggiornate da sostituire a quelle dell'oggetto Istruttore presente nel DB.
 * L'operazione non procede se la nuova matricola è attribuita ad un Istruttore già presente, o se la sua lunghezza è errata.
 */
@WebServlet(name = "ModificaIstruttoreServlet", urlPatterns = {"/ModificaIstruttore"})
public class ModificaIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
