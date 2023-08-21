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
        return istr;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB una lista di tutti gli Istruttori presenti
     * @return lista degli Istruttori
     * @throws SQLException
     */
    public static List<Istruttore> visualizzaIstruttori() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM istruttore";
        ResultSet rs = stmt.executeQuery(query);
        List<Istruttore> istruttori = new ArrayList<>();
        while(rs.next()){
            Istruttore istr = createIstruttore(rs);
            istruttori.add(istr);
        }
        return istruttori;
    }

    /**
     * Implementa la funzionalità di recuperare dal DB l'Istruttore associato a quella matricola
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
        while(rs.next()){
            istr = createIstruttore(rs);
        }
        return istr;
    }

    public static void updateInstructor (Istruttore oldIstr, Istruttore newIstr) throws SQLException {
        Connection conn = ConPool.getConnection();
        List<Istruttore> instructors = IstruttoreDAO.visualizzaIstruttori();
        if (instructors.contains(oldIstr)){
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
}
