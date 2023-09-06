package bodyTech.schedaAllenamento.controller;

import bodyTech.model.entity.Profilo;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteExerciseServlet", value = "/deleteExercise")
public class DeleteExerciseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        if (p != null && (p.loggedUserLevel().equals("Istruttore") || p.loggedUserLevel().equals("Amministratore"))){
            SchedaService services = new SchedaServiceImpl();
            String nomeEsercizio = request.getParameter("nomeEsercizio");
            int schedaId = Integer.parseInt(request.getParameter("idScheda"));
            try {
                services.eliminaEsercizio(schedaId, nomeEsercizio);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else
            response.sendError(403, "Utente non autorizzato");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
