package bodyTech.gestioneProfilo.controller;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente di recuperare un Istruttore tramite la sua matricola dal DB, settarlo nella richiesta e
 * invocare la jsp "ModificaIstruttore" con un forward.
 */
@WebServlet(name = "FindInstructorServlet", urlPatterns = {"/TrovaIstruttore"})
public class FindInstructorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricola = request.getParameter("matricola");
            Istruttore istr = IstruttoreDAO.findByMatricola(matricola);
            request.setAttribute("istruttore", istr);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificaIstruttore.jsp");
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
