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

public class SchedaAllenamentoDAO {

    public static List<SchedaAllenamento> findAllByInstructor (String matricola) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM schedaAllenamento WHERE istruttore = '" + matricola + "'";
        ResultSet rs = stmt.executeQuery(query);
        List<SchedaAllenamento> schede = new ArrayList<>();
        while (rs.next()){
            SchedaAllenamento scheda = new SchedaAllenamento();
            scheda.setIdScheda(rs.getInt(1));
            scheda.setDataInizio(rs.getDate(2));
            scheda.setDataCompletamento(rs.getDate(3));
            scheda.setTipo(rs.getString(4));
            scheda.setUtente(UtenteDAO.findByCodiceFiscale(rs.getString(5)));
            scheda.setIstruttore(IstruttoreDAO.findByMatricola(matricola));
            scheda.setListaEsercizi(EsercizioAllenamentoDAO.findBySchedaID(rs.getInt(1)));
            schede.add(scheda);
        }
        return schede;
    }
}
