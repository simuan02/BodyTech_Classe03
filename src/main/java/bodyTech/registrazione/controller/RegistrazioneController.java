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
        boolean b = true;
        try {
            for (Utente user : UtenteDAO.visualizzaUtenti()){
                if (user.getCodiceFiscale().equalsIgnoreCase(u.getCodiceFiscale())) {
                    request.setAttribute("CodiceGiaPresente", true);
                    b = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (b) {
            if (services.registrazioneUtente(u)) {
                request.setAttribute("Registrazione", true);
            } else {
                request.setAttribute("Registrazione", false);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
