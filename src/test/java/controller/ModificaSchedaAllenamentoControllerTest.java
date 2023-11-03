package controller;

import bodyTech.model.ConPool;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import bodyTech.schedaAllenamento.controller.SchedaAllenamentoController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ModificaSchedaAllenamentoControllerTest {

    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private SchedaAllenamento scheda;
    private Utente user;

    @BeforeEach
    public void setUp() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        creaUtenteScheda();
        Connection conn = ConPool.getConnection();
        scheda = SchedaAllenamentoDAO.findByID(250);
        if (scheda == null) {
            scheda = new SchedaAllenamento();
            scheda.setIdScheda(250);
            scheda.setTipo("Powerbuilding");
            scheda.setDataCompletamento(new Date(123, Calendar.DECEMBER, 25));
            scheda.setDataInizio(new Date(123, Calendar.OCTOBER, 25));
            scheda.setUtente(user);
            scheda.setIstruttore(IstruttoreDAO.visualizzaIstruttori().get(0));
            scheda.setListaEsercizi(new ArrayList<>());
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO schedaAllenamento values (?,?,?,?,?,?)");
            pstmt.setInt(1, scheda.getIdScheda());
            java.sql.Date dataInizio = new java.sql.Date(scheda.getDataInizio().getTime());
            pstmt.setDate(2, dataInizio);
            java.sql.Date dataCompletamento = new java.sql.Date(scheda.getDataCompletamento().getTime());
            pstmt.setDate(3, dataCompletamento);
            pstmt.setString(4, scheda.getTipo());
            pstmt.setString(5, scheda.getUtente().getCodiceFiscale());
            pstmt.setString(6, scheda.getIstruttore().getMatricolaIstruttore());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }
    }

    public void creaUtenteScheda() throws SQLException {
        user = UtenteDAO.findByCodiceFiscale("SPSSMN02A12L844S");
        if (user==null) {
            user = new Utente();
            user.setNome("Simone");
            user.setCognome("Esposito");
            user.setCodiceFiscale("SPSSMN02A12L844S");
            user.setPassword("ABCDEFGHIJ");
            UtenteDAO.insertUser(user);
        }
    }


    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.editSchedaMethod, nel caso in cui la data
     * di completamento sia precedente alla data di inizio
     */
    @Test
    public void test_ModificaScheda_3_2_1(){
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        when(request.getParameter("DataCompletamento")).thenReturn("2023-10-24");
        when(request.getParameter("idScheda")).thenReturn("" + 250);
        when(request.getParameter("TipoScheda")).thenReturn(scheda.getTipo());
        when(request.getRequestDispatcher("/istruttorePage.jsp")).thenReturn(dispatcher);
        try {
            SchedaAllenamentoController.editSchedaMethod(request, response);
            verify(dispatcher, times(0)).forward(request, response);
        } catch (Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equals("Errato: Data di completamento precedente alla data di inizio"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.editSchedaMethod, nel caso in cui il tipo
     * di scheda fosse di lunghezza maggiore di 30
     */
    @Test
    public void test_ModificaScheda_3_2_2(){
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        when(request.getParameter("DataCompletamento")).thenReturn("2023-12-25");
        when(request.getParameter("idScheda")).thenReturn("" + 250);
        when(request.getParameter("TipoScheda")).thenReturn("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        when(request.getRequestDispatcher("/istruttorePage.jsp")).thenReturn(dispatcher);
        try {
            SchedaAllenamentoController.editSchedaMethod(request, response);
            verify(dispatcher, times(0)).forward(request, response);
        } catch (Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equals("Errato: tipo troppo lungo"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.editSchedaMethod, nel caso in cui il tipo
     * di scheda fosse null
     */
    @Test
    public void test_ModificaScheda_3_2_3() throws SQLException, ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        when(request.getParameter("DataCompletamento")).thenReturn("2023-12-25");
        when(request.getParameter("idScheda")).thenReturn("" + 250);
        when(request.getParameter("TipoScheda")).thenReturn(null);
        when(request.getRequestDispatcher("/istruttorePage.jsp")).thenReturn(dispatcher);
        try {
            SchedaAllenamentoController.editSchedaMethod(request, response);
            verify(dispatcher, times(0)).forward(request, response);
        } catch (Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equals("Errato: tipo assente"));
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaAllenamentoController.editSchedaMethod, nel caso in cui il tipo
     * di scheda fosse di lunghezza compresa tra 1 e 30 caratteri e la data di completamento fosse successiva alla data d'inizio.
     */
    @Test
    public void test_ModificaScheda_3_2_4(){
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(scheda.getIstruttore());
        when(request.getParameter("DataCompletamento")).thenReturn("2023-11-30");
        when(request.getParameter("idScheda")).thenReturn("" + 250);
        when(request.getParameter("TipoScheda")).thenReturn("Powerlifting");
        when(request.getRequestDispatcher("/istruttorePage.jsp")).thenReturn(dispatcher);
        try {
            SchedaAllenamentoController.editSchedaMethod(request, response);
            verify(dispatcher).forward(request, response);
        } catch (Exception e){
            System.out.println(e.getMessage());
            fail("Eccezione lanciata nonostante la correttezza degli input");
        }
    }
}
