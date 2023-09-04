package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Istruttore;
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
            SchedaAllenamento scheda = new SchedaAllenamento();
            scheda.setIdScheda(rs.getInt(1));
            scheda.setDataInizio(rs.getDate(2));
            scheda.setDataCompletamento(rs.getDate(3));
            scheda.setTipo(rs.getString(4));
            scheda.setUtente(UtenteDAO.findByCodiceFiscale(rs.getString(5)));
            scheda.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(rs.getInt(1)));
            schede.add(scheda);
        }
        return schede;
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
            SchedaAllenamento scheda = new SchedaAllenamento();
            scheda.setIdScheda(rs.getInt(1));
            scheda.setDataInizio(rs.getDate(2));
            scheda.setDataCompletamento(rs.getDate(3));
            scheda.setTipo(rs.getString(4));
            scheda.setUtente(UtenteDAO.findByCodiceFiscale(codiceFiscale));
            scheda.setIstruttore(IstruttoreDAO.findByMatricola(rs.getString(6)));
            scheda.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(rs.getInt(1)));
            return scheda;
        }
        return null;
    }
}
