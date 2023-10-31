package bodyTech.schedaAllenamento.controller;

import bodyTech.model.dao.*;
import bodyTech.model.entity.*;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
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

/**
 * Servlet che consente a Istruttori e Amministratori di creare una lista di EsercizioAllenamento e associarla ad una Scheda
 * di Allenamento
 */
@WebServlet(name = "CreazioneEserciziAllenamentoServlet", urlPatterns = {"/CreazioneEserciziAllenamentoServlet"})
public class CreazioneEserciziAllenamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchedaAllenamentoController.creaListaEserciziMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}

