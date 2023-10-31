package controller;

import bodyTech.gestioneProfilo.controller.GestioneProfiloController;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;
import bodyTech.schedaAllenamento.controller.SchedaAllenamentoController;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CreazioneSchedaAllenamentoControllerTest {

    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private SchedaAllenamento scheda;
    private Utente user;
    private Istruttore istr;

    @BeforeEach
    public void setUp() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        user = UtenteDAO.findByCodiceFiscale("SPSSMN01B04L845A");
        if (user == null){
            creaUtenteScheda();
        }
        creaScheda();
        istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        scheda.setTipo("Powerbuiling");
        scheda.setDataInizio(new Date(123, 9, 25));
        scheda.setDataCompletamento(new Date(123, 11, 25));
    }

    public void creaScheda(){
        scheda = new SchedaAllenamento();
        scheda.setUtente(user);
    }

    public void creaUtenteScheda() throws SQLException {
        user = new Utente();
        user.setNome("Simone");
        user.setCognome("Esposito");
        user.setCodiceFiscale("SPSSMN01B04L845A");
        user.setPassword("ABCDEFGHIJ");
        UtenteDAO.insertUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.creazioneSchedaMethod, nel caso in cui il codice fiscale
     * dell'utente fosse inesistente
     */
    @Test
    public void test_CreazioneScheda_3_1_1(){
        when(request.getParameter("cf")).thenReturn("CODICEERRATO");
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("dataInizio")).thenReturn("2023-10-25");
        when(request.getParameter("dataFine")).thenReturn("2023-12-25");
        when(request.getParameter("tipo_input")).thenReturn(scheda.getTipo());
        String[] esercizi = new String[1];
        esercizi[0] = "Crunch";
        when(request.getParameterValues("esercizio")).thenReturn(esercizi);
        when(request.getRequestDispatcher("/creazioneSchedaVolumi.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        try {
            SchedaAllenamentoController.creazioneSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue("Altra eccezione lanciata", e.getMessage().equals("Codice Fiscale Utente Inesistente"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.creazioneSchedaMethod, nel caso in cui il tipo
     * di scheda fosse di lunghezza maggiore di 30
     */
    @Test
    public void test_CreazioneScheda_3_1_2() throws SQLException, ServletException, IOException {
        when(request.getParameter("cf")).thenReturn(scheda.getUtente().getCodiceFiscale());
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("dataInizio")).thenReturn("2023-10-25");
        when(request.getParameter("dataFine")).thenReturn("2023-12-25");
        when(request.getParameter("tipo_input")).thenReturn("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        String[] esercizi = new String[1];
        esercizi[0] = "Crunch";
        when(request.getParameterValues("esercizio")).thenReturn(esercizi);
        when(request.getRequestDispatcher("/creazioneSchedaVolumi.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        try {
            SchedaAllenamentoController.creazioneSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue("Altra eccezione lanciata: " + e.getMessage(), e.getMessage().equals("Errato: Tipo troppo lungo"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.creazioneSchedaMethod, nel caso in cui il tipo
     * di scheda fosse null
     */
    @Test
    public void test_CreazioneScheda_3_1_3() throws SQLException, ServletException, IOException {
        when(request.getParameter("cf")).thenReturn(scheda.getUtente().getCodiceFiscale());
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("dataInizio")).thenReturn("2023-10-25");
        when(request.getParameter("dataFine")).thenReturn("2023-12-25");
        when(request.getParameter("tipo_input")).thenReturn(null);
        String[] esercizi = new String[1];
        esercizi[0] = "Crunch";
        when(request.getParameterValues("esercizio")).thenReturn(esercizi);
        when(request.getRequestDispatcher("/creazioneSchedaVolumi.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        try {
            SchedaAllenamentoController.creazioneSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            assertTrue("Altra eccezione lanciata: " + e.getMessage(), e.getMessage().equals("Errato: Tipo Assente"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.creazioneSchedaMethod, nel caso in cui la data
     * di completamento sia precedente alla data di inizio
     */
    @Test
    public void test_CreazioneScheda_3_1_4() throws SQLException, ServletException, IOException {
        when(request.getParameter("cf")).thenReturn(scheda.getUtente().getCodiceFiscale());
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("dataInizio")).thenReturn("2023-10-25");
        when(request.getParameter("dataFine")).thenReturn("2023-10-24");
        when(request.getParameter("tipo_input")).thenReturn(scheda.getTipo());
        String[] esercizi = new String[1];
        esercizi[0] = "Crunch";
        when(request.getParameterValues("esercizio")).thenReturn(esercizi);
        when(request.getRequestDispatcher("/creazioneSchedaVolumi.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        try {
            SchedaAllenamentoController.creazioneSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            assertTrue("Altra eccezione lanciata: " + e.getMessage(), e.getMessage().equals("Errato: Data Completamento Precedente alla Data di Inizio"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.creazioneSchedaMethod, nel caso in cui il tipo
     * di scheda fosse di lunghezza compresa tra 1 e 30 caratteri, il codice fiscale fosse esistente e la data di completamento
     * fosse successiva alla data d'inizio.
     */
    @Test
    public void test_CreazioneScheda_3_1_5(){
        when(request.getParameter("cf")).thenReturn(scheda.getUtente().getCodiceFiscale());
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("dataInizio")).thenReturn("2023-10-25");
        when(request.getParameter("dataFine")).thenReturn("2023-12-25");
        when(request.getParameter("tipo_input")).thenReturn(scheda.getTipo());
        String[] esercizi = new String[1];
        esercizi[0] = "Crunch";
        when(request.getParameterValues("esercizio")).thenReturn(esercizi);
        when(request.getRequestDispatcher("/creazioneSchedaVolumi.jsp")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        boolean eccezioneLanciata = false;
        try {
            SchedaAllenamentoController.creazioneSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (Exception e){
            System.out.println(e.getMessage());
            eccezioneLanciata = true;
        }
        assertFalse("Eccezione Non Prevista lanciata", eccezioneLanciata);
        try {
            SchedaAllenamentoDAO.deleteScheda(SchedaAllenamentoDAO.findSchedaByUtente(scheda.getUtente().getCodiceFiscale()).getIdScheda());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
