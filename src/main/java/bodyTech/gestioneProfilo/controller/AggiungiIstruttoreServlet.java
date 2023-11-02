package bodyTech.gestioneProfilo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet che consente ad un Amministratore di aggiungere un Istruttore tramite un form. L'operazione non procede nel caso
 * in cui la matricola appartenga ad un altro istruttore, e nel caso in cui la lunghezza della matricola sia errata.
 */
@WebServlet(name = "AggiungiIstruttoreServlet", value = "/AggiungiIstruttore")
public class AggiungiIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController gestioneProfiloController = new GestioneProfiloController();
        gestioneProfiloController.aggiungiIstruttoreMethod(request, response);
    }
}
