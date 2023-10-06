package services;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

public class ModificaDatiUtenteTest {

    public Utente oldUser, newUser;

    @BeforeEach
    public void init() throws SQLException {
        oldUser = new Utente();
        oldUser.setNome("Simone");
        oldUser.setCognome("Esposito");
        oldUser.setCodiceFiscale("SPSBCD09D02A301X");
        oldUser.setPassword("ABCDEFGHIJ");
        newUser = new Utente();
        newUser.setNome(oldUser.getNome());
        newUser.setCognome(oldUser.getCognome());
        newUser.setCodiceFiscale(oldUser.getCodiceFiscale());
        newUser.setPassword(oldUser.getPassword());
        UtenteDAO.insertUser(oldUser);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.codiceFiscale di lunghezza minore di 16 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_1() throws SQLException{
        newUser.setCodiceFiscale("SPSBCD09D02A301"); //lunghezza codice fiscale = 15
        boolean shortCF = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(new Amministratore(), oldUser, newUser);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Codice Errata"))
                shortCF = true;
        }
        assertTrue("Modifica Effettuata Correttamente oppure altro errore rilevato nonostante il codice fiscale fosse di lunghezza " +
                "minore di 16 caratteri", shortCF);
        UtenteDAO.deleteUser(oldUser);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.codiceFiscale di lunghezza maggiore di 16 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_2() throws SQLException{
        newUser.setCodiceFiscale("SPSBCD09D02A301FX"); //lunghezza codice fiscale = 17
        boolean longCF = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(new Amministratore(), oldUser, newUser);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Codice Errata"))
                longCF = true;
        }
        assertTrue("Modifica Effettuata Correttamente oppure altro errore rilevato nonostante il codice fiscale fosse di lunghezza " +
                "maggiore di 16 caratteri", longCF);
        UtenteDAO.deleteUser(oldUser);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.nome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_3() throws SQLException{
        newUser.setNome("SimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHISimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHI"); //lunghezza nome = 41
        boolean longName = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(new Amministratore(), oldUser, newUser);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Nome Errata"))
                longName = true;
        }
        assertTrue("Modifica Effettuata Correttamente oppure altro errore rilevato nonostante il nome fosse di lunghezza " +
                "maggiore di 40 caratteri", longName);
        UtenteDAO.deleteUser(oldUser);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di newUser.cognome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiUtente_2_4() throws SQLException{
        newUser.setCognome("EspositoABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHISimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG"); //lunghezza cognome = 41
        boolean longLastName = false;
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(new Amministratore(), oldUser, newUser);
        } catch (IOException e) {
            if (e.getMessage().equals("Lunghezza Cognome Errata"))
                longLastName = true;
        }
        assertTrue("Modifica Effettuata Correttamente oppure altro errore rilevato nonostante il cognome fosse di lunghezza " +
                "maggiore di 40 caratteri", longLastName);
        UtenteDAO.deleteUser(oldUser);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloService.modificaUtente(p, oldUser, newUser)
     * in caso di:
     * - newUser.cognome di lunghezza minore o uguale a 40 caratteri;
     * - newUser.nome di lunghezza minore o uguale a 40 caratteri;
     * - newUser.codiceFiscale di lunghezza uguale a 16 caratteri;
     */
    @Test
    public void Test_ModificaDatiUtente_2_5() throws SQLException{
        newUser.setNome("Simon");
        newUser.setCognome("Espositx");
        newUser.setCodiceFiscale("SPSBCD09D02A301F");
        boolean eccezioneLanciata = false;
        String erroreTrovato = "";
        try{
            GestioneProfiloService services = new GestioneProfiloServiceImpl();
            services.modificaUtente(new Amministratore(), oldUser, newUser);
        } catch (IOException e) {
            eccezioneLanciata = true;
            erroreTrovato = e.getMessage();
        }
        assertFalse("Modifica Non Effettuata. E' stato rilevato l'errore: " + erroreTrovato, eccezioneLanciata);
        UtenteDAO.deleteUser(newUser);
    }
}
