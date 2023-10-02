package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Istruttore di aggiungere un esercizio e il suo volume ad una scheda di allenamento.
 *
 */
@WebServlet(name = "AddExerciseServlet", value = "/addExercise")
public class AddExerciseServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeEsercizio = request.getParameter("SelezionaEsercizio");
        System.out.println(request.getParameter("idScheda"));
        String volume = request.getParameter("Volume");
        int idScheda = Integer.parseInt(request.getParameter("idScheda"));
        SchedaService services = new SchedaServiceImpl();
        try {
            services.aggiungiEsercizio(EsercizioDAO.findByName(nomeEsercizio), volume, SchedaAllenamentoDAO.findByID(idScheda));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        }
    }
}
