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
     * agli Istruttori di visualizzare tutte le Richieste di Modifica Scheda non ancora esaminate
     * @param p il profilo corrente
     * @return lista di richieste modifica scheda
     */
    public List<RichiestaModificaScheda> visualizzaModifica(Profilo p) throws SQLException;

    /**
     * Questa funzionalità consente agli Istruttori di valutare le richieste di modifica arrivate.
     * @param richiesta la richiesta da esaminare
     * @param istr l'istruttore che esamina la richiesta
     */
    public void valutaRichistaModifica (RichiestaModificaScheda richiesta, Istruttore istr);
}
