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
     * Implementa la funzionalità di recuperare dal DB tutte le Richieste di modifica scheda associate a quell'utente
     * @param codiceFiscale
     * @return lista delle Richieste
     * @throws SQLException
     */
    public static List<RichiestaModificaScheda> findByUser(String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM richiestaModificaScheda WHERE utente = '" + codiceFiscale + "'";
        ResultSet rs = stmt.executeQuery(query);
        List<RichiestaModificaScheda> richieste = new ArrayList<RichiestaModificaScheda>();
        List<RichiestaModificaScheda> richiesteModificaNonEsaminate = findNotExaminated();
        while (rs.next()){
            RichiestaModificaScheda rms = new RichiestaModificaScheda();
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            boolean richiestaNonEsaminata = false;
            for (RichiestaModificaScheda rmne : richiesteModificaNonEsaminate) {
                if (rmne.getIdRichiesta() == rms.getIdRichiesta()){
                    richiestaNonEsaminata = true;
                    break;
                }
            }
            if (!richiestaNonEsaminata)
                rms.setEsito(rs.getBoolean(4));
            richieste.add(rms);
        }
        stmt.close();
        conn.close();
        return richieste;
    }

    private static List<RichiestaModificaScheda> findNotExaminated () throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM richiestaModificaScheda WHERE esito IS NULL";
        ResultSet rs = stmt.executeQuery(query);
        List<RichiestaModificaScheda> richiesteNonEsaminate = new ArrayList<>();
        while (rs.next()){
            RichiestaModificaScheda rms = new RichiestaModificaScheda();
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            rms.setEsito(null);
            richiesteNonEsaminate.add(rms);
        }
        stmt.close();
        conn.close();
        return richiesteNonEsaminate;
    }

    public static RichiestaModificaScheda findById(int id) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM richiestaModificaScheda WHERE idRichiesta = " + id + "";
        ResultSet rs = stmt.executeQuery(query);
        RichiestaModificaScheda rms = new RichiestaModificaScheda();
        List<RichiestaModificaScheda> richiesteNonEsaminate = findNotExaminated();
        while (rs.next()){
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            boolean richiestaNonEsaminata = false;
            for (RichiestaModificaScheda rmne : richiesteNonEsaminate) {
                if (rmne.getIdRichiesta() == rms.getIdRichiesta()){
                    richiestaNonEsaminata = true;
                    break;
                }
            }
            if (!richiestaNonEsaminata)
                rms.setEsito(rs.getBoolean(4));
        }
        stmt.close();
        conn.close();
        return rms;
    }

    public static boolean insertNewRequest(RichiestaModificaScheda richiesta, String codiceFiscale) throws SQLException {
        if (richiesta.getMessaggio().length() == 0 || richiesta.getMessaggio() == null)
            return false;
        if (UtenteDAO.findByCodiceFiscale(codiceFiscale) == null)
            return false;
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO richiestaModificaScheda (Messaggio, Utente, Esito) " +
                "values (?, ?, ?)");
        pstmt.setString(1, richiesta.getMessaggio());
        pstmt.setString(2, codiceFiscale);
        if (richiesta.isEsito() != null)
            pstmt.setBoolean(3, richiesta.isEsito());
        else
            pstmt.setNull(3, Types.BOOLEAN);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        return true;
    }

    public static void cambiaEsitoRichiesta(RichiestaModificaScheda richiesta) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE richiestaModificaScheda SET esito = ? WHERE idRichiesta = ?");
        pstmt.setBoolean(1, richiesta.isEsito());
        pstmt.setInt(2, richiesta.getIdRichiesta());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void deleteRichiesta(RichiestaModificaScheda richiesta) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM richiestaModificaScheda WHERE idRichiesta = ?");
        pstmt.setInt(1, richiesta.getIdRichiesta());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
