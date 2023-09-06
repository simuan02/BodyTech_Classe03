package bodyTech.schedaAllenamento.controller;

import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "VisualizzaSchedeServlet", value = "/showTrainingCards")
public class VisualizzaSchedeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        SchedaService services = new SchedaServiceImpl();
        try {
            List<SchedaAllenamento> listaSchede = services.visualizzaSchede(p);
            request.setAttribute("listaSchede", listaSchede);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaSchedePage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(400);
        } catch (RuntimeException re){
            response.sendError(403, "Operazione non consentita");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
