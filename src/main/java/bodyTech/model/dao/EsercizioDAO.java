package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Esercizio;
import bodyTech.model.entity.EsercizioAllenamento;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Esercizio
 */
public class EsercizioDAO {

    /**
     * Implementa la funzionalità di recuperare dal DB l'Esercizio che contiene la stringa passata come nome
     * @param nome nome dell'esercizio
     * @return Esercizio trovato
     * @throws SQLException
     */
    public static Esercizio findByName (String nome) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizio WHERE nomeEsercizio = '" + nome + "'";
        ResultSet rs = stmt.executeQuery(query);
        Esercizio es = null;
        while (rs.next()){
            es = new Esercizio();
            es.setNomeEsercizio(rs.getString(1));
            es.setDescrizione(rs.getString(2));
        }
        stmt.close();
        conn.close();
        return es;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB la lista di tutti gli esercizi presenti.
     * @return lista degli esercizi trovati
     * @throws SQLException
     */
    public static List<Esercizio> findAll() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizio";
        ResultSet rs = stmt.executeQuery(query);
        List<Esercizio> listaEsercizi = new ArrayList<>();
        while(rs.next()){
            Esercizio es = new Esercizio();
            es.setNomeEsercizio(rs.getString(1));
            es.setDescrizione(rs.getString(2));
            listaEsercizi.add(es);
        }
        stmt.close();
        conn.close();
        return listaEsercizi;
    }

    /**
     * Implementa la funzionalità di recuperare la lista di tutti gli esercizi presenti nel DB che non si trovano attribuiti
     * alla scheda di allenamento il cui id è passato come parametro.
     * @param idScheda id della scheda dalla quale recuperare gli esercizi non presenti
     * @return lista degli esercizi trovati
     * @throws SQLException
     */
    public static List<Esercizio> findAvailableForScheda(int idScheda) throws SQLException {
        List<Esercizio> listaEsercizi = EsercizioDAO.findAll();
        List<EsercizioAllenamento> listaEserciziScheda = EsercizioAllenamentoDAO.findBySchedaID(idScheda);
        List<Esercizio> listaEserciziDisponibili = new ArrayList<>();
        boolean esercizioDisponibile;
        for (Esercizio esercizio: listaEsercizi){
            esercizioDisponibile = true;
            for (EsercizioAllenamento esercizioAllenamento: listaEserciziScheda){
                if (esercizio.getNomeEsercizio().equals(esercizioAllenamento.getNomeEsercizio())) {
                    esercizioDisponibile = false;
                    break;
                }
            }
            if (esercizioDisponibile) {
                listaEserciziDisponibili.add(esercizio);
            }
        }
        return listaEserciziDisponibili;
    }
}
