package bodyTech.gestioneProfilo.controller;

import bodyTech.model.entity.Profilo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.security.auth.login.AccountException;
import java.io.IOException;

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
        GestioneProfiloController.showProfileInfoServlet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
