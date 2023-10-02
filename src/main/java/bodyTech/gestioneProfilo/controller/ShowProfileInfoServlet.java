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
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        try {
            if (p!=null){
                request.setAttribute("ServletMostraProfiloLanciata", new Object());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/infoProfile.jsp");
                dispatcher.forward(request, response);
            }
            else
                throw new AccountException();
        } catch (AccountException e) {
            response.sendError(403, "Operazione non consentita!");
        } catch (Exception e2){
            log(e2.getMessage(), e2);
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
