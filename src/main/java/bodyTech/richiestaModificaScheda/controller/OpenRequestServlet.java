package bodyTech.richiestaModificaScheda.controller;

import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "OpenRequestServlet", value = "/openRequest")
public class OpenRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
