package bodyTech.model.entity;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.List;

public class Amministratore {

    public List<Utente> visualizzaUtenti() throws SQLException {
        return UtenteDAO.visualizzaUtenti();
    }

    public List<Istruttore> visualizzaIstruttori(){
        return null;
        //return IstruttoreDAO.visualizzaIstruttori();
    }

    private int codice;
}
