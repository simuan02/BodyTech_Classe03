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


    /**
     * Questo metodo verifica se le credenziali inserite in fase di accesso sono corrette o meno.
     * @param profilo Questo parametro contiene le credenziali, ossia identificativo e password, del profilo a cui si tenta di accedere.
     * @return Se viene trovata una corrispondenza con l'oggetto profilo all'interno del db, allora restituisci true. Altrimenti, restituisci false.
     * @throws SQLException
     */
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

    /**
     * Questo metodo assegna a null il profilo passato come parametro
     * @param profilo
     * @return
     */
    @Override
    public boolean logout(Profilo profilo) {
        if (profilo != null){
            profilo = null;
            return true;
        }
        return false;
    }
}
