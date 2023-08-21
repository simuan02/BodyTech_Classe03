package bodyTech.gestioneProfilo.service;

import bodyTech.model.dao.AmministratoreDAO;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;

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
                    if (users.contains(newUser))
                        okUpdate = true;
                    break;
                case "Istruttore":
                    Istruttore oldInstructor = (Istruttore)oldProfile;
                    Istruttore newInstructor = (Istruttore)newProfile;
                    IstruttoreDAO.updateInstructor(oldInstructor, newInstructor);
                    List<Istruttore> instructors = IstruttoreDAO.visualizzaIstruttori();
                    if (instructors.contains(newInstructor))
                        okUpdate = true;
                    break;
                case "Amministratore":
                    Amministratore oldAdmin = (Amministratore)oldProfile;
                    Amministratore newAdmin = (Amministratore)newProfile;
                    AmministratoreDAO.updateAdmin(oldAdmin, newAdmin);
                    List<Amministratore> admins = AmministratoreDAO.visualizzaAdmin();
                    if (admins.contains(newAdmin))
                        okUpdate = true;
                    break;
            }
        }
        return okUpdate;
    }

}
