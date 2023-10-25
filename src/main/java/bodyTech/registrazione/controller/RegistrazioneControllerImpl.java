package bodyTech.registrazione.controller;

import bodyTech.model.entity.Utente;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class RegistrazioneControllerImpl {

    public void userRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
