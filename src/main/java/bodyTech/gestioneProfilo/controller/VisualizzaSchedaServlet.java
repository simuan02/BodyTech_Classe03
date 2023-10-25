package bodyTech.gestioneProfilo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Questa servlet consente la ricerca nel DB della scheda associata al Profilo corrente,
 * tramite la chiamata al metodo ProfiloService.visualizzaScheda(Profilo).
 * Se la chiamata non lancia alcuna eccezione, allora chi ha lanciato la Servlet è autorizzato a farlo, oppure non
 * c'è stato alcun errore.
 * Inoltre, se la chiamata restituisce una scheda di allenamento che non sia null, allora inserisci questa scheda di
 * allenamento all'interno dell'oggetto request.
 */
@WebServlet(name = "VisualizzaSchedaServlet", urlPatterns = {"/visualizzaScheda"})
public class VisualizzaSchedaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestioneProfiloController.visualizzaSchedaMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
