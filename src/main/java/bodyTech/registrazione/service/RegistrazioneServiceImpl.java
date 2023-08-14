package bodyTech.registrazione.service;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;

import java.sql.SQLException;

public class RegistrazioneServiceImpl implements RegistrazioneService{

    @Override
    public boolean registrazioneUtente(Utente utente) {
        if (utente.getCodiceFiscale().length() != 16)
            return false;
        boolean b = false;
        try {
            b = UtenteDAO.insertUser(utente);
            return b;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
