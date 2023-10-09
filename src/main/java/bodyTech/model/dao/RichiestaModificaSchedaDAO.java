package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.RichiestaModificaScheda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di una Richiesta di Modifica Scheda
 */
public class RichiestaModificaSchedaDAO {

    /**
     * Implementa la funzionalità di recuperare dal DB tutte le Richieste di modifica scheda associate a quell'utente.
     * @param codiceFiscale dell'utente del quale recuperare le richieste di modifica scheda
     * @return lista delle Richieste trovate
     * @throws SQLException
     */
    public static List<RichiestaModificaScheda> findByUser(String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM richiestaModificaScheda WHERE utente = '" + codiceFiscale + "'";
        ResultSet rs = stmt.executeQuery(query);
        List<RichiestaModificaScheda> richieste = new ArrayList<RichiestaModificaScheda>();
        while (rs.next()){
            RichiestaModificaScheda rms = new RichiestaModificaScheda();
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            rms.setEsito(rs.getBoolean(4));
            richieste.add(rms);
        }
        stmt.close();
        conn.close();
        return richieste;
    }

    public static RichiestaModificaScheda findById(int id) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM richiestaModificaScheda WHERE id = " + id;
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            RichiestaModificaScheda rms = null;
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            rms.setEsito(rs.getBoolean(4));
            return rms;
        }

        return null;
    }

    /**
     * Implementa la funzionalità di aggiungere nel DB una richiesta di modifica scheda creata da un utente.
     * @param richiesta la richiesta di modifica
     * @param codiceFiscale dell'utente che ha creato la richiesta
     * @throws SQLException
     */
    public static void insertNewRequest(RichiestaModificaScheda richiesta, String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RichiestaModificaScheda (Messaggio, Utente, Esito) " +
                "values (?, ?, ?)");
        pstmt.setString(1, richiesta.getMessaggio());
        pstmt.setString(2, codiceFiscale);
        System.out.println(richiesta.isEsito());
        pstmt.setBoolean(3, richiesta.isEsito());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }


}
