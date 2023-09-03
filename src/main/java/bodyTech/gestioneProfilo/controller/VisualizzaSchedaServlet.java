package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
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

@WebServlet(name = "VisualizzaSchedaServlet", urlPatterns = {"/visualizzaScheda"})
public class VisualizzaSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        try{
            ProfiloService services = new ProfiloServiceImpl();
            SchedaAllenamento sa = services.visualizzaScheda(p);
            if (sa != null)
                request.setAttribute("SchedaAllenamento", sa);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(400, "Errore nella richiesta");
        }
        catch (RuntimeException e2) {
            response.sendError(403, "Richiesta non autorizzata");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scheda.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
