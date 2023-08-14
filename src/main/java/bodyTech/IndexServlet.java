package bodyTech;

import bodyTech.model.entity.Profilo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Questa Servlet ha il compito di inizializzare un profilo nella sessione, allo scopo di utilizzarlo per
 * verificare se è stato fatto l'accesso alla piattaforma e da chi.
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"", "/IndexServlet"}, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = null;
        session.setAttribute("Profilo", p);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
