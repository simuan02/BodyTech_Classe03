package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "VisualizzaSingolaRichiestaServlet", value = "/VisualizzaRichiesta")
public class VisualizzaSingolaRichiestaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idRichiesta = Integer.parseInt(request.getParameter("idRichiesta"));
        RichiestaModificaSchedaDAO.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
