package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.entity.Esercizio;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetExercisesServlet", value = "/getAllExercises")
public class GetExercisesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Esercizio> listaEsercizi = EsercizioDAO.findAll();
            String json = new Gson().toJson(listaEsercizi);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore Server!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
