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
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginController.loginMethod(request, response);
    }

}
