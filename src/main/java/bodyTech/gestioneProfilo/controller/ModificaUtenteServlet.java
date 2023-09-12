package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Profilo;
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

/**
 * Servlet che consente ad un Amministratore o Istruttore di modificare le informazioni di un Utente tramite un form.
 * Viene creato un nuovo oggetto Utente con le informazioni aggiornate da sostituire a quelle dell'oggetto Utente presente nel DB.
 * L'operazione non procede se il nuovo codice fiscale è attribuito ad un Utente già presente, o se la sua lunghezza è errata.
 */
@WebServlet(name = "ModificaUtenteServlet", urlPatterns = {"/ModificaUtente"})
public class ModificaUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Profilo p = (Profilo)request.getSession().getAttribute("Profilo");
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
            log(e.getMessage(), e);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        } catch (IOException e3) {
            if (e3.getMessage().equalsIgnoreCase("Codice Fiscale già presente all'interno della piattaforma")) {
                request.setAttribute("CodiceGiaPresente", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Codice Errata")){
                request.setAttribute("LunghezzaCodiceErrata", true);
            }
            else {
                log(e3.getMessage(), e3);
                response.sendError(500);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaUtenti");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
