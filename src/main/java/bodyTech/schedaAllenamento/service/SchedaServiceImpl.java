package bodyTech.schedaAllenamento.service;

import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.SQLException;
import java.util.List;

public class SchedaServiceImpl implements SchedaService{
    @Override
    public List<SchedaAllenamento> visualizzaSchede(Profilo p) throws SQLException {
        if (p.loggedUserLevel().equalsIgnoreCase("Istruttore")){
            List<SchedaAllenamento> listaSchede = SchedaAllenamentoDAO.findAll();
            return listaSchede;
        }
        throw new RuntimeException("Operazione non consentita");
    }

    @Override
    public void eliminaEsercizio(int schedaID, String nomeEsercizio) throws SQLException {
        SchedaAllenamentoDAO.deleteExercise(schedaID, nomeEsercizio);
    }
}
