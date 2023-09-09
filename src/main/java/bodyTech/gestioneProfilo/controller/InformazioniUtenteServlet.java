package bodyTech.gestioneProfilo.controller;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
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

@WebServlet(name = "InformazioniUtenteServlet", urlPatterns = {"/InformazioniUtenteServlet"})
public class InformazioniUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");
        String id = (String) request.getParameter("id");
        String address = "";
        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale);
            List<RichiestaModificaScheda> list = RichiestaModificaSchedaDAO.findByUser(codiceFiscale);
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

