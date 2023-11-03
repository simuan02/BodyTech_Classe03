package services;

import bodyTech.model.ConPool;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.SchedaAllenamento;
import bodyTech.model.entity.Utente;
import bodyTech.schedaAllenamento.service.SchedaService;
import bodyTech.schedaAllenamento.service.SchedaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ModificaSchedaAllenamentoTest {

    private SchedaAllenamento scheda;
    private SchedaAllenamento schedaModificata;

    @BeforeEach
    public void setUp() throws SQLException {
        Connection conn = ConPool.getConnection();
        scheda = SchedaAllenamentoDAO.findByID(250);
        if (scheda == null) {
            scheda = new SchedaAllenamento();
            scheda.setIdScheda(250);
            scheda.setTipo("Powerbuilding");
            scheda.setDataCompletamento(new Date(123, Calendar.DECEMBER, 25));
            scheda.setDataInizio(new Date(123, Calendar.OCTOBER, 25));
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
            scheda.setListaEsercizi(new ArrayList<>());
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO schedaAllenamento values (?,?,?,?,?,?)");
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
        }
        schedaModificata = new SchedaAllenamento();
        schedaModificata.setIdScheda(scheda.getIdScheda());
        schedaModificata.setDataInizio(scheda.getDataInizio());
        schedaModificata.setDataCompletamento(scheda.getDataCompletamento());
        schedaModificata.setUtente(scheda.getUtente());
        schedaModificata.setTipo(scheda.getTipo());
        schedaModificata.setIstruttore(scheda.getIstruttore());
        schedaModificata.setListaEsercizi(scheda.getListaEsercizi());
    }

    /**
     * Questo test verifica il comportamento del metodo SchedaService.modificaSchedaUtente() nel caso in cui la data di completamento
     * inserita nella scheda modificata fosse precedente alla data d'inizio
     */
    @Test
    public void test_ModificaScheda_3_2_1(){
        schedaModificata.setDataCompletamento(new Date(123, Calendar.OCTOBER, 24));
        try{
            SchedaService services = new SchedaServiceImpl();
            services.modificaSchedaUtente(schedaModificata, scheda.getUtente());
            fail("Modifica Effettuata Nonostante la Data di Completamento non fosse corretta");
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equals("Errato: Data di completamento precedente alla data di inizio"));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo SchedaService.modificaSchedaUtente() nel caso in cui il tipo fosse di
     * lunghezza maggiore di 30 caratteri
     */
    @Test
    public void test_ModificaScheda_3_2_2(){
        schedaModificata.setTipo("TIPO DELLA SCHEDA DI ALLENAMENTO DI LUNGHEZZA MOLTO MAGGIORE DI 30 CARATTERI");
        try{
            SchedaService services = new SchedaServiceImpl();
            services.modificaSchedaUtente(schedaModificata, scheda.getUtente());
            fail("Modifica Effettuata Nonostante il tipo fosse troppo lungo");
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equalsIgnoreCase("Errato: tipo troppo lungo"));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo SchedaService.modificaSchedaUtente() nel caso in cui il tipo fosse null
     */
    @Test
    public void test_ModificaScheda_3_2_3(){
        schedaModificata.setTipo(null);
        try{
            SchedaService services = new SchedaServiceImpl();
            services.modificaSchedaUtente(schedaModificata, scheda.getUtente());
            fail("Modifica Effettuata Nonostante il tipo fosse null");
        }
        catch(Exception e){
            assertTrue("Altra eccezione lanciata",
                    e.getMessage().equalsIgnoreCase("Errato: tipo assente"));
        }
    }

    /**
     * Questo test verifica il comportamento del metodo SchedaService.modificaSchedaUtente() nel caso in cui il tipo fosse di
     * lunghezza compresa tra 1 e 30 caratteri e la data di completamento fosse successiva alla data di inizio
     */
    @Test
    public void test_ModificaScheda_3_2_4(){
        schedaModificata.setTipo("Powerlifting");
        schedaModificata.setDataCompletamento(scheda.getDataCompletamento());
        schedaModificata.getDataCompletamento().setMonth(Calendar.NOVEMBER);
        schedaModificata.getDataCompletamento().setDate(30);
        try{
            SchedaService services = new SchedaServiceImpl();
            services.modificaSchedaUtente(schedaModificata, scheda.getUtente());
            boolean schedaAggiornata = false;
            for (SchedaAllenamento schedaDB: SchedaAllenamentoDAO.findAll()){
                if (schedaDB.getIdScheda() == schedaModificata.getIdScheda() && schedaDB.getTipo().equals(schedaModificata.getTipo()) &&
                        schedaDB.getDataInizio().getTime() == schedaModificata.getDataInizio().getTime() &&
                        schedaDB.getDataCompletamento().getTime() == schedaModificata.getDataCompletamento().getTime() &&
                        schedaDB.getUtente().equals(schedaModificata.getUtente()) && schedaDB.getIstruttore().equals(schedaModificata.getIstruttore()))
                    schedaAggiornata = true;
            }
            assertTrue("Scheda non aggiornata all'interno del DB", schedaAggiornata);
        }
        catch(Exception e){
            fail ("Eccezione lanciata: " + e.getMessage());
        }
    }
}
