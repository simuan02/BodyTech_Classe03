package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import com.mysql.cj.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AggiungiIstruttoreServlet", value = "/AggiungiIstruttore")
public class AggiungiIstruttoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if (i.getMatricolaIstruttore().length() == 10) {
                for (Istruttore istr : services.visualizzaIstruttori(p)) {
                    if (istr.getMatricolaIstruttore().equalsIgnoreCase(i.getMatricolaIstruttore())) {
                        request.setAttribute("CodiceGiaPresente", true);
                        b = false;
                    }
                }
                if (b) {
                    services.aggiungiIstruttore(p, i);
                    List<Istruttore> istruttori = (List<Istruttore>)session.getAttribute("listaIstruttori");
                    i.setListaSchedeCreate(SchedaAllenamentoDAO.findAllByInstructor(i.getMatricolaIstruttore()));
                    istruttori.add(i);
                    session.setAttribute("listaIstruttori", istruttori);
                }
            }
            else {
                request.setAttribute("LunghezzaMatricolaErrata", true);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listaIstruttoriPage.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
