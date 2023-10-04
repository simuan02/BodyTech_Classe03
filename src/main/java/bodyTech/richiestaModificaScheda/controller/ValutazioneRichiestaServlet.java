package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ValutazioneRichiestaServlet", value = "/valutaRichiesta")
public class ValutazioneRichiestaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        int idRichiesta = Integer.parseInt(request.getParameter("id"));
        boolean valutazione = Boolean.parseBoolean(request.getParameter("valutazione"));
        String address = "";
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        try {
            RichiestaModificaScheda rms = services.visualizzaSingolaRichiesta(p, idRichiesta);
            if (rms.isEsito() != null)
                request.setAttribute("richiestaGiaEsaminata", true);
            else {
                rms.setEsito(valutazione);
                services.valutaRichistaModifica(rms, (Istruttore) p);
                request.setAttribute ("valutazioneRichiesta", valutazione);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore Server!");
        } catch (RuntimeException e2){
            response.sendError(400, e2.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
