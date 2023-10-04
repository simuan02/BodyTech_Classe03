package bodyTech.richiestaModificaScheda.service;

import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;
import java.util.List;

/**
 * Questa classe consente l’operazione di richiesta della modifica della scheda utente.
 */
public interface RichiestaModificaSchedaService {
    /**
     * Questa funzionalità consente agli Utenti di effettuare delle Richieste per la Modifica della Scheda
     * @param richiesta La richiesta di modifica della scheda di allenamento
     * @param u L'Utente corrente
     * @return true, se la richiesta viene creata correttamente, false altrimenti
     */
    public boolean richiediModificaScheda(RichiestaModificaScheda richiesta, Utente u);

    /**
     * Questa funzionalità consente agli Utenti di visualizzare le proprie richieste di modifica effettuate e
     * agli Istruttori di visualizzare tutte le Richieste di Modifica Scheda effettuate dall'utente user
     * @param p il profilo corrente
     * @param user l'utente di cui trovare le richieste di modifica scheda effettuate
     * @return lista di richieste modifica scheda
     */
    public List<RichiestaModificaScheda> visualizzaModifica(Profilo p, Utente user) throws SQLException;


    /**
     * Questa funzionalità consente agli Istruttori di visualizzare una singola richiesta di modifica scheda, in base all'id della
     * richiesta.
     * @param p il profilo corrente
     * @param idRichiesta id della richiesta da trovare
     * @return la richiesta di modifica scheda associata che possiede id = idRichiesta
     * @throws SQLException
     */
    public RichiestaModificaScheda visualizzaSingolaRichiesta(Profilo p, int idRichiesta) throws SQLException;

    /**
     * Questa funzionalità consente agli Istruttori di valutare le richieste di modifica arrivate.
     * @param richiesta la richiesta da esaminare
     * @param istr l'istruttore che esamina la richiesta
     */
    public void valutaRichistaModifica (RichiestaModificaScheda richiesta, Istruttore istr) throws SQLException;
}
