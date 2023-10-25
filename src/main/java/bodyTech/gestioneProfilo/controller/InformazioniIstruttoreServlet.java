package bodyTech.gestioneProfilo.controller;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet che consente di recuperare un Istruttore tramite la sua matricola dal DB, settarlo nella richiesta e
 * invocare la jsp "informazioniIstruttore" con un forward.
 */
@WebServlet(name = "InformazioniIstruttoreServlet", urlPatterns = {"/InformazioniIstruttoreServlet"})
public class InformazioniIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.informazioniIstruttoreMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

