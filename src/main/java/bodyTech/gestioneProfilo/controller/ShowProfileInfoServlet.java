package bodyTech.gestioneProfilo.controller;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.entity.Profilo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ShowProfileInfoServlet", value = "/infoProfilo")
public class ShowProfileInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        ProfiloService services = new ProfiloServiceImpl();
        try {
            services.visualizzaProfilo(p);
            request.setAttribute("ServletMostraProfiloLanciata", new Object());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/infoProfile.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(403, "Operazione non consentita!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
