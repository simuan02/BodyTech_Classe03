package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.entity.Esercizio;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet che consente di ottenere tutti gli esercizi presenti nel DB, inserirli in un oggetto Json e presentarli come output.
 */
@WebServlet(name = "GetExercisesServlet", urlPatterns = {"/getAllExercises", "/getAvailableExercises"})
public class GetExercisesServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idScheda = request.getParameter("idScheda");
            List<Esercizio> listaEsercizi = null;
            if (idScheda == null){
                listaEsercizi = EsercizioDAO.findAll();
            }
            else {
                int id = Integer.parseInt(idScheda);
                listaEsercizi = EsercizioDAO.findAvailableForScheda(id);
            }
            String json = new Gson().toJson(listaEsercizi);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore Server!");
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
        doGet(request, response);
    }
}
