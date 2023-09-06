package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Utente;

import java.sql.*;
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

    /**
     * Implementa la funzionalità di aggiornamento delle informazioni nel DB di oldAdmin, se presente, con quelle di newAdmin.
     * @param oldAdmin l'Amministratore da aggiornare
     * @param newAdmin l'Amministratore con le informazioni aggiornate
     * @throws SQLException
     */
    public static void updateAdmin (Amministratore oldAdmin, Amministratore newAdmin) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Amministratore> admins = AmministratoreDAO.visualizzaAdmin();
        boolean existingAdmin = false;
        for (Amministratore a : admins){
            if (a.getCodice() == oldAdmin.getCodice()) {
                existingAdmin = true;
                break;
            }
        }
        if (existingAdmin){
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Amministratore SET codice = ?, nome = ?, cognome = ?, pass = ?" +
                    "WHERE codice = ?");
            pstmt.setInt(1, newAdmin.getCodice());
            pstmt.setString(2, newAdmin.getNome());
            pstmt.setString(3, newAdmin.getCognome());
            pstmt.setString(4, newAdmin.getPassword());
            pstmt.setInt(5, oldAdmin.getCodice());
            pstmt.executeUpdate();
        }
    }
}
