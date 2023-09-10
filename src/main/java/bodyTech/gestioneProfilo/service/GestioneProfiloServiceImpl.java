package bodyTech.gestioneProfilo.service;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GestioneProfiloServiceImpl implements GestioneProfiloService{

    @Override
    public List<Utente> visualizzaUtenti(Profilo p) throws SQLException{
        if (p.loggedUserLevel().equals("Istruttore") || p.loggedUserLevel().equals("Amministratore")) {
            List<Utente> listaUtenti = null;
            listaUtenti = UtenteDAO.visualizzaUtenti();
            return listaUtenti;
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public List<Istruttore> visualizzaIstruttori(Profilo p) throws SQLException {
        if (p.loggedUserLevel().equals("Amministratore")){
            return IstruttoreDAO.visualizzaIstruttori();
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public void aggiungiIstruttore(Profilo p, Istruttore istr) throws SQLException {
        if (p.loggedUserLevel().equals("Amministratore")){
            IstruttoreDAO.insertInstructor(istr);
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public Utente modificaUtente(Profilo p, Utente u, Utente u2) throws SQLException, IOException {
        if (p.loggedUserLevel().equals("Amministratore")) {
            if (u2.getCodiceFiscale().length() != 16)
                throw new IOException("Lunghezza Codice Errata");
            UtenteDAO.updateUser(u, u2);
            return u2;
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public Istruttore modificaIstruttore(Profilo p, Istruttore istr, Istruttore istr2) throws SQLException, IOException {
        if (p.loggedUserLevel().equals("Amministratore")) {
            if (istr2.getMatricolaIstruttore().length() != 10)
                throw new IOException("Lunghezza Matricola Errata");
            IstruttoreDAO.updateInstructor(istr, istr2);
            return istr2;
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public void eliminaUtente(Profilo p, Utente u) throws SQLException {
        if (p.loggedUserLevel().equals("Istruttore") || p.loggedUserLevel().equals("Amministratore")){
            UtenteDAO.deleteUser(u);
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    @Override
    public void eliminaIstruttore(Profilo p, Istruttore istr) throws SQLException {
        if (p.loggedUserLevel().equals("Amministratore")){
            IstruttoreDAO.deleteInstructor(istr);
        }
        else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }
}
