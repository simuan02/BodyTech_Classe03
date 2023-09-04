package bodyTech.login.service;

import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService{


    @Override
    public boolean login(Profilo profilo) throws SQLException {
        Profilo profilo2 = null;
        switch(profilo.loggedUserLevel()){
            case "Utente": {
                Utente u = (Utente) profilo;
                profilo2 = UtenteDAO.findByCodiceFiscale(u.getCodiceFiscale());
                break;
            }
            case "Istruttore": {
                Istruttore i = (Istruttore) profilo;
                profilo2 = IstruttoreDAO.findByMatricola(i.getMatricolaIstruttore());
                break;
            }
            case "Amministratore": {
                Amministratore a = (Amministratore) profilo;
                profilo2 = AmministratoreDAO.findByCodice(a.getCodice());
                break;
            }
        }
        if (profilo2 == null)
            return false;
        return profilo.getPassword().equals(profilo2.getPassword());
    }

    @Override
    public boolean logout(Profilo profilo) {
        if (profilo != null){
            profilo = null;
            return true;
        }
        return false;
    }
}
