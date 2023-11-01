package controller;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
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

import static org.mockito.Mockito.*;

public class RichiestaModificaSchedaControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RichiestaModificaSchedaServiceImpl controller;
    private RichiestaModificaScheda richiesta;
    private Utente utente;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        controller = new RichiestaModificaSchedaServiceImpl();
        richiesta = new RichiestaModificaScheda();
        utente = new Utente();
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
    * con messaggio di lunghezza 0.
    * */
    @Test
    public void testCreazioneRichiesta_1_1() throws ServletException, IOException {
        when(request.getParameter("richiesta")).thenReturn("");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(Utente.class);
        controller.richiediModificaScheda(richiesta, utente);
        verify(request).setAttribute("richiestaEffettuata", false);
        verify(request).setAttribute("LunghezzaMessaggioErrato", "Lunghezza Messaggio Errato");
        verify(dispatcher).forward(request, response);
    }

    /*
     * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
     * con messaggio di lunghezza maggiore di 250 caratteri.
     * */
    @Test
    public void testCreazioneRichiesta_1_2() throws ServletException, IOException {
        when(request.getParameter("richiesta")).thenReturn("Richiedo la modifica della scheda perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio subito due mesi fa che mi ha portato danni permanenti alla gamba. Quindi richiedo al mio istruttore una modifica nella sezione delle gambe. Grazie e buona giornata.");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(Utente.class);
        controller.richiediModificaScheda(richiesta, utente);
        verify(request).setAttribute("richiestaEffettuata", false);
        verify(request).setAttribute("LunghezzaMessaggioErrato", "Lunghezza Messaggio Errato");
        verify(dispatcher).forward(request, response);
    }

    /*
     * Questo caso di test verifica come si comporta il sistema in caso di creazione richiesta di modifica scheda
     * con messaggio di lunghezza compreso tra 1 e 250 caratteri.
     * */
    @Test
    public void testCreazioneRichiesta_1_3() throws ServletException, IOException, SQLException {
        when(request.getParameter("richiesta")).thenReturn("Richiedo al mio istruttore la modifica nella sezione delle gambe perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio che mi ha portato danni permanenti alla gamba. Grazie e buona giornata.");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/infoProfile.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("Profilo")).thenReturn(Utente.class);
        controller.richiediModificaScheda(richiesta, utente);
        verify(request).setAttribute("richiestaEffettuata", true);
        verify(dispatcher).forward(request, response);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiesta);
    }

}

