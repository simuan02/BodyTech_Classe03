package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Questa Servlet consente di modificare i dati del profilo corrente all'interno del DB.
 * Se il profilo corrente è null (non si è effettuato l'accesso), oppure se il profilo corrente è di un tipo diverso da
 * Utente, Istruttore ed Amministratore, lancia una pagina di errore con codice 403.
 * La modifica effettiva avverrà tramite il metodo ProfiloService.modificaDati(oldProfile, newProfile).
 * Se la modifica ha avuto esito positivo, ossia il metodo ha restituito true, allora il nuovo profilo diventerà il profilo
 * corrente e la risposta avrà come stato il codice 200.
 * Se, invece, la modifica ha avuto esito negativo, allora la risposta lancerà una pagina di errore con codice 400.
 */
@WebServlet(name = "EditDataServlet", value = "/EditData")
public class EditDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if (services.modificaDati(oldProfile, newProfile)){
                session.setAttribute("Profilo", newProfile);
                response.setStatus(200);
            }
            else
                response.sendError(400);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
