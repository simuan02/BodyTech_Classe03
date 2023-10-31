package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.Istruttore;
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
        SchedaAllenamentoController.visualizzaSchedeMethod(request, response);
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
