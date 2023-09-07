package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Utente
 */
public class UtenteDAO {

    /**
     * Implementa la funzionalità di creare un oggetto Utente e recuperare i suoi attributi dal DB
     * @param rs
     * @return Utente
     * @throws SQLException
     */
    private static Utente setUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setCodiceFiscale(rs.getString(1));
        u.setNome(rs.getString(2));
        u.setCognome(rs.getString(3));
        u.setPassword(rs.getString(4));
        List<RichiestaModificaScheda> rms = RichiestaModificaSchedaDAO.findByUser(u.getCodiceFiscale());
        u.setListeRichiesteModifica(rms);
        return u;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB una lista di tutti gli Utenti presenti
     * @return lista degli Utenti
     * @throws SQLException
     */
    public static List<Utente> visualizzaUtenti() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM utente";
        ResultSet rs = stmt.executeQuery(query);
        List<Utente> utenti = new ArrayList<Utente>();
        while (rs.next()){
            Utente u1 = setUtente(rs);
            utenti.add(u1);
        }
        return utenti;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB l'Utente associato a quel codice fiscale
     * @param codiceFiscale
     * @return Utente
     * @throws SQLException
     */
    public static Utente findByCodiceFiscale (String codiceFiscale) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM utente WHERE codiceFiscale = '" + codiceFiscale + "'";
        ResultSet rs = stmt.executeQuery(query);
        Utente u = null;
        while (rs.next()){
            u = setUtente(rs);
        }
        return u;
    }

    /**
     * Implementa la funzionalità di inserimento di un Utente all'interno del DB
     * @param u l'Utente da inserire nel DB
     * @return true, se l'inserimento è andato a buon fine; false, se esiste già un Utente nel DB con lo stesso codiceFiscale
     * di u
     * @throws SQLException
     */
    public static boolean insertUser (Utente u) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Utente> users = visualizzaUtenti();
        for (Utente user : users){
            if (user.getCodiceFiscale().equalsIgnoreCase(u.getCodiceFiscale()))
                return false;
        }
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Utente values (?,?,?,?)");
        pstmt.setString(1, u.getCodiceFiscale());
        pstmt.setString(2, u.getNome());
        pstmt.setString(3, u.getCognome());
        pstmt.setString(4, u.getPassword());
        pstmt.executeUpdate();
        return true;
    }

    /**
     * Implementa la funzionalità di aggiornamento nel DB delle informazioni di oldUser, se presente, con quelle di newUser.
     * @param oldUser l'Utente da aggiornare
     * @param newUser l'Utente con le informazioni aggiornate
     * @throws SQLException
     */
    public static void updateUser (Utente oldUser, Utente newUser) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Utente> users = visualizzaUtenti();
        boolean existingUser = false;
        for (Utente u : users){
            if (u.getCodiceFiscale().equalsIgnoreCase(oldUser.getCodiceFiscale())) {
                existingUser = true;
                break;
            }
        }
        if (existingUser){
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Utente SET codiceFiscale = ?, nome = ?, cognome = ?, pass = ?" +
                    "WHERE codiceFiscale = ?");
            pstmt.setString(1, newUser.getCodiceFiscale());
            pstmt.setString(2, newUser.getNome());
            pstmt.setString(3, newUser.getCognome());
            pstmt.setString(4, newUser.getPassword());
            pstmt.setString(5, oldUser.getCodiceFiscale());
            pstmt.executeUpdate();
        }
    }

    public static void deleteUser (String codiceFiscale) throws SQLException{
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM utente WHERE codiceFiscale = ?");
        pstmt.setString(1, codiceFiscale);
        pstmt.executeUpdate();
    }
}
