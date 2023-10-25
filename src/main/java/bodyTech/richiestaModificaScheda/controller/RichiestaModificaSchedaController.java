package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RichiestaModificaSchedaController {
    public static void openRequestMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messaggio = request.getParameter("richiesta");
        HttpSession session = request.getSession();
        Utente u = (Utente)session.getAttribute("Profilo");
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio(messaggio);
        if (services.richiediModificaScheda(richiesta, u))
            request.setAttribute("richiestaEffettuata", "YES");
        else
            request.setAttribute("richiestaEffettuata", "NO");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/infoProfile.jsp");
        dispatcher.forward(request, response);
    }

    public static void valutazioneRichiestaMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        int idRichiesta = Integer.parseInt(request.getParameter("id"));
        boolean valutazione = Boolean.parseBoolean(request.getParameter("valutazione"));
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

    public static void visualizzaRichiesteSingoloUtenteMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Utente u = (Utente)session.getAttribute("Profilo");
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        List<RichiestaModificaScheda> richiesteModifica = null;
        try {
            richiesteModifica = services.visualizzaModifica(u, u);
            String json = new Gson().toJson(richiesteModifica);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void visualizzaSingolaRichiestaMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
}
