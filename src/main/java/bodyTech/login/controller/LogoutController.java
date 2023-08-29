package bodyTech.login.controller;

import bodyTech.login.service.LoginService;
import bodyTech.login.service.LoginServiceImpl;
import bodyTech.model.entity.Profilo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutController", value = "/Logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Profilo p = (Profilo)session.getAttribute("Profilo");
        LoginService services = new LoginServiceImpl();
        services.logout(p);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
