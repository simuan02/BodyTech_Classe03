package model;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class RichiestaModificaSchedaDAOTest {

    private RichiestaModificaScheda richiesta;
    private Utente utente;

    @BeforeEach
    public void init() throws SQLException {
        richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiedo al mio istruttore la modifica nella sezione delle gambe perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio che mi ha portato danni permanenti alla gamba. Grazie e buona giornata.");
        richiesta.setEsito(true);
        utente = UtenteDAO.findByCodiceFiscale("DDMJCP01A06A509F");
        if (utente == null){
            utente = new Utente();
            utente.setNome("Jacopo");
            utente.setCognome("De Dominicis");
            utente.setPassword("ABCDEFGHIJ");
            utente.setCodiceFiscale("DDMJCP01A06A509F");
            UtenteDAO.insertUser(utente);
        }
    }

    /**
    * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.insertNewRequest(richiesta, codiceFiscale),
    *  nel caso in cui rispetti tutte le pre-condizioni.
    * - messaggio di lunghezza compreso tra 1 e 250 caratteri.7
    * - codiceFiscale esistente
    * */
    @Test
    public void testInsertNewRequest_Method_OK() throws SQLException {
        Random rand = new Random();
        String message = "";
        for (int i = 0; i < 249; i++){
            char c = (char) (rand.nextInt(43) + 48);
            message += c;
        }
        richiesta.setMessaggio(message);
        assertTrue("Errore nella creazione della richiesta.", RichiestaModificaSchedaDAO.insertNewRequest(richiesta, utente.getCodiceFiscale()));
        List<RichiestaModificaScheda> listaRichieste = RichiestaModificaSchedaDAO.findByUser("DDMJCP01A06A509F");
        RichiestaModificaSchedaDAO.deleteRichiesta(listaRichieste.get(listaRichieste.size()-1));
    }

    /**
    * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.insertNewRequest(richiesta, codiceFiscale),
    * nel caso in cui il messaggio abbia lunghezza 0.
    * */

    @Test
    public void testInsertNewRequest_Method_1() throws SQLException {
        richiesta.setMessaggio("");
        assertFalse("Richiesta creata con successo, nonostante il messaggio è vuoto",
                RichiestaModificaSchedaDAO.insertNewRequest(richiesta, utente.getCodiceFiscale()));
        RichiestaModificaSchedaDAO.deleteRichiesta(richiesta);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.insertNewRequest(richiesta, codiceFiscale),
     * nel caso in cui il codice fiscale è errato o inesistente.
     * */

    @Test
    public void testInsertNewRequest_Method_2() throws SQLException {
        Random rand = new Random();
        String message = "";
        for (int i = 0; i < 249; i++){
            char c = (char) (rand.nextInt(43) + 48);
            message += c;
        }
        String codiceFiscale = "AAAA";
        assertFalse("Richiesta creata con successo, nonostante il codice fiscale non è corretto o è inesistente",
                RichiestaModificaSchedaDAO.insertNewRequest(richiesta, codiceFiscale));
    }

    /**
     * Questo test verifica il comportamento del metodo deleteRichiesta(richiesta) nel caso in cui la richiesta sia null
     */
    @Test
    public void testDeleteRichiestaMethod_NULL(){
        RichiestaModificaScheda richiesta = null;
        try{
            RichiestaModificaSchedaDAO.deleteRichiesta(richiesta);
            fail ("Nessuna eccezione lanciata dal metodo");
        }
        catch(Exception e){
            assertTrue("E' stata lanciata un'eccezione di un tipo diverso da NullPointerException",
                    e.getClass().getSimpleName().equals("NullPointerException"));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo deleteRichiesta(richiesta) nel caso in cui l'id della richiesta
     * sia presente all'interno del DB
     */
    @Test
    public void testDeleteRichiestaMethod_OK() throws SQLException {
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("ABCDEFGHIJKL");
        String codiceFiscaleUtente = "SPSSMN02B06L845X";
        Utente u = UtenteDAO.findByCodiceFiscale(codiceFiscaleUtente);
        if (u == null){
            u = new Utente();
            u.setNome("Simone");
            u.setCognome("Esposito");
            u.setCodiceFiscale(codiceFiscaleUtente);
            u.setPassword("ABCDEFGHIJ");
            UtenteDAO.insertUser(u);
        }
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, u.getCodiceFiscale());
        List<RichiestaModificaScheda> listaRichiesteUtente = RichiestaModificaSchedaDAO.findByUser(codiceFiscaleUtente);
        richiesta.setIdRichiesta(listaRichiesteUtente.get(listaRichiesteUtente.size()-1).getIdRichiesta());
        try{
            RichiestaModificaSchedaDAO.deleteRichiesta(richiesta);
            assertNull("Nessuna eccezione lanciata dal metodo, ma eliminazione non avvenuta", RichiestaModificaSchedaDAO.findById(richiesta.getIdRichiesta()).getMessaggio());
        }
        catch(Exception e){
            fail("Eccezione lanciata");
        }
    }


    /**
     * Questo caso di test verifica il comportamento del metodo RichiestaDAO.findById(idRichiesta),
     * nel caso in cui rispetto la precondizione che l'id è esistente.
     * */
    @Test
    public void testFindMyId_Method_OK() throws SQLException {
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiesta di Prova");
        String cfRichiesta = UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, cfRichiesta);//richiesta inserita per ovviare al caso in cui non c'è alcuna richiesta nel DB
        int id = 1;
        while (RichiestaModificaSchedaDAO.findById(id).getMessaggio() == null){
            id++;
        }
        assertNotNull("Errore nella ricerca della richiesta", RichiestaModificaSchedaDAO.findById(id).getMessaggio());
        List<RichiestaModificaScheda> richieste = RichiestaModificaSchedaDAO.findByUser(cfRichiesta);
        RichiestaModificaScheda richiestaDaCancellare = richieste.get(richieste.size() - 1);
        RichiestaModificaSchedaDAO.deleteRichiesta(richiestaDaCancellare);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.findByID(idRichiesta),
     * nel caso in cui l'id è inesistente.
     * */
    @Test
    public void testFindById_Method_1() throws SQLException{
        int id = -1;
        assertNull("Richiesta trovata con successo, nonostante l'id non esiste", RichiestaModificaSchedaDAO.findById(id).getMessaggio());
    }

    /**
     * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(RichiestaModificaScheda richiesta),
     * nel caso in cui rispetta tutte le precondizioni:
     * richiesta != null
     * */
    @Test
    public void testCambiaEsitoRichiesta_Method_OK() throws SQLException {
        Random rand = new Random();
        String message = "";
        for (int i = 0; i < 249; i++){
            char c = (char) (rand.nextInt(43) + 48);
            message += c;
        }
        RichiestaModificaScheda richiesta = new RichiestaModificaScheda();
        richiesta.setMessaggio("Richiesta di Prova");
        String cfRichiesta = UtenteDAO.visualizzaUtenti().get(0).getCodiceFiscale();
        RichiestaModificaSchedaDAO.insertNewRequest(richiesta, cfRichiesta);//richiesta inserita per ovviare al caso in cui non c'è alcuna richiesta nel DB
        int id = 1;
        while (RichiestaModificaSchedaDAO.findById(id).getMessaggio() == null){
            id++;
        }
        richiesta.setIdRichiesta(id);
        richiesta.setMessaggio(message);
        richiesta.setEsito(true);
        try{
            RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(richiesta);
            List<RichiestaModificaScheda> richieste = RichiestaModificaSchedaDAO.findByUser(cfRichiesta);
            RichiestaModificaScheda richiestaDaCancellare = richieste.get(richieste.size() - 1);
            RichiestaModificaSchedaDAO.deleteRichiesta(richiestaDaCancellare);
        }
        catch(SQLException e){
            fail ("Errore nel cambiamento dell'esito della richiesta");
        }
    }

    /**
     * Questo caso di test verifica il comportamento del metodo RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(RichiestaModificaScheda richiesta),
     * nel caso in cui la richiesta non esiste
     * */
    @Test
    public void testCambiaEsitoRichiesta_Method_1(){
        RichiestaModificaScheda richiesta = null;
        try{
            RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(richiesta);
            fail ("Richiesta creata con successo nonostante la richiesta non esiste");
        }
        catch(Exception e){
            assertTrue("E' stata lanciata un'eccezione diversa da NullPointerException",
                    e.getClass().getSimpleName().equals("NullPointerException"));
        }
    }

}
