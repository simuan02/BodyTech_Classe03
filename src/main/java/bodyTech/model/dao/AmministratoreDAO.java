package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Amministratore
 */
public class AmministratoreDAO{

    /**
     * Implementa la funzionalità di recuperare dal DB una lista di tutti gli Amministratori presenti
     * @return lista degli Amministratori
     * @throws SQLException
     */
    public static List<Amministratore> visualizzaAdmin() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM amministratore";
        ResultSet rs = stmt.executeQuery(query);
        List<Amministratore> admins = new ArrayList<>();
        while (rs.next()) {
            Amministratore admin = new Amministratore();
            admin.setCodice(rs.getInt(1));
            admin.setNome(rs.getString(2));
            admin.setCognome(rs.getString(3));
            admin.setPassword(rs.getString(4));
            admins.add(admin);
        }
        return admins;
    }

    /**
     * Implementa la funzionalità di recuperare le informazioni relative ad un amministratore, partendo dal suo codice identificativo
     * @param codice codice identificativo dell'amministratore
     * @return amministratore associato a "codice"
     * @throws SQLException
     */
    public static Amministratore findByCodice(int codice) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM amministratore WHERE codice = " + codice;
        ResultSet rs = stmt.executeQuery(query);
        Amministratore admin = new Amministratore();
        while (rs.next()) {
            admin.setCodice(rs.getInt(1));
            admin.setNome(rs.getString(2));
            admin.setCognome(rs.getString(3));
            admin.setPassword(rs.getString(4));
        }
        return admin;
    }
}
