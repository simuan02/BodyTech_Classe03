package services;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreazioneSchedaAllenamentoTest {

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il codice fiscale
     * dell'utente fosse inesistente
     */
    @Test
    public void test_CreazioneScheda_3_1_1() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setTipo("Powerbuilding");
        scheda.setDataInizio(new Date(2023, 9, 25));
        scheda.setDataCompletamento(new Date(2023, 11, 25));
        Utente user = new Utente();
        user.setCodiceFiscale("CODICEERRATO");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (SQLException e) {
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
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        scheda.setDataInizio(new Date(2023, 9, 25));
        scheda.setDataCompletamento(new Date(2023, 11, 25));
        Utente user = UtenteDAO.findByCodiceFiscale("SPSSMN01B04L845A");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
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
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setTipo(null);
        scheda.setDataInizio(new Date(2023, 9, 25));
        scheda.setDataCompletamento(new Date(2023, 11, 25));
        Utente user = UtenteDAO.findByCodiceFiscale("SPSSMN01B04L845A");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            inserimentoEffettuato = false;
        }
        assertFalse("Nessuna eccezione lanciata. Inserimento erroneamente effettuato", inserimentoEffettuato);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo SchedaService.aggiungiSchedaUtente, nel caso in cui il tipo
     * di scheda fosse di lunghezza compresa tra 1 e 30 caratteri e il codice fiscale fosse esistente.
     */
    @Test
    public void test_CreazioneScheda_3_1_4() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setTipo("Powerbuilding");
        scheda.setDataInizio(new Date(2023, 9, 25));
        scheda.setDataCompletamento(new Date(2023, 11, 25));
        Utente user = UtenteDAO.findByCodiceFiscale("SPSSMN01B04L845A");
        SchedaService services = new SchedaServiceImpl();
        Istruttore istr = IstruttoreDAO.visualizzaIstruttori().get(0);
        scheda.setIstruttore(istr);
        boolean inserimentoEffettuato;
        try {
            services.aggiungiSchedaUtente(istr, scheda, user);
            inserimentoEffettuato = true;
        } catch (RuntimeException e) {
            inserimentoEffettuato = false;
        }
        assertTrue("Eccezione lanciata. Inserimento NON effettuato", inserimentoEffettuato);
        SchedaAllenamentoDAO.deleteScheda(scheda.getIdScheda());
    }
}
