package bodyTech.model.entity;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.List;

public class Amministratore extends Profilo{

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public List<Utente> visualizzaUtenti() throws SQLException {
        return UtenteDAO.visualizzaUtenti();
    }

    public List<Istruttore> visualizzaIstruttori() throws SQLException{
        return IstruttoreDAO.visualizzaIstruttori();
    }

    private int codice;
}
