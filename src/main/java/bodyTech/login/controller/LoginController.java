package bodyTech.login.controller;

import bodyTech.login.service.LoginService;
import bodyTech.login.service.LoginServiceImpl;
import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public static void loginMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");
        String dispatcherPath = "";
        LoginService services = new LoginServiceImpl();
        boolean adminCode = true;
        if (identifier == null || password == null) {
            response.sendError(400, "Identificativo e/o password nullo. Impossibile procedere");
        }
        else {
            for (int j = 0; j < identifier.length(); j++) {
                if (identifier.charAt(j) < 48 || identifier.charAt(j) > 57)
                    adminCode = false;
            }
            if (adminCode) {
                int codice = Integer.parseInt(identifier);
                Amministratore a = new Amministratore();
                a.setPassword(password);
                a.setCodice(codice);
                try {
                    dispatcherPath = chooseDispatcherPath(request, services.login(a), a);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (identifier.length() == 16) {
                Utente u = new Utente();
                u.setPassword(password);
                u.setCodiceFiscale(identifier);
                try {
                    dispatcherPath = chooseDispatcherPath(request, services.login(u), u);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    response.sendError(500);
                }
            } else if (identifier.length() == 10) {
                Istruttore i = new Istruttore();
                i.setPassword(password);
                i.setMatricolaIstruttore(identifier);
                try {
                    dispatcherPath = chooseDispatcherPath(request, services.login(i), i);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    response.sendError(500);
                }
            } else {
                try {
                    dispatcherPath = chooseDispatcherPath(request, false, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(500);
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherPath);
            dispatcher.forward(request, response);
        }
    }


    /**
     * Questo metodo consente di scegliere l'indirizzo URL a cui andare in base all'esito del metodo LoginService.Login.
     * Inoltre, salva a livello di sessione il profilo che ha appena effettuato l'accesso
     * @param request la richiesta HTTP corrente
     * @param b questo parametro indica l'esito del metodo LoginService.login
     * @param p questo parametro rappresenta il profilo costruito solo con le informazioni ottenute dal form
     * @return indirizzo URL di destinazione
     * @throws SQLException
     */
    private static String chooseDispatcherPath(HttpServletRequest request, boolean b, Profilo p) throws SQLException {
        String dispatcherPath;
        if (b) {
            dispatcherPath = "/index.jsp";
            HttpSession session = request.getSession();
            switch(p.loggedUserLevel()){
                case "Utente":
                    Utente tempUser = (Utente)p;
                    Utente u = UtenteDAO.findByCodiceFiscale(tempUser.getCodiceFiscale());
                    session.setAttribute("Profilo", u);
                    break;
                case "Istruttore":
                    Istruttore tempInstructor = (Istruttore)p;
                    Istruttore i = IstruttoreDAO.findByMatricola(tempInstructor.getMatricolaIstruttore());
                    session.setAttribute("Profilo", i);
                    break;
                case "Amministratore":
                    Amministratore tempAdmin = (Amministratore)p;
                    Amministratore a = AmministratoreDAO.findByCodice(tempAdmin.getCodice());
                    session.setAttribute("Profilo", a);
                    break;
            }
        }
        else {
            dispatcherPath = "/login.jsp";
            request.setAttribute("logged", false);
        }
        return dispatcherPath;
    }

    public static void logoutMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        LoginService services = new LoginServiceImpl();
        if (services.logout(p)) {
            session.setAttribute("Profilo", p);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        dispatcher.forward(request, response);
    }
}
