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

@WebServlet(name = "ListaUtentiServlet", urlPatterns = {"/listaUtenti"})
public class ListaUtentiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            List<Utente> listaUtenti = services.visualizzaUtenti(p);
            if (p.loggedUserLevel().equals("Istruttore")) {
                List<Utente> listaTuoiAssociati = new ArrayList<>();
                List<Utente> listaAssociati = new ArrayList<>();
                List<Utente> listaNonAssociati = new ArrayList<>();
                for (Utente u : listaUtenti) {
                    SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(u.getCodiceFiscale());

                    if (scheda != null) {
                        if (scheda.getIstruttore().getMatricolaIstruttore().equals(((Istruttore)p).getMatricolaIstruttore()))
                            listaTuoiAssociati.add(u);
                        else listaAssociati.add(u);
                    } else
                        listaNonAssociati.add(u);
                }
                request.setAttribute("listaTuoiAssociati", listaTuoiAssociati);
                request.setAttribute("listaAssociati", listaAssociati);
                request.setAttribute("listaNonAssociati", listaNonAssociati);
            } else if (p.loggedUserLevel().equals("Amministratore")) {
                request.setAttribute("listaUtenti", listaUtenti);
            }
            else
                response.sendError(403, "Operazione non consentita!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtentiPage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
