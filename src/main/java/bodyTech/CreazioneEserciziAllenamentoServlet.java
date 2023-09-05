package bodyTech;

import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CreazioneEserciziAllenamentoServlet", urlPatterns = {"/CreazioneEserciziAllenamentoServlet"})
public class CreazioneEserciziAllenamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "";
        HttpSession session = request.getSession();

        String[] volumiHtml = request.getParameterValues("volume");

        for(int i = 0; i < volumiHtml.length; i++) {
            System.out.println("Volume " + i + ": " + volumiHtml[i]);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

