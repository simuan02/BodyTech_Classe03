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
     * Consente di modificare le informazioni (attributi e esercizi) della scheda di allenamento attuale di un utente
     * sostituendole con quelle di una nuova.
     * @param sa la scheda che presenta le informazioni da utilizzare per la modifica
     * @param u utente della scheda da modificare
     * @throws SQLException
     */
    public void modificaSchedaUtente (SchedaAllenamento sa, Utente u) throws SQLException;

    /**
     * Consente l'eliminazione di una scheda d'allenamento.
     * @param scheda da eliminare
     * @return scheda eliminata
     * @throws SQLException
     */
    public SchedaAllenamento rimuoviSchedaUtente (SchedaAllenamento scheda) throws SQLException;

    /**
     * Consente l'inserimento di un esercizio in una scheda di allenamento.
     * @param es esercizio da aggiungere
     * @param volume volume dell'esercizio
     * @param scheda scheda di allenamento alla quale aggiungere l'esercizio
     * @throws SQLException
     */
    public void aggiungiEsercizio (Esercizio es, String volume, SchedaAllenamento scheda) throws SQLException;
}
