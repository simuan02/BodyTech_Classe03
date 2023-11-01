package services;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

public class ModificaDatiIstruttoreTest {

    public Istruttore oldIstr, newIstr;

    @BeforeEach
    public void init() throws SQLException {
        oldIstr = new Istruttore();
        oldIstr.setMatricolaIstruttore("1030650127");
        oldIstr.setNome("Fiery");
        oldIstr.setCognome("Ebosele");
        oldIstr.setPassword("Udinese066");
        oldIstr.setSpecializzazione("Bodybuilding");
        newIstr = new Istruttore();
        newIstr.setMatricolaIstruttore(oldIstr.getMatricolaIstruttore());
        newIstr.setNome(oldIstr.getNome());
        newIstr.setCognome(oldIstr.getCognome());
        newIstr.setPassword(oldIstr.getPassword());
        newIstr.setSpecializzazione(oldIstr.getSpecializzazione());
        IstruttoreDAO.insertInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di newIstr.nome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_1() throws SQLException{
        newIstr.setNome("FieryEboseleEnzoEbosseKingsleyEhizibueeee"); //lunghezza nome = 41
        boolean longName = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Nome Errata"))
                longName = true;
        }
        Assertions.assertTrue(longName, "Modifica Effettuata Correttamente oppure altro errore rilevato " +
                "nonostante il nome fosse di lunghezza maggiore di 40 caratteri");
        IstruttoreDAO.deleteInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di newIstr.cognome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_2() throws SQLException{
        newIstr.setCognome("EboseleFieryEnzoEbosseKingsleyEhizibueeee"); //lunghezza cognome = 41
        boolean longSurname = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Cognome Errata"))
                longSurname = true;
        }
        Assertions.assertTrue(longSurname, "Modifica Effettuata Correttamente oppure altro errore rilevato " +
                "nonostante il cognome fosse di lunghezza maggiore di 40 caratteri");
        IstruttoreDAO.deleteInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di newIstr.matricolaIstruttore di lunghezza maggiore di 10 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_3() throws SQLException{
        newIstr.setMatricolaIstruttore("103065012700"); //lunghezza matricola = 12
        boolean longMat = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Matricola Errata"))
                longMat = true;
        }
        Assertions.assertTrue(longMat, "Modifica Effettuata Correttamente oppure altro errore rilevato " +
                "nonostante la matricola fosse di lunghezza maggiore di 10 caratteri");
        IstruttoreDAO.deleteInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di newIstr.matricolaIstruttore di lunghezza minore di 10 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_4() throws SQLException{
        newIstr.setMatricolaIstruttore("10306501"); //lunghezza matricola = 8
        boolean shortMat = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Matricola Errata"))
                shortMat = true;
        }
        Assertions.assertTrue(shortMat, "Modifica Effettuata Correttamente oppure altro errore rilevato " +
                "nonostante la matricola fosse di lunghezza minore di 10 caratteri");
        IstruttoreDAO.deleteInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di newIstr.specializzazione di lunghezza maggiore di 30 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_5() throws SQLException{
        newIstr.setSpecializzazione("BodybuildingPowerliftingCrossfit"); //lunghezza spec = 32
        boolean longSpec = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Specializzazione Errata"))
                longSpec = true;
        }
        Assertions.assertTrue(longSpec, "Modifica Effettuata Correttamente oppure altro errore rilevato " +
                "nonostante la specializzazione fosse di lunghezza maggiore di 30 caratteri");
        IstruttoreDAO.deleteInstructor(oldIstr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaIstruttore(p, oldUser, newUser)
     * in caso di:
     * - newIstr.nome di lunghezza minore o uguale di 40 caratteri;
     * - newIstr.cognome di lunghezza minore o uguale di 40 caratteri;
     * - newIstr.matricolaIstruttore di lunghezza di 10 caratteri;
     * - newIstr.specializzazione di lunghezza minore o uguale di 30 caratteri;
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_6() throws SQLException{
        newIstr.setNome("Fiery");
        newIstr.setCognome("Ebosele");
        newIstr.setMatricolaIstruttore("1030650127");
        newIstr.setSpecializzazione("Bodybuilding");
        boolean exception = false;
        String erroreTrovato = "";
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaIstruttore(new Amministratore(), oldIstr, newIstr);
        } catch (IOException e) {
            exception = true;
            erroreTrovato = e.getMessage();
        }
        Assertions.assertFalse(exception, "Modifica Non Effettuata. E' stato rilevato l'errore:" + erroreTrovato);
        IstruttoreDAO.deleteInstructor(newIstr);
    }

}
