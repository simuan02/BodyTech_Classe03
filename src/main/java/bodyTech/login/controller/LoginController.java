package bodyTech.login.controller;

import bodyTech.login.service.LoginService;
import bodyTech.login.service.LoginServiceImpl;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Questa Servlet, mediante il metodo doPost(request, response) consente di effettuare l'accesso alla piattaforma a un
 * Utente, Istruttore o Amministratore con credenziali valide, ossia con identificativo e password corrispondenti.
 * Questa Servlet decide, in base al tipo d'identificativo, quale Profilo sta tentando l'accesso:
 * - Se l'identificativo è un intero, allora l'accesso è tentato da un Amministratore;
 * - Se l'identificativo è una Stringa di 16 caratteri, allora l'accesso è tentato da un Utente;
 * - Se l'identificativo è una Stringa di 10 caratteri, allora l'accesso è tentato da un Istruttore.
 * Infine, in base alla risposta fornita dal metodo LoginService.Login() si decide se consentire l'accesso o meno.
 */
@WebServlet(name = "LoginController", value = "/ProfileLogin")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");
        String dispatcherPath = "";
        LoginService services = new LoginServiceImpl();
        boolean adminCode = true;
        for (int j = 0 ; j < identifier.length(); j++) {
            if (identifier.charAt(j) < 48 || identifier.charAt(j)>57)
                adminCode = false;
        }
        if (adminCode){
            int codice = Integer.parseInt(identifier);
            Amministratore a = new Amministratore();
            a.setPassword(password);
            a.setCodice(codice);
            System.out.println(a.getCodice());
            try {
                dispatcherPath = chooseDispatcherPath(request, services.login(a), a);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (identifier.length() == 16) {
                Utente u = new Utente();
                u.setPassword(password);
                u.setCodiceFiscale(identifier);
                System.out.println(u.getCodiceFiscale());
                try {
                    dispatcherPath = chooseDispatcherPath(request, services.login(u), u);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if (identifier.length() == 10){
                Istruttore i = new Istruttore();
                i.setPassword(password);
                i.setMatricolaIstruttore(identifier);
                try {
                    dispatcherPath = chooseDispatcherPath(request, services.login(i), i);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else
                dispatcherPath = chooseDispatcherPath(request, false, null);
        RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherPath);
        dispatcher.forward(request, response);
    }

    private String chooseDispatcherPath(HttpServletRequest request, boolean b, Profilo p){
        String dispatcherPath;
        if (b) {
            dispatcherPath = "/index.jsp";
            HttpSession session = request.getSession();
            session.setAttribute("Profilo", p);
        }
        else {
            dispatcherPath = "/login.jsp";
            request.setAttribute("logged", false);
        }
        return dispatcherPath;
    }
}
