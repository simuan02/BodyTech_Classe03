package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di una Scheda di Allenamento
 */
public class SchedaAllenamentoDAO {

    /**
     * Implementa la funzionalità di recuperare dal DB tutte le Schede di Allenamento associate all'Istruttore indicato dalla stringa matricola passata
     * @param matricola
     * @return liste delle Schede di Allenamento
     * @throws SQLException
     */
    public static List<SchedaAllenamento> findAllByInstructor (String matricola) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM schedaAllenamento WHERE istruttore = '" + matricola + "'";
        ResultSet rs = stmt.executeQuery(query);
        List<SchedaAllenamento> schede = new ArrayList<>();
        while (rs.next()){
            schede.add(createSchedaAllenamento(rs));
        }
        stmt.close();
        conn.close();
        return schede;
    }

    private static SchedaAllenamento createSchedaAllenamento(ResultSet rs) throws SQLException {
        SchedaAllenamento sa = new SchedaAllenamento();
        sa.setIdScheda(rs.getInt(1));
        sa.setDataInizio(rs.getDate(2));
        sa.setDataCompletamento(rs.getDate(3));
        sa.setTipo(rs.getString(4));
        sa.setUtente(UtenteDAO.findByCodiceFiscale(rs.getString(5)));
        sa.setIstruttore(IstruttoreDAO.findByMatricolaNoSchede(rs.getString(6)));
        sa.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(sa.getIdScheda()));
        return sa;
    }

    /**
     * Implementa la funzionalità di trovare una scheda di allenamento associata ad un Utente specifico, passando
     * come parametro il suo codice fiscale
     * @param codiceFiscale il codice fiscale dell'Utente di cui cercare la Scheda Allenamento
     * @return SchedaAllenamento associata all'Utente che ha come codice fiscale wuello passato come parametro
     * @throws SQLException
     */
    public static SchedaAllenamento findSchedaByUtente (String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM schedaAllenamento WHERE utente = '" + codiceFiscale + "'";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return createSchedaAllenamento(rs);
        }
        return null;
    }

    /**
     * Implementa la funzionalità di ricerca di tutte le schede allenamento presenti all'interno del database.
     * @return lista schede di allenamento
     * @throws SQLException
     */
    public static List<SchedaAllenamento> findAll() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM SchedaAllenamento";
        ResultSet rs = stmt.executeQuery(query);
        List<SchedaAllenamento> listaSchede = new ArrayList<>();
        while(rs.next()){
            SchedaAllenamento sa = createSchedaAllenamento(rs);
            listaSchede.add(sa);
        }
        stmt.close();
        conn.close();
        return listaSchede;
    }

    public static SchedaAllenamento findByID(int schedaID) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM SchedaAllenamento WHERE idScheda = " + schedaID;
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()){
            return createSchedaAllenamento(rs);
        }
        return null;
    }

    public static void updateScheda(SchedaAllenamento currentSa, SchedaAllenamento sa) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE schedaAllenamento SET dataCompletamento = ?, tipo = ? WHERE idScheda = ?");
        Date nuovaDataCompletamento = new Date(sa.getDataCompletamento().getTime());
        pstmt.setDate(1, nuovaDataCompletamento);
        pstmt.setString(2, sa.getTipo());
        pstmt.setInt(3, currentSa.getIdScheda());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void deleteScheda(int idScheda) throws SQLException {
        EsercizioAllenamentoDAO.deleteAllSchedaExercises(idScheda);
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM SchedaAllenamento WHERE idScheda = ?");
        pstmt.setInt(1, idScheda);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void insertScheda(SchedaAllenamento sa) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SchedaAllenamento (dataInizio, dataCompletamento, tipo, utente, istruttore) values" +
                "(?, ?, ?, ?, ?)");
        Date nuovaDataInizio = new Date(sa.getDataInizio().getTime());
        pstmt.setDate(1, nuovaDataInizio);
        Date nuovaDataCompletamento = new Date(sa.getDataCompletamento().getTime());
        pstmt.setDate(2, nuovaDataCompletamento);
        pstmt.setString(3, sa.getTipo());
        pstmt.setString(4, sa.getUtente().getCodiceFiscale());
        pstmt.setString(5, sa.getIstruttore().getMatricolaIstruttore());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
