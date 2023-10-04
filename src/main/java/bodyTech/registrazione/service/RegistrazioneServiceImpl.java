package bodyTech.registrazione.service;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrazioneServiceImpl implements RegistrazioneService{

    @Override
    public List<String> registrazioneUtente(Utente utente, String password) {
        boolean wrongParameters = false;
        List<String> problemiRegistrazione = new ArrayList<>();
        if (password.length() < 8 || password.length() > 32){
            wrongParameters = true;
            problemiRegistrazione.add("Lunghezza PW Errata");
        }
        if (utente.getNome().length() > 40) {
            wrongParameters = true;
            problemiRegistrazione.add("Lunghezza Nome Errata");
        }
        if (utente.getCognome().length() > 40) {
            wrongParameters = true;
            problemiRegistrazione.add("Lunghezza Cognome Errata");
        }
        if (utente.getCodiceFiscale().length() != 16){
            wrongParameters = true;
            problemiRegistrazione.add("Lunghezza CF Errata");
        }
        try {
            for (Utente user : UtenteDAO.visualizzaUtenti()){
                if (user.getCodiceFiscale().equalsIgnoreCase(utente.getCodiceFiscale())) {
                    problemiRegistrazione.add("Codice Fiscale Registrato");
                    wrongParameters = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore Server!");
        }
        try {
            if (!wrongParameters) {
                boolean b = UtenteDAO.insertUser(utente);
                if (!b)
                    problemiRegistrazione.add("Errore Server");
            }
            return problemiRegistrazione;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
