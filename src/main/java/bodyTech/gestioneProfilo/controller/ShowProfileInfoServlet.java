package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.entity.Profilo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Questa Servlet verifica, tramite il metodo ProfiloService.visualizzaProfilo(Profilo), se il profilo corrente è inizializzato,
 * cioè se si è effettuato l'accesso alla piattaforma.
 * In caso affermativo, allora verrà salvato in request un oggetto, che sarà utilizzato dalla JSP infoProfile.jsp, per
 * verificare l'avvenuta invocazione della Servlet, al fine di evitare invocazioni dirette della JSP sovracitata.
 * Infine, sarà lanciata la JSP infoProfile.jsp.
 * In caso negativo, sarà lanciata una pagina di errore con codice 403.
 */
@WebServlet(name = "ShowProfileInfoServlet", value = "/infoProfilo")
public class ShowProfileInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        ProfiloService services = new ProfiloServiceImpl();
        try {
            services.visualizzaProfilo(p);
            request.setAttribute("ServletMostraProfiloLanciata", new Object());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/infoProfile.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(403, "Operazione non consentita!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
