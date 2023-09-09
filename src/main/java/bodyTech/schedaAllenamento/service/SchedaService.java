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
     * Questo metodo consente ad un istruttore di recuperare la lista di tutte le schede di allenamento salvate nel DB.
     * @param p il profilo che invoca questo metodo
     * @return
     */
    public List<SchedaAllenamento> visualizzaSchede (Profilo p) throws SQLException;

    /**
     * Questo metodo consente l'eliminazione di un esercizio da una scheda di allenamento.
     * @param schedaID id della scheda dalla quale verrà rimosso l'esercizio
     * @param nomeEsercizio nome dell'esercizio che verrà rimosso
     * @throws SQLException
     */
    public void eliminaEsercizio (int schedaID, String nomeEsercizio) throws SQLException;

    /**
     * Questo metodo consente di modificare gli attributi di una scheda di allenamento e i singoli suoi esercizi.
     * @param sa scheda che verrà modificata
     * @param u utente della scheda che verrà modifcata
     * @throws SQLException
     */
    public void modificaSchedaUtente (SchedaAllenamento sa, Utente u) throws SQLException;

    /**
     * Questo metodo consente l'eliminazione di una scheda d'allenamento.
     * @param scheda che verrà eliminata
     * @return scheda eliminata
     * @throws SQLException
     */
    public SchedaAllenamento rimuoviSchedaUtente (SchedaAllenamento scheda) throws SQLException;

    /**
     * Questo metodo consente l'inserimento di un esercizio in una scheda di allenamento.
     * @param es esercizio da aggiungere
     * @param volume volume dell'esercizio
     * @param scheda scheda di allenamento alla quale verrà aggiunto l'esercizio
     * @throws SQLException
     */
    public void aggiungiEsercizio (Esercizio es, String volume, SchedaAllenamento scheda) throws SQLException;
}
