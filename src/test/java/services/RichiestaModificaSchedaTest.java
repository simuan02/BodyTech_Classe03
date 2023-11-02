package services;

import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaService;
import bodyTech.richiestaModificaScheda.service.RichiestaModificaSchedaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RichiestaModificaSchedaTest {

    private RichiestaModificaScheda richiesta;

    @BeforeEach
    public final void init() {
        richiesta = new RichiestaModificaScheda();
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con utente = null
    * */

    public void testCreazioneRichiesta_1() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = null;
        richiesta.setIdRichiesta(1);
        richiesta.setMessaggio("Richiedo un cambiamento della scheda");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertFalse("Il servizio RichiestaModificaScheda ha un comportamento inatteso per quanto riguarda il parametro Utente",
                    e.getMessage().contains("L'utente non esiste"));
        }
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con lunghezza messaggio = 0.
    */

    public void testCreazioneRichiesta_2() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");

        richiesta.setIdRichiesta(1);
        richiesta.setMessaggio("");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertFalse("Richiesta creata con successo, nonostante il messaggio è vuoto",
                    e.getMessage().contains("Lunghezza messaggio uguale a 0"));
        }
    }

    /*
    * Questo caso di test verifica come si comporta il sistema in caso di inserimento di una richiesta di modifica scheda
    * con lunghezza messaggio > 250
    * */
    public void testCreazioneRichiesta_3() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");

        richiesta.setIdRichiesta(1);
        richiesta.setMessaggio("Richiedo la modifica della scheda perché sono impossibilitato ad allenarmi a questa intensità a causa di un grave infortunio subito due mesi fa che mi ha portato danni permanenti alla gamba. Quindi richiedo al mio istruttore una modifica nella sezione delle gambe. Grazie e buona giornata.");
        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertFalse("Richiesta creata con successo, nonostante il messaggio è troppo lungo",
                    e.getMessage().contains("Lunghezza messaggio maggiore di 250 caratteri"));
        }
    }

    /*
    *
    * */

    public void testCreazioneRichiesta_4() {
        RichiestaModificaSchedaService services = new RichiestaModificaSchedaServiceImpl();
        Utente utente = new Utente();
        utente.setCodiceFiscale("DDMJCP01A06A509F");
        utente.setNome("Jacopo");
        utente.setCognome("De Dominicis");
        utente.setPassword("Jacopo01");

        richiesta.setIdRichiesta(1);
        richiesta.setMessaggio("Richiedo un cambiamento della scheda");

        try {
            services.richiediModificaScheda(richiesta, utente);
        } catch (Exception e) {
            assertTrue("Il servizio RichiestaModificaScheda ha un comportamento inatteso per quanto riguarda il parametro Utente",
                    e.getMessage().contains("Errore inaspettato"));
        }
    }

}
