package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.SchedaAllenamento;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FindSchedaServlet", value = "/FindScheda")
public class FindSchedaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int schedaID = Integer.parseInt(request.getParameter("idScheda"));
        try {
            SchedaAllenamento sa = SchedaAllenamentoDAO.findByID(schedaID);
            request.setAttribute("SchedaAllenamento", sa);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/modificaScheda.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
