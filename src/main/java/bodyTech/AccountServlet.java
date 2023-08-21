package bodyTech;

import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
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


@WebServlet(name = "AccountServlet", urlPatterns = {"/AccountServlet"})
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        String address = "";
        switch (p.loggedUserLevel()) {
            case "Utente":
                Utente utente = (Utente) p;
                session.setAttribute("Utente", utente);
                address = "/utentePage.jsp";
                break;
            case "Istruttore":
                Istruttore istruttore = (Istruttore) p;
                session.setAttribute("Istruttore", istruttore);
                address = "/istruttorePage.jsp";
                break;
            case "Amministratore":
                Amministratore amministratore = (Amministratore) p;
                session.setAttribute("Amministratore", amministratore);
                address = "/amministratorePage.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
