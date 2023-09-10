package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Profilo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Amministratore di eliminare un Istruttore, il quale viene selezionato dalla sua matricola.
 */
@WebServlet(name = "EliminaIstruttoreServlet", urlPatterns = {"/EliminaIstruttoreServlet"})
public class EliminaIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String matricola = request.getParameter("mat");

        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.eliminaIstruttore((Profilo)request.getSession().getAttribute("Profilo"), IstruttoreDAO.findByMatricola(matricola));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

