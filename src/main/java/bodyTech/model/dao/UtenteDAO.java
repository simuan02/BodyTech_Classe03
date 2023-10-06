package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Utente
 */
public class UtenteDAO {

    /**
     * Implementa la funzionalità di creare un oggetto Utente e recuperare i suoi attributi dal DB.
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
     * Implementa la funzionalità di recuperare dal DB una lista di tutti gli Utenti presenti.
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
        stmt.close();
        conn.close();
        return utenti;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB l'Utente associato a quel codice fiscale.
     * @param codiceFiscale dell'Utente da cercare
     * @return Utente trovato
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
        stmt.close();
        conn.close();
        return u;
    }

    /**
     * Implementa la funzionalità di inserimento di un Utente all'interno del DB.
     * @param u l'Utente da inserire nel DB
     * @return true, se l'inserimento è andato a buon fine; false, se esiste già un Utente nel DB con lo stesso codiceFiscale
     * di u
     * @throws SQLException
     */
    public static boolean insertUser (Utente u) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Utente> users = visualizzaUtenti();
        if (u.getCodiceFiscale()==null || u.getNome() == null || u.getCognome() == null || u.getPassword() == null)
            return false;
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
        pstmt.close();
        conn.close();
        return true;
    }

    /**
     * Implementa la funzionalità di aggiornamento nel DB delle informazioni di oldUser, se presente, con quelle di newUser.
     * @param oldUser l'Utente da aggiornare
     * @param newUser l'Utente con le informazioni aggiornate
     * @return valore booleano che indica se la modifica è andata a buon fine
     * @throws SQLException
     */
    public static boolean updateUser (Utente oldUser, Utente newUser) throws SQLException, IOException {
        Connection conn = ConPool.getConnection();
        List<Utente> users = visualizzaUtenti();
        if (newUser.getCodiceFiscale()==null || newUser.getNome() == null || newUser.getCognome() == null)
            return false;
        boolean existingUser = false;
        for (Utente u : users){
            if (u.getCodiceFiscale().equalsIgnoreCase(newUser.getCodiceFiscale()) &&
                    !u.getCodiceFiscale().equalsIgnoreCase(oldUser.getCodiceFiscale()))
                throw new IOException("Codice Fiscale già presente all'interno della piattaforma");
            if (u.getCodiceFiscale().equalsIgnoreCase(oldUser.getCodiceFiscale())) {
                existingUser = true;
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
            if (!oldUser.getCodiceFiscale().equals(newUser.getCodiceFiscale())){
                SchedaAllenamento nuovaSchedaUtente = SchedaAllenamentoDAO.findSchedaByUtente(oldUser.getCodiceFiscale());
                if (nuovaSchedaUtente != null) {
                    nuovaSchedaUtente.setUtente(newUser);
                    SchedaAllenamentoDAO.updateScheda(SchedaAllenamentoDAO.findSchedaByUtente(oldUser.getCodiceFiscale()), nuovaSchedaUtente);
                }
            }
            pstmt.close();
            conn.close();
            return true;
        }
        else
            return false;
    }

    /**
     * Implementa la funzionalità di eliminare un Utente dal DB.
     * @param u Utente da eliminare
     * @throws SQLException
     */
    public static void deleteUser(Utente u) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Utente WHERE codiceFiscale = ?");
        SchedaAllenamento sa = SchedaAllenamentoDAO.findSchedaByUtente(u.getCodiceFiscale());
        if (sa!=null) {
            SchedaAllenamentoDAO.deleteScheda(sa.getIdScheda());
        }
        pstmt.setString(1, u.getCodiceFiscale());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
