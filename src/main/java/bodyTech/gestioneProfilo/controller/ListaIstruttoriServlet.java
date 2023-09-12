package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListaIstruttoriServlet", urlPatterns = {"/listaIstruttori"})
public class ListaIstruttoriServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            List<Istruttore> listaIstruttori = services.visualizzaIstruttori(p);
            if (p.loggedUserLevel().equals("Amministratore")) {
                request.setAttribute("listaIstruttori", listaIstruttori);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttoriPage.jsp");
                dispatcher.forward(request, response);
            }
            else
                response.sendError(403, "Operazione non consentita!");
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
