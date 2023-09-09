package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.EsercizioAllenamento;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Servlet che consente ad un Istruttore o Amministratore di modificare
 * la data di completamento, il tipo, e il volume degli esercizi di una scheda di allenamento.
 */
@WebServlet(name = "EditSchedaServlet", value = "/editScheda")
public class EditSchedaServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                Date date = java.sql.Date.valueOf(localDate);
                sa.setDataCompletamento(date);
                sa.setTipo(request.getParameter("TipoScheda"));
                List<EsercizioAllenamento> listaEsercizi = sa.getListaEsercizi();
                for (EsercizioAllenamento ea: listaEsercizi){
                    ea.setVolume(request.getParameter(ea.getNomeEsercizio()));
                    System.out.println(ea.getVolume());
                }
                sa.setListaEsercizi(listaEsercizi);
                SchedaService services = new SchedaServiceImpl();
                services.modificaSchedaUtente(sa, sa.getUtente());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/istruttorePage.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(500, "Errore del Server");
            } catch (RuntimeException e2){
                response.sendError(403, e2.getMessage());
            }
        }
        else
            response.sendError(403, "Utente non autorizzato!");
    }
}
