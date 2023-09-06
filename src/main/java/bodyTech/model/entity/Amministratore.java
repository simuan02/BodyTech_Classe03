package bodyTech.model.entity;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Contiene i dati relativi ad un amministratore del sistema.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amministratore)) return false;
        Amministratore admin = (Amministratore) o;
        return codice == admin.codice && super.equals(admin);
    }

    private int codice;
}
