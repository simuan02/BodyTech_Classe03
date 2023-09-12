package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "VisualizzaRichiesteModificaUtenteServlet", value = "/VisualizzaRichiesteModifica")
public class VisualizzaRichiesteModificaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente u = (Utente)session.getAttribute("Profilo");
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        List<RichiestaModificaScheda> richiesteModifica = null;
        try {
            richiesteModifica = services.visualizzaModifica(u);
            String json = new Gson().toJson(richiesteModifica);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
