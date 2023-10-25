package controller;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import bodyTech.registrazione.controller.RegistrazioneController;
import bodyTech.registrazione.controller.RegistrazioneControllerImpl;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RegistrazioneUtenteControllerTest {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private RegistrazioneService services;

    private RegistrazioneControllerImpl controller;

    @BeforeEach
    public void setUp() {
        services = mock(RegistrazioneServiceImpl.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        controller = new RegistrazioneControllerImpl();
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente che soddisfa le seguenti condizioni:
     * - Codice Fiscale di 16 caratteri;
     * - Nome di 6 caratteri;
     * - Cognome di 8 caratteri;
     * - Password di 8 caratteri;
     */
    @Test
    public void testRegistrazione_1_6() throws ServletException, IOException, SQLException {
        when(request.getParameter("Nome")).thenReturn("Simone");
        when(request.getParameter("Cognome")).thenReturn("Esposito");
        when(request.getParameter("Identifier")).thenReturn("SPSABC01L93L128X");
        when(request.getParameter("password")).thenReturn("XYZABCDE");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", true);
        verify(dispatcher).forward(request, response);
        Utente user = new Utente();
        user.setCodiceFiscale("SPSABC01L93L128X");
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di registrazione di un utente con il nome di lunghezza
     * maggiore di 40
     */
    @Test
    public void testRegistrazione_1_1 () throws ServletException, IOException{
        when(request.getParameter("Nome")).thenReturn("SimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHI");
        when(request.getParameter("Cognome")).thenReturn("Esposito");
        when(request.getParameter("Identifier")).thenReturn("SPSBCD09D02A301F");
        when(request.getParameter("password")).thenReturn("ABCDEFGHIJ");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("LunghezzaNomeErrata", "Lunghezza Nome Errata");
        verify(dispatcher).forward(request, response);
    }


    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un cognome di
     * lunghezza maggiore di 40
     */
    @Test
    public void testRegistrazione_1_2 () throws ServletException, IOException{
        when(request.getParameter("Nome")).thenReturn("Simone");
        when(request.getParameter("Cognome")).thenReturn("EspositoABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG");
        when(request.getParameter("Identifier")).thenReturn("SPSBCD09D02A301F");
        when(request.getParameter("password")).thenReturn("ABCDEFGHIJ");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("LunghezzaCognomeErrata", "Lunghezza Cognome Errata");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un codice fiscale di
     * lunghezza minore di 16
     */
    @Test
    public void testRegistrazione_1_3 () throws ServletException, IOException {
        when(request.getParameter("Nome")).thenReturn("Simone");
        when(request.getParameter("Cognome")).thenReturn("Esposito");
        when(request.getParameter("Identifier")).thenReturn("SPSBCD09D02A301");
        when(request.getParameter("password")).thenReturn("ABCDEFGHIJ");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("LunghezzaCFErrata", "Lunghezza CF Errata");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un codice fiscale di
     * lunghezza maggiore di 16
     */
    @Test
    public void testRegistrazione_1_4 () throws ServletException, IOException {
        when(request.getParameter("Nome")).thenReturn("Simone");
        when(request.getParameter("Cognome")).thenReturn("Esposito");
        when(request.getParameter("Identifier")).thenReturn("SPSBCD09D02A301X1");
        when(request.getParameter("password")).thenReturn("ABCDEFGHIJ");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("LunghezzaCFErrata", "Lunghezza CF Errata");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con una password di
     * lunghezza minore di 8 caratteri.
     */
    @Test
    public void testRegistrazione_1_5 () throws ServletException, IOException {
        when(request.getParameter("Nome")).thenReturn("Simone");
        when(request.getParameter("Cognome")).thenReturn("Esposito");
        when(request.getParameter("Identifier")).thenReturn("SPSBCD09D02A301X");
        when(request.getParameter("password")).thenReturn("ABCDEFG");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        controller.userRegistration(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("LunghezzaPWErrata", "Lunghezza PW Errata");
        verify(dispatcher).forward(request, response);
    }

}
