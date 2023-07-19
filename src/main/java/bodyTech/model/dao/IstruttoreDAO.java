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

public class IstruttoreDAO {

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
