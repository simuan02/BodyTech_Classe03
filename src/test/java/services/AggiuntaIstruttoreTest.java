package services;

import bodyTech.gestioneProfilo.service.GestioneProfiloService;
import bodyTech.gestioneProfilo.service.GestioneProfiloServiceImpl;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.registrazione.service.RegistrazioneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AggiuntaIstruttoreTest {

    private Istruttore istr;

    @BeforeEach
    public final void init() {
        istr = new Istruttore();
    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * la matricola di lunghezza maggiore di 10
     */
    @Test
    public void testAggiunta_1_1() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "103065012700"; // lunghezza matricola = 12
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().equals("Lunghezza Matricola Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro matricola");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * la matricola di lunghezza minore di 10
     */
    @Test
    public void testAggiunta_1_2() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "10306501"; // lunghezza matricola = 8
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().equals("Lunghezza Matricola Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro matricola");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * nome di lunghezza maggiore di 40
     */
    @Test
    public void testAggiunta_1_3() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "FieryEboseleEnzoEbosseKingsleyEhizibueeee"; // lunghezza nome = 41
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().equals("Lunghezza Nome Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro nome");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * cognome di lunghezza maggiore di 40
     */
    @Test
    public void testAggiunta_1_4() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "EboseleFieryEnzoEbosseKingsleyEhizibueeee"; // lunghezza cognome = 41
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Lunghezza Cognome Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro cognome");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * password di lunghezza maggiore di 32
     */
    @Test
    public void testAggiunta_1_5() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066EboseleEnzoEbosseKingsley"; // lunghezza pw = 35
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Lunghezza Password Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro password");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * password di lunghezza minore di 8
     */
    @Test
    public void testAggiunta_1_6() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese"; // lunghezza pw = 7
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Lunghezza Password Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro password");
        }

    }

    /**
     * Questo caso di test verifica come si comporta il sistema in caso di inserimento di un istruttore con
     * specializzazione di lunghezza maggiore di 30
     */
    @Test
    public void testAggiunta_1_7() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "BodybuildingPowerliftingCrossfit"; // lunghezza spec = 32
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
        }catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Lunghezza Specializzazione Errata"),
                    "Il servizio GestioneProfilo ha un comportamento inatteso per quanto riguarda il parametro specializzazione");
        }

    }

    /**
     * Questo caso di test verifica il comportamento del sistema in caso di inserimento di un istruttore che soddisfa le seguenti condizioni:
     * - Matricola di 10 caratteri;
     * - Nome di <= 40 caratteri;
     * - Cognome di <= 40 caratteri;
     * - Password di <= 32  >= 8 caratteri;
     * - Specializzazione di <= 30 caratteri;
     */
    @Test
    public void testAggiunta_1_8() {
        GestioneProfiloService services = new GestioneProfiloServiceImpl();
        String matricola = "1030650127";
        istr.setMatricolaIstruttore(matricola);
        String nome = "Fiery";
        istr.setNome(nome);
        String cognome = "Ebosele";
        istr.setCognome(cognome);
        String password = "Udinese066";
        istr.setPassword(password);
        String specializzazione = "Bodybuilding";
        istr.setSpecializzazione(specializzazione);
        Amministratore admin = new Amministratore();
        try {
            services.aggiungiIstruttore(admin, istr, password);
            services.eliminaIstruttore(admin, istr);
        }catch (Exception e) {
            Assertions.assertFalse(e.getMessage().contains("Lunghezza"),
                    "Il servizio GestioneProfilo ha riscontrato un problema inaspettato");
        }
    }
}
