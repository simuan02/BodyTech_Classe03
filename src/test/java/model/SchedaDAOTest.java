package model;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SchedaDAOTest {

    /**
     * Questo test verifica il comportamento del metodo findSchedaByUtente(String codiceFiscale), nel caso in cui
     * codiceFiscale sia inesistente
     */
    @Test
    public void findSchedaByUtente_TEST_CF_Inesistente() throws SQLException {
        Utente u = new Utente();
        u.setCodiceFiscale("SPSMNS12L20A201C");
        UtenteDAO.deleteUser(u); //viene invocato deleteUser per eliminare l'utente di cui trovare la scheda
        assertTrue("Scheda Trovata nonostante l'utente non esista",
                SchedaAllenamentoDAO.findSchedaByUtente("SPSMNS12L20A201C") == null);
    }



    public void setUserSchedaANDInstructor_TESTING(Utente u, SchedaAllenamento s, Istruttore i) throws SQLException {
        u.setNome("Simone");
        u.setCognome("Esposito");
        u.setCodiceFiscale("SPSSMN01B04L845A");
        u.setPassword("ABCDEFGHIJ");
        i.setNome("A");
        i.setCognome("B");
        i.setMatricolaIstruttore("ALCOEPROW1");
        i.setPassword("ABCDEFGHIJ");
        s.setUtente(u);
        Date dataInizio = new Date();
        s.setDataInizio(dataInizio);
        Date dataCompletamento = new Date();
        dataCompletamento.setMonth(11);
        s.setDataCompletamento(dataCompletamento);
        s.setTipo("Powerbuilding");
        s.setIstruttore(i);
        try{
            IstruttoreDAO.insertInstructor(i);
        }
        catch(SQLException e){

        }
    }
    /**
     * Questo test verifica il comportamento del metodo findSchedaByUtente(String codiceFiscale), nel caso in cui
     * CodiceFiscale esista
     */
    @Test
    public void findSchedaByUtente_TEST_CF_Esistente() throws SQLException{
        SchedaAllenamento scheda = new SchedaAllenamento();
        Utente user = new Utente();
        Istruttore istr = new Istruttore();
        setUserSchedaANDInstructor_TESTING(user, scheda, istr);
        UtenteDAO.insertUser(user);
        SchedaAllenamentoDAO.insertScheda(scheda);
        assertTrue("Scheda di allenamento non trovata",
                SchedaAllenamentoDAO.findSchedaByUtente("SPSSMN01B04L845A") != null);
        UtenteDAO.deleteUser(user);
        IstruttoreDAO.deleteInstructor(istr);
    }

    /**
     * Questo test verifica il comportamento del metodo deleteScheda(int idScheda) nel caso in cui idScheda non sia
     * presente nel DB
     */
    @Test
    public void deleteScheda_TESTING_IDScheda_ASSENTE() throws SQLException {
        //codiceFiscale di lunghezza minore di 16, quindi è impossibile che esista un Utente che lo possiede
        SchedaAllenamento sa = SchedaAllenamentoDAO.findSchedaByUtente("CODICEERRATO");
        boolean eliminazioneRiuscita;
        try{
            SchedaAllenamentoDAO.deleteScheda(sa.getIdScheda());
            eliminazioneRiuscita = true;
        } catch(Exception e){
            eliminazioneRiuscita = false;
        }
        assertFalse("Il metodo non ha lanciato alcuna eccezione", eliminazioneRiuscita);
    }

    /**
     * Questo test verifica il comportamento del metodo deleteScheda(int idScheda) nel caso in cui idScheda sia
     * presente nel DB
     */
    @Test
    public void deleteScheda_TESTING_IDScheda_PRESENTE() throws SQLException {
        SchedaAllenamento scheda = SchedaAllenamentoDAO.findAll().get(0);
        boolean eliminazioneRiuscita;
        try{
            SchedaAllenamentoDAO.deleteScheda(scheda.getIdScheda());
            eliminazioneRiuscita = true;
        } catch(Exception e){
            eliminazioneRiuscita = false;
        }
        assertTrue("Il metodo ha lanciato un'eccezione", eliminazioneRiuscita);
        SchedaAllenamentoDAO.insertScheda(scheda);
    }

    /**
     * Questo test verifica il comportamento del metodo findById(int idScheda), nel caso in cui non esista alcuna scheda con
     * quell'identificativo
     */
    @Test
    public void findById_TESTING_IdScheda_ASSENTE() throws SQLException {
        int idScheda = 201920;
        SchedaAllenamento sa = SchedaAllenamentoDAO.findByID(idScheda);
        assertTrue("E' stata restituita una scheda con id diverso", sa == null);
    }

    /**
     * Questo test verifica il comportamento del metodo findById(int idScheda), nel caso in cui esista una scheda con
     * quell'identificativo
     */
    @Test
    public void findById_TESTING_IdScheda_PRESENTE() throws SQLException{
        int idScheda = SchedaAllenamentoDAO.findAll().get(0).getIdScheda();
        assertTrue("Non è stata trovata la scheda con idScheda = " + idScheda,
                idScheda == SchedaAllenamentoDAO.findByID(idScheda).getIdScheda());
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui esista la scheda passata come
     * parametro abbia un codice fiscale inesistente
     */
    @Test
    public void insertScheda_TESTING_CF_Inesistente() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        Utente user = new Utente();
        Istruttore istr = new Istruttore();
        setUserSchedaANDInstructor_TESTING(user, scheda, istr);
        String codiceFiscale = "CODICEERRATO";
        user.setCodiceFiscale(codiceFiscale);
        scheda.setUtente(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (SQLException e){
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui esista la scheda passata come
     * parametro abbia un tipo di lunghezza maggiore di 30 caratteri
     */
    @Test
    public void insertScheda_TESTING_Tipo_Lunghezza_Maggiore_30() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        Utente user = new Utente();
        Istruttore istr = new Istruttore();
        setUserSchedaANDInstructor_TESTING(user, scheda, istr);
        scheda.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        UtenteDAO.insertUser(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (RuntimeException e){
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui esista la scheda passata come
     * parametro abbia un tipo null
     */
    @Test
    public void insertScheda_TESTING_Tipo_NULL() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        Utente user = new Utente();
        Istruttore istr = new Istruttore();
        setUserSchedaANDInstructor_TESTING(user, scheda, istr);
        scheda.setTipo("");
        UtenteDAO.insertUser(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (RuntimeException e){
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui esista la scheda passata come
     * parametro abbia:
     * - Codice Fiscale Esistente;
     * - Tipo di lunghezza compresa tra 1 e 30 caratteri.
     */
    @Test
    public void insertScheda_TESTING_METHOD_OK() throws SQLException {
        SchedaAllenamento scheda = new SchedaAllenamento();
        Utente user = new Utente();
        Istruttore istr = new Istruttore();
        setUserSchedaANDInstructor_TESTING(user, scheda, istr);
        UtenteDAO.insertUser(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (RuntimeException e){
            inserimentoRiuscito = false;
        }
        assertTrue("E' stata lanciata una qualche eccezione, nonostante l'inserimento " +
                "fosse stato fatto in maniera corretta", inserimentoRiuscito);
    }
}
