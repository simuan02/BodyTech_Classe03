package controller;

import bodyTech.gestioneProfilo.controller.GestioneProfiloController;
import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Utente;
import bodyTech.registrazione.controller.RegistrazioneControllerImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ModificaDatiUtenteControllerTest {
    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Utente user;

    @BeforeEach
    public void setUp() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        inserisciUtenteTest();
    }

    public void inserisciUtenteTest() throws SQLException {
        user = new Utente();
        user.setNome("Prova");
        user.setCognome("ABC");
        user.setCodiceFiscale("SPSBCD09D02A301F");
        user.setPassword("ABCDEFGHIJ");
        UtenteDAO.insertUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.codiceFiscale di lunghezza minore di 16 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_1() throws ServletException, IOException {
        when(request.getParameter("CodiceFiscaleUtente")).thenReturn("SPSBCD09D02A301");
        when(request.getParameter("NomeUtente")).thenReturn(user.getNome());
        when(request.getParameter("CognomeUtente")).thenReturn(user.getCognome());
        when(request.getParameter("CodiceFiscaleVecchio")).thenReturn(user.getCodiceFiscale());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaUtenteMethod(request, response);
        verify(request).setAttribute("LunghezzaCodiceErrata", true);
        verify(dispatcher).forward(request, response);
    }


    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.codiceFiscale di lunghezza maggiore di 16 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_2() throws ServletException, IOException {
        when(request.getParameter("CodiceFiscaleUtente")).thenReturn("SPSBCD09D02A301XD");
        when(request.getParameter("NomeUtente")).thenReturn(user.getNome());
        when(request.getParameter("CognomeUtente")).thenReturn(user.getCognome());
        when(request.getParameter("CodiceFiscaleVecchio")).thenReturn(user.getCodiceFiscale());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaUtenteMethod(request, response);
        verify(request).setAttribute("LunghezzaCodiceErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.nome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_3() throws ServletException, IOException {
        when(request.getParameter("CodiceFiscaleUtente")).thenReturn(user.getCodiceFiscale());
        when(request.getParameter("NomeUtente")).thenReturn("SimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHI");
        when(request.getParameter("CognomeUtente")).thenReturn(user.getCognome());
        when(request.getParameter("CodiceFiscaleVecchio")).thenReturn(user.getCodiceFiscale());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaUtenteMethod(request, response);
        verify(request).setAttribute("LunghezzaNomeErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.cognome di lunghezza maggiore di 40 caratteri
     */
    @Test
     public void Test_ModificaDatiUtente_2_4() throws SQLException, ServletException, IOException {
         when(request.getParameter("CodiceFiscaleUtente")).thenReturn(user.getCodiceFiscale());
         when(request.getParameter("NomeUtente")).thenReturn(user.getNome());
         when(request.getParameter("CognomeUtente")).thenReturn("EspositoABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG");
         when(request.getParameter("CodiceFiscaleVecchio")).thenReturn(user.getCodiceFiscale());
         when(request.getParameter("password")).thenReturn(user.getPassword());
         when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
         when(request.getSession()).thenReturn(session);
         when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
         GestioneProfiloController.modificaUtenteMethod(request, response);
         verify(request).setAttribute("LunghezzaCognomeErrata", true);
         verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di:
     * - newUser.cognome di lunghezza minore o uguale a 40 caratteri;
     * - newUser.nome di lunghezza minore o uguale a 40 caratteri;
     * - newUser.codiceFiscale di lunghezza uguale a 16 caratteri;
     */
    @Test
    public void Test_ModificaDatiUtente_2_5() throws SQLException, ServletException, IOException {
        Utente user2 = new Utente();
        user2.setNome("Simon");
        user2.setCognome("Espositx");
        user2.setCodiceFiscale("SPSBCD09D02A301F");
        user2.setPassword("ABCDEFGHIJ");
        when(request.getParameter("CodiceFiscaleUtente")).thenReturn(user2.getCodiceFiscale());
        when(request.getParameter("NomeUtente")).thenReturn(user2.getNome());
        when(request.getParameter("CognomeUtente")).thenReturn(user2.getCognome());
        when(request.getParameter("CodiceFiscaleVecchio")).thenReturn(user.getCodiceFiscale());
        when(request.getParameter("password")).thenReturn(user2.getPassword());
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaUtenteMethod(request, response);
        assertTrue("Modifica non effettuata!", UtenteDAO.visualizzaUtenti().contains(user2));
        UtenteDAO.deleteUser(user2);
        verify(dispatcher).forward(request, response);
    }
}
