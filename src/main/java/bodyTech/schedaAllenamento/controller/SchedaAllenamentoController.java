package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.EsercizioAllenamentoDAO;
import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SchedaAllenamentoController {
    public static void aggiungiEsercizioMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeEsercizio = request.getParameter("SelezionaEsercizio");
        System.out.println(request.getParameter("idScheda"));
        String volume = request.getParameter("Volume");
        int idScheda = Integer.parseInt(request.getParameter("idScheda"));
        SchedaService services = new SchedaServiceImpl();
        try {
            services.aggiungiEsercizio(EsercizioDAO.findByName(nomeEsercizio), volume, SchedaAllenamentoDAO.findByID(idScheda));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void creaListaEserciziMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String[] volumiHtml = request.getParameterValues("volume");
        List<Esercizio> esercizi = (List<Esercizio>) session.getAttribute("listaEsercizi");
        String codiceFiscale = (String) session.getAttribute("codiceFiscale");
        try {
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale);
            SchedaService services = new SchedaServiceImpl();

            for(int i = 0; i < volumiHtml.length; i++) {
                services.aggiungiEsercizio(esercizi.get(i), volumiHtml[i], scheda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
        dispatcher.forward(request, response);
    }

    public static void creazioneSchedaMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            e.printStackTrace();
            response.sendError(500);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public static void deleteExerciseMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        if (p != null && (p.loggedUserLevel().equals("Istruttore") || p.loggedUserLevel().equals("Amministratore"))) {
            SchedaService services = new SchedaServiceImpl();
            String nomeEsercizio = request.getParameter("nomeEsercizio");
            int schedaId = Integer.parseInt(request.getParameter("idScheda"));
            try {
                services.eliminaEsercizio(schedaId, nomeEsercizio);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500);
            }

        }
        else
            response.sendError(403, "Utente non autorizzato");
    }

    public static void deleteSchedaMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idScheda = Integer.parseInt(request.getParameter("idScheda"));
        try {
            SchedaAllenamento sa = SchedaAllenamentoDAO.findByID(idScheda);
            SchedaService services = new SchedaServiceImpl();
            if (services.rimuoviSchedaUtente(sa) != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            } else
                response.sendError(400, "Errore nell'eliminazione della scheda: scheda inesistente");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void editSchedaMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        if (p.loggedUserLevel().equals("Istruttore") || p.loggedUserLevel().equals("Amministratore")){
            int idScheda = Integer.parseInt(request.getParameter("idScheda"));
            SchedaAllenamento sa = null;
            try {
                sa = SchedaAllenamentoDAO.findByID(idScheda);
                String dataCompletamento = request.getParameter("DataCompletamento");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dataCompletamento, formatter);
                java.util.Date date = java.sql.Date.valueOf(localDate);
                sa.setDataCompletamento(date);
                sa.setTipo(request.getParameter("TipoScheda"));
                List<EsercizioAllenamento> listaEsercizi = sa.getListaEsercizi();
                for (EsercizioAllenamento ea: listaEsercizi){
                    ea.setVolume(request.getParameter(ea.getNomeEsercizio()));
                }
                sa.setListaEsercizi(listaEsercizi);
                SchedaService services = new SchedaServiceImpl();
                services.modificaSchedaUtente(sa, sa.getUtente());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500);
            } catch (RuntimeException e2){
                response.sendError(403, e2.getMessage());
            }

        }
        else
            response.sendError(403, "Utente non autorizzato!");
    }

    public static void findSchedaMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int schedaID = Integer.parseInt(request.getParameter("idScheda"));
        try {
            SchedaAllenamento sa = SchedaAllenamentoDAO.findByID(schedaID);
            request.setAttribute("SchedaAllenamento", sa);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/modificaScheda.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void getExercisesMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idScheda = request.getParameter("idScheda");
            List<Esercizio> listaEsercizi = null;
            if (idScheda == null){
                listaEsercizi = EsercizioDAO.findAll();
            }
            else {
                int id = Integer.parseInt(idScheda);
                listaEsercizi = EsercizioDAO.findAvailableForScheda(id);
            }
            String json = new Gson().toJson(listaEsercizi);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void visualizzaSchedaUtenteMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(utente.getCodiceFiscale());
            if (scheda != null) {
                List<EsercizioAllenamento> listaEsercizi = EsercizioAllenamentoDAO.findBySchedaID(scheda.getIdScheda());
                scheda.setListaEsercizi(listaEsercizi);
                request.setAttribute("scheda", scheda);
                request.setAttribute("utente", utente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/schedaAllenamento.jsp");
        dispatcher.forward(request, response);
    }

    public static void visualizzaSchedeMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        SchedaService services = new SchedaServiceImpl();
        try {
            List<SchedaAllenamento> listaSchede = null;
            if (p.loggedUserLevel().equals("Istruttore")) {
                listaSchede = services.visualizzaSchede(p);
            }
            else if (p.loggedUserLevel().equals("Amministratore")){
                String matricola = request.getParameter("mat");
                listaSchede = SchedaAllenamentoDAO.findAllByInstructor(matricola);
            }
            request.setAttribute("listaSchede", listaSchede);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaSchedePage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException re){
            response.sendError(403, "Operazione non consentita");
        }
    }
}
