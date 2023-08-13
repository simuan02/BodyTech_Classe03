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
     * @param nome
     * @return Esercizio
     * @throws SQLException
     */
    public static Esercizio findByName (String nome) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizio WHERE nomeEsercizio = '" + nome + "'";
        ResultSet rs = stmt.executeQuery(query);
        Esercizio es = new Esercizio();
        while (rs.next()){
            es.setNomeEsercizio(rs.getString(1));
            es.setDescrizione(rs.getString(2));
        }
        return es;
    }
}
