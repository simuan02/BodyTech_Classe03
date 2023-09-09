package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta il DAO di un Istruttore
 */
public class IstruttoreDAO {

    /**
     * Implementa la funzionalità di creare un oggetto Istruttore e recuperare i suoi attributi dal DB
     *
     * @param rs
     * @return Istruttore
     * @throws SQLException
     */
    public static Istruttore createIstruttore(ResultSet rs) throws SQLException {
        Istruttore istr = new Istruttore();
        istr.setMatricolaIstruttore(rs.getString(1));
        istr.setNome(rs.getString(2));
        istr.setCognome(rs.getString(3));
        istr.setPassword(rs.getString(4));
        istr.setSpecializzazione(rs.getString(5));
        istr.setListaSchedeCreate(SchedaAllenamentoDAO.findAllByInstructor(istr.getMatricolaIstruttore()));
        for (SchedaAllenamento sa : istr.getListaSchedeCreate()) {
            sa.setIstruttore(istr);
        }
        return istr;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB una lista di tutti gli Istruttori presenti
     *
     * @return lista degli Istruttori
     * @throws SQLException
     */
    public static List<Istruttore> visualizzaIstruttori() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM istruttore";
        ResultSet rs = stmt.executeQuery(query);
        List<Istruttore> istruttori = new ArrayList<>();
        while (rs.next()) {
            Istruttore istr = createIstruttore(rs);
            istruttori.add(istr);
        }
        return istruttori;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB l'Istruttore associato a quella matricola
     *
     * @param matricola
     * @return Istruttore
     * @throws SQLException
     */
    public static Istruttore findByMatricola(String matricola) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM istruttore WHERE matricolaIstruttore = '" + matricola + "'";
        ResultSet rs = stmt.executeQuery(query);
        Istruttore istr = null;
        if (rs.next())
            istr = createIstruttore(rs);
        return istr;
    }

    /**
     * Implementa la funzionalità di aggiornare le informazioni di un istruttore nel DB, se già esistente
     *
     * @param oldIstr l'istruttore corrente da aggiornare
     * @param newIstr l'istruttore che contiene le informazioni aggiornate
     * @throws SQLException
     */
    public static void updateInstructor(Istruttore oldIstr, Istruttore newIstr) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Istruttore> instructors = IstruttoreDAO.visualizzaIstruttori();
        boolean existingInstructor = false;
        for (Istruttore i : instructors) {
            if (i.getMatricolaIstruttore().equalsIgnoreCase(oldIstr.getMatricolaIstruttore())) {
                existingInstructor = true;
                break;
            }
        }
        if (existingInstructor) {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Istruttore SET matricolaIstruttore = ?, nome = ?, cognome = ?, pass = ?, specializzazione = ?" +
                    "WHERE matricolaIstruttore = ?");
            pstmt.setString(1, newIstr.getMatricolaIstruttore());
            pstmt.setString(2, newIstr.getNome());
            pstmt.setString(3, newIstr.getCognome());
            pstmt.setString(4, newIstr.getPassword());
            pstmt.setString(5, newIstr.getSpecializzazione());
            pstmt.setString(6, oldIstr.getMatricolaIstruttore());
            pstmt.executeUpdate();
        }
    }

    /**
     * Implementa la funzionalità di recuperare dal DB l'Istruttore associato a quella matricola, senza però recuperarne le schede
     * di allenamento a lui associate
     *
     * @param matricola
     * @return Istruttore
     * @throws SQLException
     */
    public static Istruttore findByMatricolaNoSchede(String matricola) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM istruttore WHERE matricolaIstruttore = '" + matricola + "'";
        ResultSet rs = stmt.executeQuery(query);
        Istruttore istr = null;
        if (rs.next()) {
            istr = new Istruttore();
            istr.setMatricolaIstruttore(rs.getString(1));
            istr.setNome(rs.getString(2));
            istr.setCognome(rs.getString(3));
            istr.setPassword(rs.getString(4));
            istr.setSpecializzazione(rs.getString(5));
            istr.setListaSchedeCreate(new ArrayList<>());
        }
        return istr;
    }

    public static void insertInstructor(Istruttore istr) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Istruttore values (?, ?, ?, ?, ?)");
        pstmt.setString(1, istr.getMatricolaIstruttore());
        pstmt.setString(2, istr.getNome());
        pstmt.setString(3, istr.getCognome());
        pstmt.setString(4, istr.getPassword());
        pstmt.setString(5, istr.getSpecializzazione());
        pstmt.executeUpdate();
    }

    public static void deleteInstructor(Istruttore istr) throws SQLException {
        Connection conn = ConPool.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Istruttore WHERE matricolaIstruttore = ?");
        pstmt.setString(1, istr.getMatricolaIstruttore());
        pstmt.executeUpdate();
    }
}
