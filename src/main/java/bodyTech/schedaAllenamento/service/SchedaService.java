package bodyTech.schedaAllenamento.service;

import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.SQLException;
import java.util.List;

/**
 * Questa classe consente le operazioni relative alla gestione, aggiunta o eliminazione di Scheda di Allenamento.
 */
public interface SchedaService {

    /**
     * Questo metodo consente ad un istruttore di recuperare la lista di tutte le schede di allenamento salvate nel DB
     * @param p il profilo che invoca questo metodo
     * @return
     */
    public List<SchedaAllenamento> visualizzaSchede (Profilo p) throws SQLException;

    public void eliminaEsercizio (int schedaID, String nomeEsercizio) throws SQLException;
}
