package bodyTech.richiestaModificaScheda.service;

import bodyTech.gestioneProfilo.service.ProfiloService;
import bodyTech.gestioneProfilo.service.ProfiloServiceImpl;
import bodyTech.model.dao.RichiestaModificaSchedaDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.RichiestaModificaScheda;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;
import java.util.List;

public class RichiestaModificaSchedaServiceImpl implements RichiestaModificaSchedaService{
    @Override
    public boolean richiediModificaScheda(RichiestaModificaScheda richiesta, Utente u) {
        if (u!= null && (richiesta.getMessaggio().length() > 0 && richiesta.getMessaggio().length() <= 250)){
            try {
                RichiestaModificaSchedaDAO.insertNewRequest(richiesta, u.getCodiceFiscale());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public List<RichiestaModificaScheda> visualizzaModifica(Profilo p, Utente user) throws SQLException {
        if (p.loggedUserLevel().equals("Utente") || p.loggedUserLevel().equals("Istruttore")) {
            return RichiestaModificaSchedaDAO.findByUser(user.getCodiceFiscale());
        }
        return null;
    }

    @Override
    public RichiestaModificaScheda visualizzaSingolaRichiesta(Profilo p, int idRichiesta) throws SQLException {
        if (p.loggedUserLevel().equals("Istruttore")){
            return RichiestaModificaSchedaDAO.findById(idRichiesta);
        }
        return null;
    }

    @Override
    public void valutaRichistaModifica(RichiestaModificaScheda richiesta, Istruttore istr) throws SQLException {
        if (istr != null) {
            RichiestaModificaSchedaDAO.cambiaEsitoRichiesta(richiesta);
        }
        else
            throw new RuntimeException("Utente Non Autorizzato!");
    }
}
