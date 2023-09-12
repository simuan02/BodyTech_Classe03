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

/**
 * Servlet che consente di recuperare un Utente tramite il suo codice fiscale dal DB, settarlo nella richiesta e
 * invocare la jsp "ModificaUtente" con un forward.
 */
@WebServlet(name = "FindUserServlet", urlPatterns = {"/TrovaUtente"})
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String codiceFiscale = request.getParameter("codiceFiscale");
            Utente u = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            request.setAttribute("Utente", u);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificaUtente.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
