package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ModificaUtenteServlet", urlPatterns = {"/ModificaUtente"})
public class ModificaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Profilo p = (Profilo)request.getSession().getAttribute("Profilo");
        try {
            String codiceFiscale = request.getParameter("CodiceFiscaleUtente");
            String nome = request.getParameter("NomeUtente");
            String cognome = request.getParameter("CognomeUtente");
            String vecchioCodiceFiscale = request.getParameter("CodiceFiscaleVecchio");
            Utente oldUser = UtenteDAO.findByCodiceFiscale(vecchioCodiceFiscale);
            Utente newUser = new Utente();
            newUser.setNome(nome);
            newUser.setCognome(cognome);
            newUser.setCodiceFiscale(codiceFiscale);
            newUser.setPassword(oldUser.getPassword());
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(p, oldUser, newUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
