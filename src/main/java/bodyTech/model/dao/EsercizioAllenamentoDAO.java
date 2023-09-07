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
     * quell'ID come parametro
     * @param schedaID
     * @return lista degli Esercizi
     * @throws SQLException
     */
    public static List<EsercizioAllenamento> findBySchedaID(int schedaID) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizioAllenamento WHERE schedaAllenamento = " + schedaID;
        ResultSet rs = stmt.executeQuery(query);
        List<EsercizioAllenamento> esercizi = new ArrayList<>();
        while (rs.next()){
            EsercizioAllenamento es = new EsercizioAllenamento();
            es.setNomeEsercizio(rs.getString(2));
            es.setVolume(rs.getString(3));
            es.setDescrizione(EsercizioDAO.findByName(es.getNomeEsercizio()).getDescrizione());
            esercizi.add(es);
        }
        return esercizi;
    }

    public static void insertEsercizioAllenamento(Esercizio es, String volume, int idScheda) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO EsercizioAllenamento VALUES (?, ?, ?)");
        pstmt.setInt(1, idScheda);
        pstmt.setString(2, es.getNomeEsercizio());
        pstmt.setString(3, volume);
        pstmt.executeUpdate();
    }

    public static void updateEsercizio(EsercizioAllenamento ea, int idScheda) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE EsercizioAllenamento SET volume = ? WHERE esercizio = ? and " +
                "schedaAllenamento = ?");
        pstmt.setString(1, ea.getVolume());
        pstmt.setString(2, ea.getNomeEsercizio());
        pstmt.setInt(3, idScheda);
        pstmt.executeUpdate();
    }

    public static void deleteEsercizi (int idScheda) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM esercizioAllenamento WHERE schedaAllenamento = ?");
        pstmt.setInt(1, idScheda);
        pstmt.executeUpdate();
    }
}
