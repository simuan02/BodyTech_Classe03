package services;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

public class RichiestaModificaSchedaTest {

    private RichiestaModificaScheda richiesta;

    @BeforeEach
    public final void init() {
        richiesta = new RichiestaModificaScheda();
    }

    /**
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con utente = null
    * */
    @Test
    public void testCreazioneRichiesta_1() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = null;
        richiesta.setIdRichiesta(1);
        richiesta.setMessaggio("Richiedo un cambiamento della scheda");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertTrue("Il servizio RichiestaModificaScheda ha un comportamento inatteso per quanto riguarda il parametro Utente",
                    e.getMessage().contains("L'utente non esiste"));
        }
    }

    /**
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con lunghezza messaggio = 0.
    */
    @Test
    public void testCreazioneRichiesta_2() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");

        richiesta.setMessaggio("");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertTrue("Richiesta creata con successo, nonostante il messaggio è vuoto",
                    e.getMessage().contains("Lunghezza messaggio uguale a 0"));
        }
    }

    /**
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con lunghezza messaggio > 250
    * */
    @Test
    public void testCreazioneRichiesta_3() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");

        richiesta.setMessaggio("Richiedo la modifica della scheda perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio subito due mesi fa che mi ha portato danni permanenti alla gamba. Quindi richiedo al mio istruttore una modifica nella sezione delle gambe. Grazie e buona giornata.");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertTrue("Richiesta creata con successo, nonostante il messaggio è troppo lungo",
                    e.getMessage().contains("Lunghezza messaggio maggiore di 250 caratteri"));
        }
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso d'inserimento di una richiesta di modifica scheda
     * con lunghezza messaggio compresa tra 0 e 250 caratteri e Utente presente nel DB
    * */
    @Test
    public void testCreazioneRichiesta_4() throws SQLException {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");
        if (UtenteDAO.findByCodiceFiscale("DDMJCP01A06A509F") == null)
            UtenteDAO.insertUser(utente);
        richiesta.setMessaggio("Richiedo un cambiamento della scheda");

        try {
            services.richiediModificaScheda(richiesta, utente);
            List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser(utente.getCodiceFiscale());
            RichiestaModificaSchedaDAO.deleteRichiesta(listaRichieste.get(listaRichieste.size()-1));
        } catch (Exception e) {
            fail("Il servizio RichiestaModificaScheda ha un comportamento inatteso per quanto riguarda il parametro Utente");
        }
    }

    /**
     * Questo caso di test verifica il comportamento del service RichiestaModificaSchedaService.valutaRichiestaModifica()
     * in caso in cui l'esito della richiesta è null
     */
    @Test
    public void testValutazioneRichiesta_1() throws SQLException {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Istruttore i = new Istruttore();
        i.setMatricolaIstruttore("100000000P");
        i.setNome("Fabio");
        i.setCognome("Istruttore");
        i.setPassword("FabioP1");
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiesta di Prova");
        String cfRichiesta = UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, cfRichiesta);//richiesta inserita per ovviare al caso in cui non c'è alcuna richiesta nel DB
        int id = 1;
        while (RichiestaModificaSchedaDAO.findById(id).getMessaggio() == null){
            id++;
        }
        richiesta.setIdRichiesta(id);
        try {
            services.valutaRichiestaModifica(richiesta, i);
            fail("Richiesta inserita nonostante l'esito della richiesta fosse nullo");
        } catch (Exception e) {
            assertTrue("Il servizio RichiestaModificaScheda lancia un altro tipo di eccezione, diversa da NullPointerException",
                    e.getClass().getSimpleName().equals("NullPointerException"));
        }
        IstruttoreDAO.deleteInstructor(i);
        List<RichiestaModificaScheda> richieste = RichiestaModificaSchedaDAO.findByUser(cfRichiesta);
        RichiestaModificaScheda richiestaDaCancellare = richieste.get(richieste.size() - 1);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaDaCancellare);
    }

    /**
     * Questo caso di test verifica il comportamento del service RichiestaModificaSchedaService.valutaRichiestaModifica()
     * in caso in cui l'istruttore che valuta la richiesta sia uguale a null
     * */
    @Test
    public void testValutazioneRichiesta_2() throws  SQLException {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Istruttore i = null;
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiesta di Prova");
        String cfRichiesta = UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, cfRichiesta);//richiesta inserita per ovviare al caso in cui non c'è alcuna richiesta nel DB
        int id = 1;
        while (RichiestaModificaSchedaDAO.findById(id).getMessaggio() == null){
            id++;
        }
        richiesta.setIdRichiesta(id);

        try {
            services.valutaRichiestaModifica(richiesta, i);
        } catch (Exception e) {
            assertTrue("Il service non ha tenuto conto dell'istruttore null",
                    e.getMessage().contains("Utente Non Autorizzato!"));
        }
        List<RichiestaModificaScheda> richieste = RichiestaModificaSchedaDAO.findByUser(cfRichiesta);
        RichiestaModificaScheda richiestaDaCancellare = richieste.get(richieste.size() - 1);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaDaCancellare);
    }

    /**
     * Questo caso di test verifica il comportamento del service RichiestaModificaSchedaService.valutaRichiestaModifica()
     * in caso in cui l'istruttore che valuta la richiesta sia esistente e l'esito della richiesta sia true
     * */
    @Test
    public void testValutazioneRichiesta_3() throws SQLException {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Istruttore i = new Istruttore();
        i.setMatricolaIstruttore("100000000P");
        i.setNome("Fabio");
        i.setCognome("Istruttore");
        i.setPassword("FabioP1");
        IstruttoreDAO.insertInstructor(i);
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiesta di Prova");
        String cfRichiesta = UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, cfRichiesta);//richiesta inserita per ovviare al caso in cui non c'è alcuna richiesta nel DB
        int id = 1;
        while (RichiestaModificaSchedaDAO.findById(id).getMessaggio() == null){
            id++;
        }
        richiesta.setIdRichiesta(id);
        richiesta.setMessaggio("Richiedo un cambiamento della scheda");
        richiesta.setEsito(true);
        try{
            services.valutaRichiestaModifica(richiesta, i);
            assertTrue("Richiesta non valutata correttamente", RichiestaModificaSchedaDAO.findById(richiesta.getIdRichiesta()).isEsito());
        }
        catch(Exception e){
            fail ("Eccezione lanciata");
        }
        List<RichiestaModificaScheda> richieste = RichiestaModificaSchedaDAO.findByUser(cfRichiesta);
        RichiestaModificaScheda richiestaDaCancellare = richieste.get(richieste.size() - 1);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaDaCancellare);
    }

}
