package services;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreazioneSchedaAllenamentoTest {

    private SchedaAllenamento scheda = new SchedaAllenamento();
    private Utente user = new Utente();

    @AfterEach
    public void refreshDB() throws SQLException {
        SchedaAllenamento scheda2 = SchedaAllenamentoDAO.findSchedaByUtente(user.getCodiceFiscale());
        if (scheda2 != null)
            SchedaAllenamentoDAO.deleteScheda(scheda2.getIdScheda());
        UtenteDAO.deleteUser(user);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        user.setNome("Simone");
        user.setCognome("Esposito");
        user.setCodiceFiscale("SPSSMN01B04L845A");
        user.setPassword("ABCDEFGHIJ");
        if (!UtenteDAO.visualizzaUtenti().contains(user)){
            UtenteDAO.insertUser(user);
        }
        scheda.setUtente(user);
        Date dataInizio = new Date();
        scheda.setDataInizio(dataInizio);
        Date dataCompletamento = new Date();
        dataCompletamento.setMonth(11);
        scheda.setDataCompletamento(dataCompletamento);
        scheda.setTipo("Powerbuilding");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il codice fiscale
     * dell'utente fosse inesistente
     */
    @Test
    public void test_CreazioneScheda_3_1_1() throws SQLException {
        user.setCodiceFiscale("CODICEERRATO");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            inserimentoEffettuato = false;
        }
        assertFalse("Nessuna eccezione lanciata. Inserimento erroneamente effettuato", inserimentoEffettuato);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il tipo
     * di scheda fosse di lunghezza maggiore di 30
     */
    @Test
    public void test_CreazioneScheda_3_1_2() throws SQLException {
        scheda.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            inserimentoEffettuato = false;
        }
        assertFalse("Nessuna eccezione lanciata. Inserimento erroneamente effettuato", inserimentoEffettuato);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il tipo
     * di scheda fosse null
     */
    @Test
    public void test_CreazioneScheda_3_1_3() throws SQLException {
        scheda.setTipo(null);
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            inserimentoEffettuato = false;
        }
        assertFalse("Nessuna eccezione lanciata. Inserimento erroneamente effettuato", inserimentoEffettuato);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui la data
     * di completamento sia precedente alla data di inizio
     */
    @Test
    public void test_CreazioneScheda_3_1_4() throws SQLException {
        scheda.setDataInizio(new Date(123, Calendar.OCTOBER, 25));
        scheda.setDataCompletamento(new Date(123, Calendar.OCTOBER, 24));
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            inserimentoEffettuato = false;
        }
        assertFalse("Nessuna eccezione lanciata. Inserimento erroneamente effettuato", inserimentoEffettuato);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il tipo
     * di scheda fosse di lunghezza compresa tra 1 e 30 caratteri, la data di completamento fosse successiva alla
     * data d'inizio e il codice fiscale fosse esistente.
     */
    @Test
    public void test_CreazioneScheda_3_1_5() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setTipo("Powerbuilding");
        Date dataInizio = new Date(123, Calendar.OCTOBER, 25);
        Date dataCompletamento = new Date (123, Calendar.DECEMBER, 25);
        scheda.setDataInizio(dataInizio);
        scheda.setDataCompletamento(dataCompletamento);
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            inserimentoEffettuato = false;
        }
        assertTrue("Eccezione lanciata. Inserimento NON effettuato", inserimentoEffettuato);
        SchedaAllenamentoDAO.deleteScheda(SchedaAllenamentoDAO.findSchedaByUtente(user.getCodiceFiscale()).getIdScheda());
    }
}
