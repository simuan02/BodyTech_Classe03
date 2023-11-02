package model;

import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
