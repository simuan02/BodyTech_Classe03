package bodyTech;

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

@WebServlet(name = "VisualizzaSchedaServlet", urlPatterns = {"/VisualizzaSchedaServlet"})
public class VisualizzaSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        if (p.loggedUserLevel().equals("Utente")) {
            Utente utente = (Utente) p;
            try {
                SchedaAllenamento scheda = SchedaAllenamentoDAO.findScehdaByUtente(utente.getCodiceFiscale());
                if (scheda != null) System.out.println("SCHEDA TROVATA");
                else System.out.println("NON ESISTE NESSUNA SCHEDA");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        /*RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
