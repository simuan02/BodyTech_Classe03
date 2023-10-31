package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
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

/**
 * <p>Servlet che consente ad un Amministratore o Istruttore di visualizzare la lista di tutti gli Utenti, recuperata dal DB.</p>
 * <p>Nel caso in cui sia un Istruttore a richiedere la lista, vengono create 3 liste: una degli utenti associati a quell'Istruttore,
 * una degli utenti associati ad un Istruttore qualunque, e una degli utenti non associati ad un Istruttore.
 * Le 3 liste vengono settate nella richiesta.</p>
 * <p>Nel caso in cui sia un Amministratore a richiedere la lista, viene creata solo una lista di tutti gli utenti,
 * e settata nella richiesta</p>
 * Infine viene invocata la jsp "listaUtentiPage" con un forward.
 */
@WebServlet(name = "ListaUtentiServlet", value = "/listaUtenti")
public class ListaUtentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.listaUtentiMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
