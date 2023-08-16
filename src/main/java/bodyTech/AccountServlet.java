package bodyTech;

import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AccountServlet", value = "/AccountServlet")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Profilo p = (Profilo) request.getAttribute("Profilo");

        String address = "";

        switch(p.loggedUserLevel()){
            case "Utente":
                Utente tempUser = (Utente)p;
                Utente u = null;
                try {
                    u = UtenteDAO.findByCodiceFiscale(tempUser.getCodiceFiscale());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                session.setAttribute("Profilo", u);
                address = "utentePage";
                break;
            case "Istruttore":
                Istruttore tempInstructor = (Istruttore)p;
                Istruttore i = null;
                try {
                    i = IstruttoreDAO.findByMatricola(tempInstructor.getMatricolaIstruttore());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                session.setAttribute("Profilo", i);
                address = "istruttorePage";
                break;
            case "Amministratore":
                Amministratore tempAdmin = (Amministratore)p;
                Amministratore a = null;
                try {
                    a = AmministratoreDAO.findByCodice(tempAdmin.getCodice());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                session.setAttribute("Profilo", a);
                address = "amministratorePage";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}