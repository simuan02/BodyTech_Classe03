package bodyTech.gestioneProfilo.service;

import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProfiloServiceImpl implements ProfiloService{

    @Override
    public boolean modificaDati(Profilo oldProfile, Profilo newProfile) throws SQLException, IOException {
        boolean okUpdate = false;
        if (oldProfile != null){
            switch (oldProfile.loggedUserLevel()){
                case "Utente":
                    Utente oldUser = (Utente)oldProfile;
                    Utente newUser = (Utente)newProfile;
                    UtenteDAO.updateUser(oldUser, newUser);
                    List<Utente> users = UtenteDAO.visualizzaUtenti();
                    for (Utente u : users) {
                        if (newUser.equals(u)) {
                            okUpdate = true;
                            break;
                        }
                    }
                    break;
                case "Istruttore":
                    Istruttore oldInstructor = (Istruttore)oldProfile;
                    Istruttore newInstructor = (Istruttore)newProfile;
                    IstruttoreDAO.updateInstructor(oldInstructor, newInstructor);
                    List<Istruttore> instructors = IstruttoreDAO.visualizzaIstruttori();
                    for (Istruttore i : instructors) {
                        if (newInstructor.equals(i)) {
                            okUpdate = true;
                            break;
                        }
                    }
                    break;
                case "Amministratore":
                    Amministratore oldAdmin = (Amministratore)oldProfile;
                    Amministratore newAdmin = (Amministratore)newProfile;
                    AmministratoreDAO.updateAdmin(oldAdmin, newAdmin);
                    List<Amministratore> admins = AmministratoreDAO.visualizzaAdmin();
                    for (Amministratore a : admins) {
                        if (newAdmin.equals(a)) {
                            okUpdate = true;
                            break;
                        }
                    }
                    break;
            }
        }
        return okUpdate;
    }

    public SchedaAllenamento visualizzaScheda(Profilo p) throws SQLException {
        SchedaAllenamento sa = SchedaAllenamentoDAO.findSchedaByUtente(((Utente)p).getCodiceFiscale());
        return sa;
    }

}
