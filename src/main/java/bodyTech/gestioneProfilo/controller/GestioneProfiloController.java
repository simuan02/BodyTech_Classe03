package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.security.auth.login.AccountException;

public class GestioneProfiloController {

    public static void profilePageMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        String address = "";
        if (p == null){
            response.sendError(403, "ACCESSO NEGATO. Non è stato effettuato l'accesso alla piattaforma");
        }
        else {
            switch (p.loggedUserLevel()) {
                case "Utente":
                    Utente utente = (Utente) p;
                    session.setAttribute("Utente", utente);
                    address = "/utentePage.jsp";
                    break;
                case "Istruttore":
                    Istruttore istruttore = (Istruttore) p;
                    session.setAttribute("Istruttore", istruttore);
                    address = "/istruttorePage.jsp";
                    break;
                case "Amministratore":
                    Amministratore amministratore = (Amministratore) p;
                    session.setAttribute("Amministratore", amministratore);
                    address = "/amministratorePage.jsp";
                    break;
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    public void aggiungiIstruttoreMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        String nome = request.getParameter("NomeIstruttore");
        String cognome = request.getParameter("CognomeIstruttore");
        String matricolaIstruttore = request.getParameter("MatricolaIstruttore");
        String password = request.getParameter("PasswordIstruttore");
        String specializzazione = request.getParameter("SpecializzazioneIstruttore");
        Istruttore i = new Istruttore();
        i.setMatricolaIstruttore(matricolaIstruttore);
        i.setNome(nome);
        i.setCognome(cognome);
        i.setPassword(password);
        i.setSpecializzazione(specializzazione);
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        boolean b = true;
        try {
                for (Istruttore istr : services.visualizzaIstruttori(p)) {
                    if (istr.getMatricolaIstruttore().equalsIgnoreCase(i.getMatricolaIstruttore())) {
                        request.setAttribute("CodiceGiaPresente", true);
                        b = false;
                    }
                }
                if (b) {
                    try {
                        services.aggiungiIstruttore(p, i);
                        request.setAttribute("Registrazione", true);
                    }catch (Exception e) {
                        request.setAttribute("Registrazione", false);
                        request.setAttribute(e.getMessage(), true);
                    }
                } else {
                   request.setAttribute("Registrazione", false);
                }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void editDataMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nuovoValore = request.getParameter("newValue");
        String datoModificato = request.getParameter("datoModificato");
        HttpSession session = request.getSession();
        Profilo newProfile = null;
        Profilo oldProfile = (Profilo)session.getAttribute("Profilo");
        if (oldProfile != null){
            switch(oldProfile.loggedUserLevel()){
                case "Utente": {
                    newProfile = new Utente();
                    ((Utente)newProfile).setCodiceFiscale(((Utente)oldProfile).getCodiceFiscale());
                    break;
                }
                case "Istruttore": {
                    newProfile = new Istruttore();
                    ((Istruttore)newProfile).setMatricolaIstruttore(((Istruttore)oldProfile).getMatricolaIstruttore());
                    break;
                }
                case "Amministratore": {
                    newProfile = new Amministratore();
                    ((Amministratore)newProfile).setCodice(((Amministratore)oldProfile).getCodice());
                    break;
                }
                default:
                    response.sendError(403, "Operazione non consentita!");
            }
        }
        else
            response.sendError(403, "Operazione non consentita!");
        newProfile.setPassword(oldProfile.getPassword());
        switch(datoModificato){
            case "nome":
                newProfile.setCognome(oldProfile.getCognome());
                newProfile.setNome(nuovoValore);
                break;
            case "cognome":
                newProfile.setCognome(nuovoValore);
                newProfile.setNome(oldProfile.getNome());
                break;
        }
        ProfiloService services = new ProfiloServiceImpl();
        try {
            boolean x = services.modificaDati(oldProfile, newProfile);
            if (x){
                session.setAttribute("Profilo", newProfile);
                response.setStatus(200);
            }
            else
                response.sendError(400);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    public static void eliminaIstruttoreMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matricola = request.getParameter("mat");

        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.eliminaIstruttore((Profilo)request.getSession().getAttribute("Profilo"), IstruttoreDAO.findByMatricola(matricola));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
        dispatcher.forward(request, response);
    }

    public static void eliminaUtenteMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");

        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.eliminaUtente((Profilo)request.getSession().getAttribute("Profilo"), UtenteDAO.findByCodiceFiscale(codiceFiscale));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
        dispatcher.forward(request, response);
    }

    public static void findInstructorMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricola = request.getParameter("matricola");
            Istruttore istr = IstruttoreDAO.findByMatricola(matricola);
            request.setAttribute("istruttore", istr);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificaIstruttore.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    public static void findUserMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String codiceFiscale = request.getParameter("codiceFiscale");
            Utente u = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            request.setAttribute("Utente", u);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificaUtente.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    public static void informazioniIstruttoreMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matricolaIstruttore = request.getParameter("mat");
        String address = "";
        try {
            Istruttore istr = IstruttoreDAO.findByMatricola(matricolaIstruttore);
            request.setAttribute("istruttore", istr);
            address = "/informazioniIstruttore.jsp";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public static void informazioniUtenteMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        String codiceFiscale = request.getParameter("cf");
        String id = request.getParameter("id");
        String address = "";
        try {
            RichiestaModificaSchedaService requestsServices = new RichiestaModificaSchedaServiceImpl();
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            SchedaAllenamento scheda = SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale);
            List<RichiestaModificaScheda> list = requestsServices.visualizzaModifica(p, utente);
            request.setAttribute("utente", utente);
            request.setAttribute("scheda", scheda);
            request.setAttribute("richieste", list);
            if (Integer.parseInt(id) == 1) {
                request.setAttribute("isAssociato", true);
            } else {
                request.setAttribute("isAssociato", false);
            }

            address = "/informazioniUtente.jsp";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public static void listaIstruttoriMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");
        try {
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            List<Istruttore> listaIstruttori = services.visualizzaIstruttori(p);
            if (p.loggedUserLevel().equals("Amministratore")) {
                request.setAttribute("listaIstruttori", listaIstruttori);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttoriPage.jsp");
                dispatcher.forward(request, response);
            }
            else
                response.sendError(403, "Operazione non consentita!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }

    public static void listaUtentiMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        }
    }


    public static void modificaIstruttoreMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Profilo p = (Profilo)request.getSession().getAttribute("Profilo");
        try {
            String matricola = request.getParameter("MatricolaIstruttore");
            String nome = request.getParameter("NomeIstruttore");
            String cognome = request.getParameter("CognomeIstruttore");
            String vecchiaMatricola = request.getParameter("MatricolaVecchia");
            String specializzazione = request.getParameter("SpecializzazioneIstruttore");
            Istruttore oldIstr = IstruttoreDAO.findByMatricola(vecchiaMatricola);
            Istruttore newIstr = new Istruttore();
            newIstr.setNome(nome);
            newIstr.setCognome(cognome);
            newIstr.setMatricolaIstruttore(matricola);
            newIstr.setPassword(oldIstr.getPassword());
            newIstr.setSpecializzazione(specializzazione);
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(p, oldIstr, newIstr);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        } catch (IOException e3){
            if (e3.getMessage().equalsIgnoreCase("Matricola già presente all'interno della piattaforma")) {
                request.setAttribute("CodiceGiaPresente", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Matricola Errata")) {
                request.setAttribute("LunghezzaMatricolaErrata", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Nome Errata")) {
                request.setAttribute("LunghezzaNomeErrata", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Cognome Errata")){
                request.setAttribute("LunghezzaCognomeErrata", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Specializzazione Errata")){
                request.setAttribute("LunghezzaSpecializzazioneErrata", true);}
            else {
                e3.printStackTrace();
                response.sendError(500);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        }
    }

    public static void modificaUtenteMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Profilo p = (Profilo) request.getSession().getAttribute("Profilo");
        try {
            String codiceFiscale = request.getParameter("CodiceFiscaleUtente");
            String nome = request.getParameter("NomeUtente");
            String cognome = request.getParameter("CognomeUtente");
            String vecchioCodiceFiscale = request.getParameter("CodiceFiscaleVecchio");
            Utente oldUser = UtenteDAO.findByCodiceFiscale(vecchioCodiceFiscale);
            Utente newUser = new Utente();
            newUser.setNome(nome);
            newUser.setCognome(cognome);
            newUser.setCodiceFiscale(codiceFiscale);
            newUser.setPassword(oldUser.getPassword());
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(p, oldUser, newUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            response.sendError(500);
            e.printStackTrace();
        } catch (RuntimeException e2) {
            response.sendError(403, e2.getMessage());
        } catch (IOException e3) {
            if (e3.getMessage().equalsIgnoreCase("Codice Fiscale già presente all'interno della piattaforma")) {
                request.setAttribute("CodiceGiaPresente", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Codice Errata")) {
                request.setAttribute("LunghezzaCodiceErrata", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Nome Errata")){
                request.setAttribute("LunghezzaNomeErrata", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Cognome Errata")) {
                request.setAttribute("LunghezzaCognomeErrata", true);
            }
            else {
                e3.printStackTrace();
                response.sendError(500);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        }
    }

    public static void showProfileInfoServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        try {
            if (p!=null){
                request.setAttribute("ServletMostraProfiloLanciata", new Object());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/infoProfile.jsp");
                dispatcher.forward(request, response);
            }
            else
                throw new AccountException();
        } catch (AccountException e) {
            response.sendError(403, "Operazione non consentita!");
        } catch (Exception e2){
            e2.printStackTrace();
            response.sendError(500);
        }
    }

    public static void visualizzaSchedaMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo) session.getAttribute("Profilo");

        try{
            ProfiloService services = new ProfiloServiceImpl();
            SchedaAllenamento sa = services.visualizzaScheda(p);
            if (sa != null)
                request.setAttribute("SchedaAllenamento", sa);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(400, "Errore nella richiesta");
        }
        catch (RuntimeException e2) {
            response.sendError(403, "Richiesta non autorizzata");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scheda.jsp");
        dispatcher.forward(request, response);
    }
}
