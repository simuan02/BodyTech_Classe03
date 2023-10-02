package bodyTech.schedaAllenamento.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
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

/**
 * Servlet che consente a Istruttori e Amministratori di creare una Scheda di Allenamento ed associarla ad un Utente
 * che non ne ha nessuna associata.
 */
@WebServlet(name = "CreazioneSchedaServlet", urlPatterns = {"/CreazioneSchedaServlet"})
public class CreazioneSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");
        String id = request.getParameter("id");
        String address = "";
        HttpSession session = request.getSession();

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            request.setAttribute("utente", utente);
            if (id == null) {
                //prendere tutti i dati;
                Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
                Date dataFine = Date.valueOf(request.getParameter("dataFine"));
                String tipo = request.getParameter("tipo_input");

                Istruttore istruttore = (Istruttore) session.getAttribute("Profilo");

                String[] eserciziHtml = request.getParameterValues("esercizio");

                if (eserciziHtml != null && eserciziHtml.length > 0) {
                    List<Esercizio> eserciziChecked = new ArrayList<>();
                    for (int i = 0; i < eserciziHtml.length; i++) {
                        Esercizio e = EsercizioDAO.findByName(eserciziHtml[i]);
                        eserciziChecked.add(e);
                    }

                    SchedaAllenamento scheda = new SchedaAllenamento();
                    scheda.setDataInizio(dataInizio);
                    scheda.setDataCompletamento(dataFine);
                    scheda.setTipo(tipo);
                    scheda.setUtente(UtenteDAO.findByCodiceFiscale(codiceFiscale));
                    scheda.setIstruttore(istruttore);

                    SchedaService services = new SchedaServiceImpl();
                    services.aggiungiSchedaUtente(istruttore, scheda, utente);

                    session.setAttribute("listaEsercizi", eserciziChecked);
                    request.setAttribute("listaEsercizi", eserciziChecked);
                    address = "/creazioneSchedaVolumi.jsp";

                } else {
                    response.sendError(400, "Nessun esercizio inserito");
                }
            }
            else if (Integer.parseInt(id) == 1) {
                List<Esercizio> list = EsercizioDAO.findAll();
                session.setAttribute("codiceFiscale", codiceFiscale);
                request.setAttribute("esercizi", list);
                address = "/creazioneScheda.jsp";
            }
            else {
                response.sendError(400);
            }
        } catch (SQLException e) {
            log(e.getMessage(), e);
            response.sendError(500);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}
