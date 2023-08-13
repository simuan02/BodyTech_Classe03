package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.RichiestaModificaScheda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        while (rs.next()){
            RichiestaModificaScheda rms = new RichiestaModificaScheda();
            rms.setIdRichiesta(rs.getInt(1));
            rms.setMessaggio(rs.getString(2));
            rms.setEsito(rs.getBoolean(3));
            richieste.add(rms);
        }
        return richieste;
    }
}
