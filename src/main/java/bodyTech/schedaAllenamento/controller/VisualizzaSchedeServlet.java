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

/**
 * Servlet che consente di recuperare una lista di tutte le schede di allenamento dal DB,
 * settarla nella richiesta e invocare la jsp "listaSchedePage" con un forward.
 */
@WebServlet(name = "VisualizzaSchedeServlet", value = "/showTrainingCards")
public class VisualizzaSchedeServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
