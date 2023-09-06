package bodyTech.gestioneProfilo.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
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

@WebServlet(name = "ListaUtentiServlet", urlPatterns = {"/ListaUtentiServlet"})
public class ListaUtentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SERVLET APERTA");
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        if (p.loggedUserLevel().equals("Istruttore")) {
            Istruttore istr = (Istruttore) p;
            request.setAttribute("Istruttore", istr);
            try {
                List<Utente> listaUtenti = UtenteDAO.visualizzaUtenti();
                System.out.println("UTENTI TROVATI");
                request.setAttribute("listaUtenti", listaUtenti);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtentiPage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
