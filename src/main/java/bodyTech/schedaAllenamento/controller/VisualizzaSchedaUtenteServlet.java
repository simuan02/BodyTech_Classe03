package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet che consente a Istruttori e Amministratori di visualizzare la scheda associata a un Utente il cui codice è
 * passato come parametro nella richiesta
 */
@WebServlet(name = "VisualizzaSchedaUtenteServlet", urlPatterns = {"/VisualizzaSchedaUtenteServlet"})
public class VisualizzaSchedaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchedaAllenamentoController.visualizzaSchedaUtenteMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

