package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.SchedaAllenamento;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente di recuperare una scheda di allenamento tramite il suo idScheda dal DB,
 * settarla nella richiesta e invocare la jsp "modificaScheda" con un forward.
 */

@WebServlet(name = "FindSchedaServlet", value = "/FindScheda")
public class FindSchedaServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchedaAllenamentoController.findSchedaMethod(request, response);
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
