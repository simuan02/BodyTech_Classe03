package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
