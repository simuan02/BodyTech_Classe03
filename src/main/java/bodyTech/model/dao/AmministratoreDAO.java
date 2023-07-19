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

public class AmministratoreDAO{

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
}
