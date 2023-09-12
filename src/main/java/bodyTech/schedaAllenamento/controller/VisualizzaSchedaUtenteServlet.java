package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "VisualizzaSchedaUtenteServlet", urlPatterns = {"/VisualizzaSchedaUtenteServlet"})
public class VisualizzaSchedaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(utente.getCodiceFiscale());
            if (scheda != null) {
                List<EsercizioAllenamento> listaEsercizi = EsercizioAllenamentoDAO.findBySchedaID(scheda.getIdScheda());
                scheda.setListaEsercizi(listaEsercizi);
                request.setAttribute("scheda", scheda);
                request.setAttribute("utente", utente);


            }
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/schedaAllenamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

