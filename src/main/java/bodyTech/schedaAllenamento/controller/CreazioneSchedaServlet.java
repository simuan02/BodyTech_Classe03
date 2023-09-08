package bodyTech.schedaAllenamento.controller;

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

@WebServlet(name = "bodyTech.schedaAllenamento.controller.CreazioneSchedaServlet", urlPatterns = {"/bodyTech.schedaAllenamento.controller.CreazioneSchedaServlet"})
public class CreazioneSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");
        String id = (String) request.getParameter("id");
        String address = "";
        HttpSession session = request.getSession();

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            request.setAttribute("utente", utente);
            if (id == null) {
                //prendere tutti i dati;
                Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
                Date dataFine = Date.valueOf(request.getParameter("dataFine"));
                String tipo = (String) request.getParameter("tipo_input");


                Istruttore istruttore = (Istruttore) session.getAttribute("Profilo");
                System.out.println("ISTRUTTORE: " + istruttore.getCognome());

                String[] eserciziHtml = request.getParameterValues("esercizio");

                if (eserciziHtml != null && eserciziHtml.length > 0) {
                    List<Esercizio> eserciziChecked = new ArrayList<>();
                    for (int i = 0; i < eserciziHtml.length; i++) {
                        Esercizio e = EsercizioDAO.findByName(eserciziHtml[i]);
                        eserciziChecked.add(e);
                    }

                    codiceFiscale = (String) session.getAttribute("codiceFiscale");

                    SchedaAllenamento scheda = new SchedaAllenamento();
                    scheda.setDataInizio(dataInizio);
                    scheda.setDataCompletamento(dataFine);
                    scheda.setTipo(tipo);
                    scheda.setUtente(UtenteDAO.findByCodiceFiscale(codiceFiscale));
                    scheda.setIstruttore(istruttore);

                    SchedaAllenamentoDAO.insertScheda(scheda);

                    session.setAttribute("listaEsercizi", eserciziChecked);

                    request.setAttribute("eserciziChecked", eserciziChecked);
                    address = "/creazioneSchedaVolumi.jsp";

                } else {
                    //mandare ad una pagina d'errore;
                }
            }
            else if (Integer.parseInt(id) == 1) {
                List<Esercizio> list = EsercizioDAO.findAll();
                session.setAttribute("codiceFiscale", codiceFiscale);
                request.setAttribute("esercizi", list);
                address = "/creazioneScheda.jsp";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

