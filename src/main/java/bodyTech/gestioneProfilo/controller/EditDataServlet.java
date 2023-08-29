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
