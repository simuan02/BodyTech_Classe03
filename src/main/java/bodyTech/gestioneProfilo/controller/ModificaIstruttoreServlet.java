package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet che consente ad un Amministratore di modificare le informazioni di un Istruttore tramite un form. Viene creato
 * un nuovo oggetto Istruttore con le informazioni aggiornate da sostituire a quelle dell'oggetto Istruttore presente nel DB.
 * L'operazione non procede se la nuova matricola è attribuita ad un Istruttore già presente, o se la sua lunghezza è errata.
 */
@WebServlet(name = "ModificaIstruttoreServlet", urlPatterns = {"/ModificaIstruttore"})
public class ModificaIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            log(e.getMessage(), e);
            response.sendError(500);
        } catch (RuntimeException e2){
            response.sendError(403, e2.getMessage());
        } catch (IOException e3){
            if (e3.getMessage().equalsIgnoreCase("Matricola già presente all'interno della piattaforma")) {
                request.setAttribute("CodiceGiaPresente", true);
            } else if (e3.getMessage().equalsIgnoreCase("Lunghezza Matricola Errata")){
                request.setAttribute("LunghezzaMatricolaErrata", true);
            }
            else {
                log(e3.getMessage(), e3);
                response.sendError(500);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttori");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
