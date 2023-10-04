package bodyTech.registrazione.controller;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Questa Servlet consente la registrazione di un utente alla piattaforma.
 * Innanzitutto, vengono prelevati i parametri passati nella richiesta tramite il form. In seguito, questi valori
 * saranno associati ad un nuovo Utente, che, sarà inserito nel DB tramite il metodo
 * RegistrazioneService.registrazioneUtente(Utente), a patto che non sia già presente nel DB un Utente con lo stesso codice fiscale.
 * In tal caso, sarà passata una variabile booleana nell'oggetto request, che consentirà di avvertire l'utente della
 * presenza dello stesso codiceFiscale inserito in fase di registrazione all'ìnterno del DB.
 * Se la registrazione è andata a buon fine, passa nell'oggetto request la variabile booleana "registrazione" con valore true;
 * altrimenti, tale variabile avrà valore false.
 */
@WebServlet(name = "RegistrazioneController", value = "/UserRegistration")
public class RegistrazioneController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("Nome");
        String cognome = request.getParameter("Cognome");
        String codiceFiscale = request.getParameter("Identifier");
        String password = request.getParameter("password");
        Utente u = new Utente();
        u.setCodiceFiscale(codiceFiscale);
        u.setNome(nome);
        u.setCognome(cognome);
        u.setPassword(password);
        RegistrazioneService services = new RegistrazioneServiceImpl();
        List<String> problemiRegistrazione = services.registrazioneUtente(u, password);
        if (problemiRegistrazione.isEmpty()) {
            request.setAttribute("Registrazione", true);
        } else {
            request.setAttribute("Registrazione", false);
            for (String problema : problemiRegistrazione) {
                String problemaNoSpaces = problema.replaceAll(" ", "");
                request.setAttribute(problemaNoSpaces, problema);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
