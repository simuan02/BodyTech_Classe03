package bodyTech.registrazione.controller;

import bodyTech.model.entity.Utente;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

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
        if (services.registrazioneUtente(u)){
            request.setAttribute("Registrazione", true);
        }
        else {
            request.setAttribute("Registrazione", false);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
