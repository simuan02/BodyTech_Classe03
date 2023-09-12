package bodyTech;

import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebServlet(name = "VisualizzaSchedaServlet", urlPatterns = {"/VisualizzaSchedaServlet"})
public class VisualizzaSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaSchedePage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

