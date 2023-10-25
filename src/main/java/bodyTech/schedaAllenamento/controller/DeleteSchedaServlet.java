package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Istruttore di eliminare una scheda di allenamento.
 */
@WebServlet(name = "DeleteSchedaServlet", value = "/deleteScheda")
public class DeleteSchedaServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchedaAllenamentoController.deleteSchedaMethod(request, response);
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
        doGet(request, response);
    }
}
