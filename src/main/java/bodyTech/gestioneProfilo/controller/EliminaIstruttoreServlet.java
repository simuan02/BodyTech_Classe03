package bodyTech.gestioneProfilo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet che consente ad un Amministratore di eliminare un Istruttore, il quale viene selezionato dalla sua matricola.
 */
@WebServlet(name = "EliminaIstruttoreServlet", urlPatterns = {"/EliminaIstruttoreServlet"})
public class EliminaIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       GestioneProfiloController.eliminaIstruttoreMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

