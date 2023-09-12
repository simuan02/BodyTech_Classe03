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
        int idScheda = Integer.parseInt(request.getParameter("idScheda"));
        try {
            SchedaAllenamento sa = SchedaAllenamentoDAO.findByID(idScheda);
            SchedaService services = new SchedaServiceImpl();
            if (services.rimuoviSchedaUtente(sa) != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            }
            else
                response.sendError(400, "Errore nell'eliminazione della scheda: scheda inesistente");
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
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
