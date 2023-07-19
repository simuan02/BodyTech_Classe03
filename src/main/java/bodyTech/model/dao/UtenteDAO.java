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

    public static List<Utente> visualizzaUtenti() throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM utente";
        ResultSet rs = stmt.executeQuery(query);
        List<Utente> utenti = new ArrayList<Utente>();
        while (rs.next()){
            Utente u1 = new Utente();
            u1.setCodiceFiscale(rs.getString(1));
            u1.setNome(rs.getString(2));
            u1.setCognome(rs.getString(3));
            u1.setPassword(rs.getString(4));
            List<RichiestaModificaScheda> rms = RichiestaModificaSchedaDAO.findByUser(u1.getCodiceFiscale());
            u1.setListeRichiesteModifica(rms);
        }
        return utenti;
    }
}
