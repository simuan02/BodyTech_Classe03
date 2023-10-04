package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Servlet che consente di recuperare un Utente tramite il suo codice fiscale dal DB, settarlo nella richiesta insieme
 * alla sua scheda di allenamento e la sua lista delle richieste di modifica scheda effettuate, e invocare la
 * jsp "informazioniUtente" con un forward.
 */
@WebServlet(name = "InformazioniUtenteServlet", urlPatterns = {"/InformazioniUtenteServlet"})
public class InformazioniUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        String codiceFiscale = request.getParameter("cf");
        String id = (String) request.getParameter("id");
        String address = "";
        try {
            RichiestaModificaSchedaService requestsServices = new RichiestaModificaSchedaServiceImpl();
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale);
            List<RichiestaModificaScheda> list = requestsServices.visualizzaModifica(p, utente);
            request.setAttribute("utente", utente);
            request.setAttribute("scheda", scheda);
            request.setAttribute("richieste", list);
            if (Integer.parseInt(id) == 1) {
                request.setAttribute("isAssociato", true);
            } else {
                request.setAttribute("isAssociato", false);
            }

            address = "/informazioniUtente.jsp";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

