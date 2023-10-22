package model;

import bodyTech.model.ConPool;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class SchedaDAOTest {

    private Utente user = new Utente();
    private Istruttore istr = new Istruttore();
    private SchedaAllenamento scheda = new SchedaAllenamento();

    @BeforeEach
    public void setUp(){
        user.setNome("Simone");
        user.setCognome("Esposito");
        user.setCodiceFiscale("SPSSMN01B04L845A");
        user.setPassword("ABCDEFGHIJ");
        istr.setNome("P");
        istr.setCognome("H");
        istr.setMatricolaIstruttore("ALCOEPROW1");
        istr.setPassword("ABCDEFGHIJ");
        istr.setSpecializzazione("BodyBuilding");
        scheda.setUtente(user);
        Date dataInizio = new Date(123, Calendar.OCTOBER, 25);
        scheda.setDataInizio(dataInizio);
        Date dataCompletamento = new Date(123, Calendar.DECEMBER, 25);
        dataCompletamento.setMonth(11);
        scheda.setDataCompletamento(dataCompletamento);
        scheda.setTipo("Powerbuilding");
        scheda.setIstruttore(istr);
        try{
            IstruttoreDAO.insertInstructor(istr);
        }
        catch(SQLException e){

        }
    }


    /**
     * Questo test verifica il comportamento del metodo findSchedaByUtente(String codiceFiscale), nel caso in cui
     * codiceFiscale sia inesistente
     */
    @Test
    public void findSchedaByUtente_TEST_CF_Inesistente() throws SQLException {
        Utente u = new Utente();
        u.setCodiceFiscale("SPSMNS12L20A201C");
        UtenteDAO.deleteUser(u); //viene invocato deleteUser per eliminare l'utente di cui trovare la scheda
        assertNull("Scheda Trovata nonostante l'utente non esista",
                SchedaAllenamentoDAO.findSchedaByUtente("SPSMNS12L20A201C"));
    }

    /**
     * Questo test verifica il comportamento del metodo findSchedaByUtente(String codiceFiscale), nel caso in cui
     * CodiceFiscale esista
     */
    @Test
    public void findSchedaByUtente_TEST_CF_Esistente() throws SQLException{
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
        try{
            SchedaAllenamentoDAO.deleteScheda(sa.getIdScheda());
            fail("Il metodo non ha lanciato alcuna eccezione");
        } catch(Exception e){
            System.out.println(e.getClass().getSimpleName());
            assertTrue("Altro tipo di eccezione lanciata", e.getClass().getSimpleName().equals("NullPointerException"));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo deleteScheda(int idScheda) nel caso in cui idScheda sia
     * presente nel DB
     */
    @Test
    public void deleteScheda_TESTING_IDScheda_PRESENTE() throws SQLException {
        SchedaAllenamento scheda = SchedaAllenamentoDAO.findAll().get(0);
        try{
            SchedaAllenamentoDAO.deleteScheda(scheda.getIdScheda());
            assertNull(SchedaAllenamentoDAO.findByID(scheda.getIdScheda()));
        } catch(Exception e){
            fail("Il metodo ha lanciato un'eccezione");
        }
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
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui la scheda passata come
     * parametro abbia un codice fiscale inesistente
     */
    @Test
    public void insertScheda_TESTING_CF_Inesistente() throws SQLException {
        String codiceFiscale = "CODICEERRATO";
        user.setCodiceFiscale(codiceFiscale);
        scheda.setUtente(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui la scheda passata come
     * parametro abbia un tipo di lunghezza maggiore di 30 caratteri
     */
    @Test
    public void insertScheda_TESTING_Tipo_Lunghezza_Maggiore_30() throws SQLException {
        scheda.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        UtenteDAO.insertUser(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui la scheda passata come
     * parametro abbia la data di completamento precedente alla data d'inizio
     */
    @Test
    public void insertScheda_TESTING_DataCompletamento_Precedente_DataInizio() throws SQLException {
        scheda.setDataCompletamento(new Date(123, Calendar.OCTOBER, 24));
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
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui la scheda passata come
     * parametro abbia un tipo null
     */
    @Test
    public void insertScheda_TESTING_Tipo_NULL() throws SQLException {
        scheda.setTipo(null);
        UtenteDAO.insertUser(user);
        boolean inserimentoRiuscito;
        try {
            SchedaAllenamentoDAO.insertScheda(scheda);
            inserimentoRiuscito = true;
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            inserimentoRiuscito = false;
        }
        assertFalse("Non è stata lanciata alcuna eccezione", inserimentoRiuscito);
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo test verifica il comportamento del metodo insertScheda(), nel caso in cui la scheda passata come
     * parametro abbia:
     * - Codice Fiscale Esistente;
     * - Tipo di lunghezza compresa tra 1 e 30 caratteri;
     * - Data di completamento successiva alla Data d'Inizio.
     */
    @Test
    public void insertScheda_TESTING_METHOD_OK() throws SQLException {
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
        SchedaAllenamentoDAO.deleteScheda(SchedaAllenamentoDAO.findSchedaByUtente(user.getCodiceFiscale()).getIdScheda());
    }

    /**
     * Questo metodo consente di effettuare l'inserimento all'interno del DB di una SchedaAllenamento al cui id non è
     * auto-incrementato, ma è uguale a 250 (da utilizzare solo per i casi di test relativi all'update).
     * @return scheda di allenamento con id=250
     * @throws SQLException
     */
    public SchedaAllenamento beforeUpdateSchedaTests() throws SQLException {
        Connection conn = ConPool.getConnection();
        SchedaAllenamento scheda = new SchedaAllenamento();
        SchedaAllenamento existingScheda = SchedaAllenamentoDAO.findByID(250);
        if (existingScheda != null)
            return existingScheda;
        scheda.setIdScheda(250);
        scheda.setTipo("Powerbuilding");
        scheda.setDataCompletamento(new Date(123, Calendar.DECEMBER, 25));
        scheda.setDataInizio(new Date(123, Calendar.OCTOBER,25));
        Utente user2 = UtenteDAO.findByCodiceFiscale("SPSSMN02A12L844S");
        if (user2 == null){
            user2 = new Utente();
            user2.setNome("Simone");
            user2.setCognome("Esposito");
            user2.setListeRichiesteModifica(new ArrayList<>());
            user2.setPassword("ABCDEFGHIJ");
            user2.setCodiceFiscale("SPSSMN02A12L844S");
            UtenteDAO.insertUser(user2);
        }
        scheda.setUtente(user2);
        scheda.setIstruttore(IstruttoreDAO.visualizzaIstruttori().get(0));
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SchedaAllenamento values (?,?,?,?,?,?)");
        pstmt.setInt(1, scheda.getIdScheda());
        java.sql.Date dataInizio = new java.sql.Date(scheda.getDataInizio().getTime());
        pstmt.setDate(2, dataInizio);
        java.sql.Date dataCompletamento = new java.sql.Date(scheda.getDataCompletamento().getTime());
        pstmt.setDate(3, dataCompletamento);
        pstmt.setString(4, scheda.getTipo());
        pstmt.setString(5, scheda.getUtente().getCodiceFiscale());
        pstmt.setString(6, scheda.getIstruttore().getMatricolaIstruttore());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        return scheda;
    }

    /**
     * Questo test verifica il comportamento del metodo updateScheda(), nel caso in cui la schedaModificata abbia
     * una data di completamento precedente alla sua data di inizio
     */
    @Test
    public void updateScheda_TESTING_DataCompletamento_Precedente_DataInizio() throws SQLException {
        SchedaAllenamento scheda1 = beforeUpdateSchedaTests();
        SchedaAllenamento scheda2 = new SchedaAllenamento();
        scheda2.setIdScheda(scheda1.getIdScheda());
        scheda2.setDataInizio(scheda1.getDataInizio());
        scheda2.setDataCompletamento(new Date(123, Calendar.OCTOBER, 24));
        scheda2.setIstruttore(scheda1.getIstruttore());
        scheda2.setUtente(scheda1.getUtente());
        scheda2.setTipo(scheda.getTipo());
        try{
            SchedaAllenamentoDAO.updateScheda(scheda1, scheda2);
            fail("Update eseguito nonostante la data di completamento non fosse corretta");
            SchedaAllenamentoDAO.deleteScheda(scheda1.getIdScheda());
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata: " + e.getStackTrace(),
                    (e.getClass().getSimpleName().equals("RuntimeException") &&
                            e.getMessage().equals("Errato: Data di completamento precedente alla data di inizio")));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo updateScheda(), nel caso in cui la schedaCorrente abbia
     * un id non presente all'interno del Database
     */
    @Test
    public void updateScheda_TESTING_ID_Inesistente() throws SQLException {
        SchedaAllenamento scheda1 = beforeUpdateSchedaTests();
        SchedaAllenamento scheda2 = new SchedaAllenamento();
        scheda1.setIdScheda(1920273);
        scheda2.setDataInizio(scheda1.getDataInizio());
        scheda2.setDataCompletamento(scheda1.getDataCompletamento());
        scheda2.setIstruttore(scheda1.getIstruttore());
        scheda2.setUtente(scheda1.getUtente());
        scheda2.setTipo(scheda.getTipo());
        try{
            SchedaAllenamentoDAO.updateScheda(scheda1, scheda2);
            fail("Update eseguito nonostante l'id della scheda corrente non fosse presente all'interno del DB");
            SchedaAllenamentoDAO.deleteScheda(scheda1.getIdScheda());
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata: " + e.getClass().getSimpleName() + " " + e.getMessage(),
                    (e.getClass().getSimpleName().equals("RuntimeException") &&
                            e.getMessage().equals("Scheda con id = " + scheda1.getIdScheda() + " assente")));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo updateScheda(), nel caso in cui la schedaModificata abbia
     * un tipo null
     */
    @Test
    public void updateScheda_TESTING_Tipo_Null() throws SQLException {
        SchedaAllenamento scheda1 = beforeUpdateSchedaTests();
        SchedaAllenamento scheda2 = new SchedaAllenamento();
        scheda2.setDataInizio(scheda1.getDataInizio());
        scheda2.setDataCompletamento(scheda1.getDataCompletamento());
        scheda2.setIstruttore(scheda1.getIstruttore());
        scheda2.setUtente(scheda1.getUtente());
        scheda2.setTipo(null);
        try{
            SchedaAllenamentoDAO.updateScheda(scheda1, scheda2);
            fail("Update eseguito nonostante il tipo della scheda modificata fosse null");
            SchedaAllenamentoDAO.deleteScheda(scheda1.getIdScheda());
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata: " + e.getClass().getSimpleName() + " " + e.getMessage(),
                    (e.getClass().getSimpleName().equals("RuntimeException") &&
                            e.getMessage().equals("Errato: tipo assente")));
        }
    }
    /**
     * Questo test verifica il comportamento del metodo updateScheda(), nel caso in cui la schedaModificata abbia
     * un tipo di lunghezza maggiore di 30 caratteri
     */
    @Test
    public void updateScheda_TESTING_Tipo_Maggiore_30Caratteri() throws SQLException {
        SchedaAllenamento scheda1 = beforeUpdateSchedaTests();
        SchedaAllenamento scheda2 = new SchedaAllenamento();
        scheda2.setDataInizio(scheda1.getDataInizio());
        scheda2.setDataCompletamento(scheda1.getDataCompletamento());
        scheda2.setIstruttore(scheda1.getIstruttore());
        scheda2.setUtente(scheda1.getUtente());
        scheda2.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        try{
            SchedaAllenamentoDAO.updateScheda(scheda1, scheda2);
            fail("Update eseguito nonostante il tipo della scheda modificata fosse di lunghezza maggiore di 30 caratteri");
            SchedaAllenamentoDAO.deleteScheda(scheda1.getIdScheda());
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata: " + e.getClass().getSimpleName() + " " + e.getMessage(),
                    (e.getClass().getSimpleName().equals("RuntimeException") &&
                            e.getMessage().equals("Errato: tipo troppo lungo")));
        }
    }
    /**
     * Questo test verifica il comportamento del metodo updateScheda(), nel caso in cui la schedaModificata abbia
     * un tipo di lunghezza compresa tra 1 e 30 caratteri, una data di completamento successiva alla data d'inizio
     * e la schedaCorrente abbia un id esistente all'interno del DB
     */
    @Test
    public void updateScheda_TESTING_METHOD_OK() throws SQLException {
        SchedaAllenamento scheda1 = beforeUpdateSchedaTests();
        SchedaAllenamento scheda2 = new SchedaAllenamento();
        scheda2.setDataInizio(scheda1.getDataInizio());
        scheda2.setDataCompletamento(new Date(123, Calendar.NOVEMBER, 29));
        scheda2.setIstruttore(scheda1.getIstruttore());
        scheda2.setUtente(scheda1.getUtente());
        scheda2.setTipo("Powerlifting");
        try{
            SchedaAllenamentoDAO.updateScheda(scheda1, scheda2);
            boolean schedaAggiornata = false;
            for (SchedaAllenamento schedaDB: SchedaAllenamentoDAO.findAll()){
                if (schedaDB.getIdScheda() == scheda1.getIdScheda() && schedaDB.getTipo().equals(scheda2.getTipo()) &&
                schedaDB.getDataInizio().getTime() == scheda2.getDataInizio().getTime() &&
                schedaDB.getDataCompletamento().getTime() == scheda2.getDataCompletamento().getTime() &&
                schedaDB.getUtente().equals(scheda2.getUtente()) && schedaDB.getIstruttore().equals(scheda2.getIstruttore()))
                    schedaAggiornata = true;
            }
            assertTrue("Scheda non aggiornata all'interno del DB", schedaAggiornata);
        }
        catch(Exception e){
            fail("Eccezione al metodo lanciata");
        }
    }
}
