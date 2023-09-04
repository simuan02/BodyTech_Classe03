
import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CreazioneSchedaServlet", urlPatterns = {"/CreazioneSchedaServlet"})
public class CreazioneSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceFiscale = request.getParameter("cf");
        String id = (String) request.getParameter("id");
        String address = "";
        //System.out.println("CF: " + codiceFiscale + " ID: " + id);

        try {
            Utente utente = UtenteDAO.findByCodiceFiscale(codiceFiscale);
            request.setAttribute("utente", utente);
            if (id == null) {
                //prendere tutti i dati;
                String dataInizio = request.getParameter("dataInizio");
                String dataFine = request.getParameter("dataFine");

                String[] eserciziHtml = request.getParameterValues("esercizio");

                if (eserciziHtml != null && eserciziHtml.length > 0) {
                    List<Esercizio> eserciziChecked = new ArrayList<>();
                    System.out.println("Selected Items: " + Arrays.toString(eserciziHtml));
                    /*for (int i = 0; i < eserciziHtml.length; i++) {
                        System.out.println("Esercizio " + i + " " + eserciziHtml[i]);
                        Esercizio e = EsercizioDAO.findByName(eserciziHtml[i]);
                        eserciziChecked.add(e);
                    }*/

                    request.setAttribute("esercizi", eserciziChecked);
                    address = "/creazioneSchedaVolumi.jsp";

                } else {
                    //mandare ad una pagina d'errore;
                }
            }
            else if (Integer.parseInt(id) == 1) {
                List<Esercizio> list = EsercizioDAO.findAll();
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

