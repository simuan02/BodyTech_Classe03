package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "VisualizzaSingolaRichiestaServlet", value = "/VisualizzaRichiesta")
public class VisualizzaSingolaRichiestaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idRichiesta = Integer.parseInt(request.getParameter("id"));
        String codiceFiscale = request.getParameter("cf");
        HttpSession session = request.getSession();
        String address = "";
        try {
            RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
            RichiestaModificaScheda richiesta = services.visualizzaSingolaRichiesta((Profilo) session.getAttribute("Profilo"), idRichiesta);
            if (richiesta.isEsito() == null) {
                request.setAttribute("richiesta", richiesta);
                request.setAttribute("utente", UtenteDAO.findByCodiceFiscale(codiceFiscale));
                address = "/valutazioneRichiesta.jsp";
            }
            else {
                request.setAttribute("richiestaGiaEsaminata", true);
                address = "/listaUtenti";
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
