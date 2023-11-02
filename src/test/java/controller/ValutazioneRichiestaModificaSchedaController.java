package controller;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.controller.RichiestaModificaSchedaController;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ValutazioneRichiestaModificaSchedaController {


    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Utente utente;

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
     * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
     * con esito inesistente
     * */
    @Test
    public void testValutazioneRichiesta_1_1() throws ServletException, IOException, SQLException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(IstruttoreDAO.visualizzaIstruttori().get(0));
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(request.getParameter("valutazione")).thenReturn(null);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        try {
            RichiestaModificaSchedaController.valutazioneRichiestaMethod(request, response);
            fail("Nessuna eccezione lanciata");
        }
        catch(Exception e){
            assertEquals(e.getMessage(), "Valutazione Null");
        }
    }


    /**
     * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda già valutata
     * */
    @Test
    public void testValutazioneRichiesta_1_2() throws SQLException, ServletException, IOException {
        RichiestaModificaScheda richiestaNonEsaminata = new RichiestaModificaScheda();
        richiestaNonEsaminata.setMessaggio("Messaggio Richiesta");
        String cf =  UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiestaNonEsaminata, cf);
        List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser(cf);
        richiestaNonEsaminata.setIdRichiesta(listaRichieste.get(listaRichieste.size()-1).getIdRichiesta());
        richiestaNonEsaminata.setEsito(true);
        RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(richiestaNonEsaminata);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(IstruttoreDAO.visualizzaIstruttori().get(0));
        when(request.getParameter("id")).thenReturn(String.valueOf(richiestaNonEsaminata.getIdRichiesta()));
        when(request.getParameter("valutazione")).thenReturn(String.valueOf(false));
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        RichiestaModificaSchedaController.valutazioneRichiestaMethod(request, response);
        verify(request).setAttribute("richiestaGiaEsaminata", true);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaNonEsaminata);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
     * con esito = true
     * */
    @Test
    public void testValutazioneRichiesta_1_3() throws SQLException, ServletException, IOException {
        RichiestaModificaScheda richiestaNonEsaminata = new RichiestaModificaScheda();
        richiestaNonEsaminata.setMessaggio("Messaggio Richiesta");
        String cf =  UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiestaNonEsaminata, cf);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(IstruttoreDAO.visualizzaIstruttori().get(0));
        List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser(cf);
        richiestaNonEsaminata.setIdRichiesta(listaRichieste.get(listaRichieste.size()-1).getIdRichiesta());
        when(request.getParameter("id")).thenReturn(String.valueOf(richiestaNonEsaminata.getIdRichiesta()));
        when(request.getParameter("valutazione")).thenReturn(String.valueOf(true));
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        RichiestaModificaSchedaController.valutazioneRichiestaMethod(request, response);
        verify(request).setAttribute("valutazioneRichiesta", true);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaNonEsaminata);
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di valutazione richiesta di modifica scheda
     * con esito = false
     * */
    @Test
    public void testValutazioneRichiesta_1_4() throws SQLException, ServletException, IOException {
        RichiestaModificaScheda richiestaNonEsaminata = new RichiestaModificaScheda();
        richiestaNonEsaminata.setMessaggio("Messaggio Richiesta");
        String cf =  UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiestaNonEsaminata, cf);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profilo")).thenReturn(IstruttoreDAO.visualizzaIstruttori().get(0));
        List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser(cf);
        richiestaNonEsaminata.setIdRichiesta(listaRichieste.get(listaRichieste.size()-1).getIdRichiesta());
        when(request.getParameter("id")).thenReturn(String.valueOf(richiestaNonEsaminata.getIdRichiesta()));
        when(request.getParameter("valutazione")).thenReturn(String.valueOf(false));
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        RichiestaModificaSchedaController.valutazioneRichiestaMethod(request, response);
        when(request.getRequestDispatcher("/listaUtenti")).thenReturn(dispatcher);
        verify(request).setAttribute("valutazioneRichiesta", false);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaNonEsaminata);
    }
}
