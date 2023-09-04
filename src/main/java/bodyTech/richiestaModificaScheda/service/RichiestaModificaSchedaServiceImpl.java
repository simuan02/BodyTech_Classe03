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
        ProfiloService services = new ProfiloServiceImpl();
        if (services.visualizzaProfilo(u)){
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
    public List<RichiestaModificaScheda> visualizzaModifica(Profilo p) {
        return null;
    }

    @Override
    public void valutaRichistaModifica(RichiestaModificaScheda richiesta, Istruttore istr) {

    }
}
