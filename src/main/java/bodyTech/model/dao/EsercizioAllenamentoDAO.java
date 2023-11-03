package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Esercizio;
import bodyTech.model.entity.EsercizioAllenamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Esercizio contenuto in una scheda di allenamento
 */
public class EsercizioAllenamentoDAO {

    /**
     * Implementa la funzionalità di recuperare dal DB la lista degli Esercizi della scheda di allenamento che ha
     * quell'ID come parametro.
     * @param schedaID id della scheda da cercare
     * @return lista degli Esercizi
     * @throws SQLException
     */
    public static List<EsercizioAllenamento> findBySchedaID(int schedaID) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizioAllenamento WHERE schedaAllenamento = " + schedaID;
        ResultSet rs = stmt.executeQuery(query);
        List<EsercizioAllenamento> esercizi = new ArrayList<>();
        while (rs.next()) {
            EsercizioAllenamento es = new EsercizioAllenamento();
            es.setNomeEsercizio(rs.getString(2));
            es.setVolume(rs.getString(3));
            es.setDescrizione(EsercizioDAO.findByName(es.getNomeEsercizio()).getDescrizione());
            esercizi.add(es);
        }
        stmt.close();
        conn.close();
        return esercizi;
    }

    /**
     * Implementa la funzionalità di inserire un nuovo esercizio in una scheda di allenamento nel DB.
     * @param es l'esercizio da inserire
     * @param volume il volume dell'esercizio da inserire
     * @param idScheda id della scheda alla quale aggiungere l'esercizio
     * @throws SQLException
     */
    public static void insertEsercizioAllenamento(Esercizio es, String volume, int idScheda) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO esercizioAllenamento VALUES (?, ?, ?)");
        pstmt.setInt(1, idScheda);
        pstmt.setString(2, es.getNomeEsercizio());
        pstmt.setString(3, volume);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    /**
     * Implementa la funzionalità di modificare un esercizio della scheda di allenamento sostituendolo
     * con gli attributi dell'esercizio passato come parametro.
     * @param ea l'esercizio i cui attributi si sostituiscono a quelli dell'esercizio già presente nella scheda
     * @param idScheda id della scheda alla quale viene modificato l'esercizio
     * @throws SQLException
     */
    public static void updateEsercizio(EsercizioAllenamento ea, int idScheda) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE esercizioAllenamento SET volume = ? WHERE esercizio = ? and " +
                "schedaAllenamento = ?");
        pstmt.setString(1, ea.getVolume());
        pstmt.setString(2, ea.getNomeEsercizio());
        pstmt.setInt(3, idScheda);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    /**
     * Implementa la funzionalità di eliminare un esercizio da una scheda di allenamento.
     * @param schedaID id della scheda dalla quale rimuovere l'esercizio
     * @param nomeEsercizio nome dell'esercizio da eliminare
     * @throws SQLException
     */
    public static void deleteExercise(int schedaID, String nomeEsercizio) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM esercizioAllenamento WHERE esercizio = ? and schedaAllenamento = ?");
        pstmt.setString(1, nomeEsercizio);
        pstmt.setInt(2, schedaID);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    /**
     * Implementa la funzionalità di eliminare tutti gli esercizi da una scheda di allenamento.
     * @param schedaID id della scheda dalla quale eliminare gli esercizi
     * @throws SQLException
     */
    public static void deleteAllSchedaExercises(int schedaID) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM esercizioAllenamento WHERE schedaAllenamento = ?");
        pstmt.setInt(1, schedaID);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
