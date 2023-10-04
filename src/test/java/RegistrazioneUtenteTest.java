import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import bodyTech.registrazione.service.RegistrazioneService;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
public class RegistrazioneUtenteTest {

    private Utente user;

    @BeforeEach
    public final void init(){
        user = new Utente();
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di registrazione di un utente con il nome di lunghezza
     * maggiore di 40
     */
    @Test
    public void testRegistrazione_1_1 () {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSBCD09D02A301F";
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFGHIJ";
        user.setPassword(password);
        String nome = "SimoneABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHI"; //lunghezza nome = 41
        user.setNome(nome);
        String cognome = "Esposito";
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertTrue("Il servizio RegistrazioneUtente ha un comportamento inatteso per quanto riguarda il parametro nome",
                problemiRegistrazione.contains("Lunghezza Nome Errata"));
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un cognome di
     * lunghezza maggiore di 40
     */
    @Test
    public void testRegistrazione_1_2 () {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSBCD09D02A301F";
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFGHIJ";
        user.setPassword(password);
        String nome = "Simone";
        user.setNome(nome);
        String cognome = "EspositoABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG"; //lunghezza cognome = 41
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertTrue("Il servizio RegistrazioneUtente ha un comportamento inatteso per quanto riguarda il parametro cognome",
                problemiRegistrazione.contains("Lunghezza Cognome Errata"));
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un codice fiscale di
     * lunghezza minore di 16
     */
    @Test
    public void testRegistrazione_1_3 () {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSBCD09D02A301"; //lunghezza codice fiscale = 15
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFGHIJ";
        user.setPassword(password);
        String nome = "Simone";
        user.setNome(nome);
        String cognome = "Esposito";
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertTrue("Il servizio RegistrazioneUtente ha un comportamento inatteso per quanto riguarda il parametro codice fiscale",
                problemiRegistrazione.contains("Lunghezza CF Errata"));
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con un codice fiscale di
     * lunghezza maggiore di 16
     */
    @Test
    public void testRegistrazione_1_4 () {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSBCD09D02A301X1"; //lunghezza codice fiscale = 17
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFGHIJ";
        user.setPassword(password);
        String nome = "Simone";
        user.setNome(nome);
        String cognome = "Esposito";
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertTrue("Il servizio RegistrazioneUtente ha un comportamento inatteso per quanto riguarda il parametro codice fiscale",
                problemiRegistrazione.contains("Lunghezza CF Errata"));
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente con una password di
     * lunghezza minore di 8 caratteri.
     */
    @Test
    public void testRegistrazione_1_5 () {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSBCD09D02A301X";
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFG"; //lunghezza password = 7
        user.setPassword(password);
        String nome = "Simone";
        user.setNome(nome);
        String cognome = "Esposito";
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertTrue("Il servizio RegistrazioneUtente ha un comportamento inatteso per quanto riguarda il parametro password",
                problemiRegistrazione.contains("Lunghezza PW Errata"));
    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di registrazione di un utente che soddisfa le seguenti condizioni:
     * - Codice Fiscale di 16 caratteri;
     * - Nome di 6 caratteri;
     * - Cognome di 8 caratteri;
     * - Password di 8 caratteri;
     */
    @Test
    public void testRegistrazione_1_6 () throws SQLException {
        RegistrazioneService services = new RegistrazioneServiceImpl();
        String codiceFiscale = "SPSSMN02B06L845Y"; //lunghezza CF = 16
        user.setCodiceFiscale(codiceFiscale);
        String password = "ABCDEFGH"; //lunghezza password = 8
        user.setPassword(password);
        String nome = "Simone"; //lunghezza nome = 6
        user.setNome(nome);
        String cognome = "Esposito"; //lunghezza cognome = 8
        user.setCognome(cognome);
        List<String> problemiRegistrazione = services.registrazioneUtente(user, password);
        assertEquals("Il servizio RegistrazioneUtente ha riscontrato un problema inatteso", problemiRegistrazione.size(), 0);
        UtenteDAO.deleteUser(user);
    }
}
