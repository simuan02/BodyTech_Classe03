package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di una Scheda di Allenamento
 */
public class SchedaAllenamentoDAO {

    /**
     * Implementa la funzionalità di recuperare dal DB tutte le Schede di Allenamento associate a l'Istruttore indicato dalla stringa matricola passata
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
            SchedaAllenamento scheda = new SchedaAllenamento();
            scheda.setIdScheda(rs.getInt(1));
            scheda.setDataInizio(rs.getString(2));
            scheda.setDataCompletamento(rs.getString(3));
            scheda.setTipo(rs.getString(4));
            scheda.setUtente(UtenteDAO.findByCodiceFiscale(rs.getString(5)));
            scheda.setIstruttore(IstruttoreDAO.findByMatricola(matricola));
            scheda.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(rs.getInt(1)));
            schede.add(scheda);
        }
        return schede;
    }

    public static SchedaAllenamento findScehdaByUtente (String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM schedaAllenamento WHERE utente = '" + codiceFiscale + "'";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            SchedaAllenamento scheda = new SchedaAllenamento();
            scheda.setIdScheda(rs.getInt(1));
            scheda.setDataInizio(rs.getString(2));
            scheda.setDataCompletamento(rs.getString(3));
            scheda.setTipo(rs.getString(4));
            scheda.setUtente(UtenteDAO.findByCodiceFiscale(codiceFiscale));
            scheda.setIstruttore(IstruttoreDAO.findByMatricola(rs.getString(6)));
            scheda.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(rs.getInt(1)));
            return scheda;
        }
        return null;
    }

    private static SchedaAllenamento setScheda(ResultSet rs) throws SQLException {
        SchedaAllenamento s = new SchedaAllenamento();
        s.setIdScheda(rs.getInt(1));
        s.setDataInizio(rs.getString(2));
        s.setDataCompletamento(rs.getString(3));
        s.setTipo(rs.getString(4));
        s.setUtente(UtenteDAO.findByCodiceFiscale(rs.getString(5)));
        s.setIstruttore(IstruttoreDAO.findByMatricola(rs.getString(6)));
        return s;
    }

    public static List<SchedaAllenamento> findAllSchede () throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM schedaAllenamento";
        ResultSet rs = stmt.executeQuery(query);
        List<SchedaAllenamento> schede = new ArrayList<SchedaAllenamento>();
        while (rs.next()){
            SchedaAllenamento s1 = setScheda(rs);
            schede.add(s1);
        }
        return schede;
    }

    public static void insertScheda (SchedaAllenamento scheda) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO schedaAllenamento (dataInizio, dataCompletamento, tipo, utente, istruttore) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, scheda.getDataInizio());
            ps.setString(2, scheda.getDataCompletamento());
            ps.setString(3, scheda.getTipo());
            ps.setString(4, scheda.getUtente().getCodiceFiscale());
            ps.setString(5, scheda.getIstruttore().getMatricolaIstruttore());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            scheda.setIdScheda(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
