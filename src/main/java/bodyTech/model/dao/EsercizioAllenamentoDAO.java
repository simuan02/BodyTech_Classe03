package bodyTech.model.dao;

import bodyTech.model.ConPool;
import bodyTech.model.entity.EsercizioAllenamento;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EsercizioAllenamentoDAO {

    public static List<EsercizioAllenamento> findBySchedaID(int schedaID) throws SQLException {
        Connection conn = ConPool.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM esercizioAllenamento WHERE idScheda = " + schedaID;
        ResultSet rs = stmt.executeQuery(query);
        List<EsercizioAllenamento> esercizi = new ArrayList<>();
        while (rs.next()){
            EsercizioAllenamento es = new EsercizioAllenamento();
            es.setNomeEsercizio(rs.getString(2));
            es.setVolume(rs.getString(3));
            es.setDescrizione(EsercizioDAO.findByName(es.getNomeEsercizio()).getDescrizione());
            esercizi.add(es);
        }
        return esercizi;
    }
}
