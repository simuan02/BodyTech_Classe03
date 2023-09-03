package bodyTech.gestioneProfilo.service;

import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;

import java.sql.SQLException;
import java.util.List;

public class ProfiloServiceImpl implements ProfiloService{
    @Override
    public boolean visualizzaProfilo(Profilo p) throws SQLException {
        if (p != null){
            String profileLevel = p.loggedUserLevel();
            boolean profileFound = false;
            switch (profileLevel){
                case "Utente":{
                    List<Utente> users = UtenteDAO.visualizzaUtenti();
                    if (users.contains(p))
                        profileFound = true;
                    break;
                }
                case "Istruttore":{
                    List<Istruttore> instructors = IstruttoreDAO.visualizzaIstruttori();
                    if (instructors.contains(p))
                        profileFound = true;
                    break;
                }
                case "Amministratore":{
                    List<Amministratore> admins = AmministratoreDAO.visualizzaAdmin();
                    if (admins.contains(p))
                        profileFound = true;
                    break;
                }
            }
            return profileFound;
        }
        else
            throw new RuntimeException("Operazione non consentita");
    }

    @Override
    public boolean modificaDati(Profilo oldProfile, Profilo newProfile) throws SQLException {
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
        if (!p.loggedUserLevel().equals("Utente"))
            throw new RuntimeException("Accesso non autorizzato al metodo");
        SchedaAllenamento sa = SchedaAllenamentoDAO.findSchedaByUtente(((Utente)p).getCodiceFiscale());
        return sa;
    }

}
