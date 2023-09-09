package bodyTech.schedaAllenamento.service;

import bodyTech.model.entity.Esercizio;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;
import java.util.List;

/**
 * Questa classe consente le operazioni relative alla gestione, aggiunta o eliminazione di Scheda di Allenamento.
 */
public interface SchedaService {

    /**
     * Questa funzionalità consente agli Istruttori di visualizza una lista di Schede di Allenamento
     * @param p il profilo che invoca questo metodo
     * @return la lista di schede di allenamento presenti nel DB
     */
    public List<SchedaAllenamento> visualizzaSchede (Profilo p) throws SQLException;

    /**
     * Questa funzionalità consente di eliminare un esercizio alla Scheda di Allenamento
     * @param schedaID identificativo della scheda di allenamento da cui eliminare l'esercizio
     * @param nomeEsercizio il nome dell'esercizio da eliminare
     * @throws SQLException
     */
    public void eliminaEsercizio (int schedaID, String nomeEsercizio) throws SQLException;

    /**
     * Questa funzionalità consente di modificare una SchedaAllenamento già esistente, associata ad un Utente.
     * @param sa scheda di allenamento con le informazioni modificate, da aggiornare nel DB
     * @param u l'Utente a cui è associata da scheda da modificare
     * @throws SQLException
     */
    public void modificaSchedaUtente (SchedaAllenamento sa, Utente u) throws SQLException;

    /**
     * Questa funzionalità consente agli Istruttori di eliminare una Scheda di Allenamento
     * @param scheda la scheda di allenamento da eliminare
     * @return la scheda eliminata
     * @throws SQLException
     */
    public SchedaAllenamento rimuoviSchedaUtente (SchedaAllenamento scheda) throws SQLException;

    /**
     * Questa funzionalità consente di aggiungere un esercizio alla Scheda di Allenamento
     * @param es l'esercizio da aggiungere alla scheda di allenamento
     * @param volume il volume associato all'esercizio da aggiungere
     * @param scheda la scheda di allenamento a cui aggiungere l'esercizio
     * @throws SQLException
     */
    public void aggiungiEsercizio (Esercizio es, String volume, SchedaAllenamento scheda) throws SQLException;

    /**
     * Questa funzionalità consente agli Istruttori di creare una Scheda di Allenamento ed associarla ad un Utente.
     * @param p profilo che invoca questa funzionalità
     * @param scheda la scheda di allenamento da aggiungere all'utente
     * @param utente l'utente a cui aggiungere la scheda di allenamento
     */
    public void aggiungiSchedaUtente (Profilo p, SchedaAllenamento scheda, Utente utente) throws SQLException;
}
