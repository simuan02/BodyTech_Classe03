package bodyTech.gestioneProfilo.service;

import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.SQLException;

/**
 * Questa classe fornisce tutte le operazioni di visualizzazione del profilo.
 */
public interface ProfiloService {

    /**
     * Questa funzionalità consente a Utenti, Istruttori ed Amministratori di modificare
     * le informazioni relative al proprio profilo.
     * @param oldProfile il profilo da aggiornare
     * @param newProfile il profilo con le informazioni aggiornate
     * @return l'esito dell'aggiornamento: true, se andato a buon fine; false, altrimenti.
     * @throws SQLException
     */
    public boolean modificaDati (Profilo oldProfile, Profilo newProfile) throws SQLException;


    /**
     * Questa funzionalità consente agli Utenti di visualizzare le informazioni
     * relative alla propria scheda di allenamento, se esistente.
     * @param p il Profilo di cui trovare la Scheda di Allenamento
     * @return scheda di allenamento associata al profilo passato come parametro.
     * @throws SQLException
     */
    public SchedaAllenamento visualizzaScheda (Profilo p) throws SQLException;
}
