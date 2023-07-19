package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public static Utente setUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setCodiceFiscale(rs.getString(1));
        u.setNome(rs.getString(2));
        u.setCognome(rs.getString(3));
        u.setPassword(rs.getString(4));
        List<RichiestaModificaScheda> rms = RichiestaModificaSchedaDAO.findByUser(u.getCodiceFiscale());
        u.setListeRichiesteModifica(rms);
        return u;
    }

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
}
