package controller;

import bodyTech.gestioneProfilo.controller.GestioneProfiloController;
import bodyTech.model.entity.Istruttore;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AggiuntaIstruttoreControllerTest {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private GestioneProfiloController controller;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        controller = new GestioneProfiloController();
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * la matricola di lunghezza maggiore di 10
     */
    @Test
    public void testAggiunta_1_1() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("103065012700");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Matricola Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * la matricola di lunghezza minore di 10
     */
    @Test
    public void testAggiunta_1_2() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("10306501");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Matricola Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * nome di lunghezza maggiore di 40
     */
    @Test
    public void testAggiunta_1_3() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("FieryEboseleEnzoEbosseKingsleyEhizibueeee");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Nome Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * cognome di lunghezza maggiore di 40
     */
    @Test
    public void testAggiunta_1_4() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("EboseleFieryEnzoEbosseKingsleyEhizibueeee");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Cognome Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * password di lunghezza maggiore di 32
     */
    @Test
    public void testAggiunta_1_5() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066EboseleEnzoEbosseKingsley");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Password Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * password di lunghezza minore di 8
     */
    @Test
    public void testAggiunta_1_6() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Password Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * specializzazione di lunghezza maggiore di 30
     */
    @Test
    public void testAggiunta_1_7() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("BodybuildingPowerliftingCrossfit");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", false);
        verify(request).setAttribute("Lunghezza Specializzazione Errata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di inserimento di un istruttore che soddisfa le seguenti condizioni:
     * - Matricola di 10 caratteri;
     * - Nome di <= 40 caratteri;
     * - Cognome di <= 40 caratteri;
     * - Password di <= 32  >= 8 caratteri;
     * - Specializzazione di <= 30 caratteri;
     */
    @Test
    public void testAggiunta_1_8() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("Fiery");
        when(request.getParameter("CognomeIstruttore")).thenReturn("Ebosele");
        when(request.getParameter("MatricolaIstruttore")).thenReturn("1030650127");
        when(request.getParameter("PasswordIstruttore")).thenReturn("Udinese066");
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("Bodybuilding");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        controller.aggiungiIstruttoreMethod(request, response);
        verify(request).setAttribute("Registrazione", true);
        verify(dispatcher).forward(request, response);
    }

}
