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

@WebServlet(name = "AddExerciseServlet", value = "/addExercise")
public class AddExerciseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

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
            e.printStackTrace();
            response.sendError(500, "Errore Server!");
        }
    }
}
