package bodyTech.schedaAllenamento.service;

import bodyTech.model.entity.Esercizio;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe che consente le operazioni relative alla gestione, aggiunta o eliminazione di Scheda di Allenamento.
 */
public interface SchedaService {

    /**
     * Consente ad un istruttore di recuperare la lista di tutte le schede di allenamento salvate nel DB.
     * @param p il profilo che invoca questo metodo
     * @return lista di tutte le schede di allenamento
     */
    public List<SchedaAllenamento> visualizzaSchede (Profilo p) throws SQLException;

    /**
     * Consente l'eliminazione di un esercizio da una scheda di allenamento.
     * @param schedaID id della scheda dalla quale rimuovere l'esercizio
     * @param nomeEsercizio nome dell'esercizio da rimuovere
     * @throws SQLException
     */
    public void eliminaEsercizio (int schedaID, String nomeEsercizio) throws SQLException;

    /**
     * Consente di modificare una SchedaAllenamento già esistente, associata ad un Utente.
     * @param sa scheda di allenamento con le informazioni modificate, da aggiornare nel DB
     * @param u l'Utente a cui è associata la scheda da modificare
     * @throws SQLException
     */
    public void modificaSchedaUtente (SchedaAllenamento sa, Utente u) throws SQLException;

    /**
     * Consente agli Istruttoeri l'eliminazione di una scheda d'allenamento.
     * @param scheda da eliminare
     * @return scheda eliminata
     * @throws SQLException
     */
    public SchedaAllenamento rimuoviSchedaUtente (SchedaAllenamento scheda) throws SQLException;

    /**
     * Consente l'inserimento di un esercizio in una scheda di allenamento.
     * @param es esercizio da aggiungere
     * @param volume volume dell'esercizio da aggiungere
     * @param scheda scheda di allenamento alla quale aggiungere l'esercizio
     * @throws SQLException
     */
    public void aggiungiEsercizio (Esercizio es, String volume, SchedaAllenamento scheda) throws SQLException;

    /**
     * Consente agli Istruttori di creare una Scheda di Allenamento ed associarla ad un Utente.
     * @param p profilo che invoca questa funzionalità
     * @param scheda la scheda di allenamento da aggiungere all'utente
     * @param utente l'utente a cui aggiungere la scheda di allenamento
     */
    public void aggiungiSchedaUtente (Profilo p, SchedaAllenamento scheda, Utente utente) throws SQLException;
}
