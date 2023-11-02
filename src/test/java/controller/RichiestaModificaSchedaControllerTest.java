package controller;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.controller.RichiestaModificaSchedaController;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

public class RichiestaModificaSchedaControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Utente utente;
    private Istruttore istruttore;
    private boolean valutazione;
    private int id;

    @BeforeEach
    public void setUp() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        utente = UtenteDAO.findByCodiceFiscale("BGSPLX01M23D137A");
        if (utente == null) {
            utente = new Utente();
            utente.setNome("NomeProva");
            utente.setCognome("CognomeProva");
            utente.setCodiceFiscale("BGSPLX01M23D137A");
            utente.setPassword("ABCDEFGHIJ");
            UtenteDAO.insertUser(utente);
        }
    }

    /**
    * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
    * con messaggio di lunghezza 0.
    * */
    @Test
    public void testCreazioneRichiesta_1_1() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("richiesta")).thenReturn("");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(utente);
        RichiestaModificaSchedaController.openRequestMethod(request, response);
        verify(request).setAttribute("richiestaEffettuata", "NO");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
     * con messaggio di lunghezza maggiore di 250 caratteri.
     * */
    @Test
    public void testCreazioneRichiesta_1_2() throws ServletException, IOException {
        when(request.getParameter("richiesta")).thenReturn("Richiedo la modifica della scheda perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio subito due mesi fa che mi ha portato danni permanenti alla gamba. Quindi richiedo al mio istruttore una modifica nella sezione delle gambe. Grazie e buona giornata.");
        when(request.getSession()).thenReturn(session);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(utente);
        RichiestaModificaSchedaController.openRequestMethod(request, response);
        verify(request).setAttribute("richiestaEffettuata", "NO");
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
     * con messaggio di lunghezza compreso tra 1 e 250 caratteri.
     * */
    @Test
    public void testCreazioneRichiesta_1_3() throws ServletException, IOException, SQLException {
        when(request.getParameter("richiesta")).thenReturn("Richiedo al mio istruttore la modifica nella sezione delle gambe perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio che mi ha portato danni permanenti alla gamba. Grazie e buona giornata.");
        when(request.getSession()).thenReturn(session);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(utente);
        RichiestaModificaSchedaController.openRequestMethod(request, response);
        verify(request).setAttribute("richiestaEffettuata", "YES");
        List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser(utente.getCodiceFiscale());
        RichiestaModificaSchedaDAO.deleteRichiesta(listaRichieste.get(listaRichieste.size()-1));
        verify(dispatcher).forward(request, response);
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
    * con esito inesistente
    * */
    @Test
    public void testValutazioneRichiesta_1_1() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(istruttore);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("valutazione")).thenReturn(null);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        RichiestaModificaSchedaController.valutazioneRichiestaMethod(request, response);
        verify(request).setAttribute("valutazioneRichiesta", valutazione);
        verify(dispatcher).forward(request, response);
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
    * con esito = true
    * */
    @Test
    public void testValutazioneRichiesta_1_2() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(istruttore);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("valutazione")).thenReturn(String.valueOf(true));
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        verify(request).setAttribute("valutazioneRichiesta", true);
    }

    /*
     * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
     * con esito = false
     * */
    @Test
    public void testValutazioneRichiesta_1_3() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(istruttore);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("valutazione")).thenReturn(String.valueOf(false));
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        verify(request).setAttribute("valutazioneRichiesta", true);
    }

}

