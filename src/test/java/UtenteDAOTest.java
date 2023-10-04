import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtenteDAOTest {

    private Utente user1;

    @BeforeEach
    public void init (){
        user1 = new Utente();
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) nel caso in cui user rispetti
     * tutte le pre-condizioni:
     * - codiceFiscale di 16 caratteri, non esistente;
     * - nome, password, cognome non nulli.
     */
    @Test
    public void testInsertUser_Method_OK() throws SQLException {
        Random rand = new Random();
        String s = "";
        for (int i = 0; i < 16; i++){
            char c = (char) (rand.nextInt(43) + 48);
            s += c;
        }
        user1.setNome("A");
        user1.setCognome("B");
        user1.setPassword("ABCDEFGH");
        user1.setCodiceFiscale(s);
        assertTrue("Utente Registrato alla Piattaforma", UtenteDAO.insertUser(user1));
        UtenteDAO.deleteUser(user1);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso di user.CodiceFiscale già esistente
     */
    @Test
    public void testInsertUser_Method_1() throws SQLException {
        user1.setNome("A");
        user1.setCognome("B");
        user1.setPassword("ABCDEFGH");
        user1.setCodiceFiscale("ABCDEFGHIJKLMNOP");
        UtenteDAO.insertUser(user1);
        Assertions.assertFalse(UtenteDAO.insertUser(user1), "Codice Fiscale Già Presente");
        UtenteDAO.deleteUser(user1);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo nome di user null
     */
    @Test
    public void testInsertUser_Method_2() throws SQLException {
        Utente user2 = new Utente();
        user2.setCodiceFiscale("ABCDEFGHIJKLMNOX");
        user2.setPassword("ABCDEFGHIJ");
        user2.setCognome("Esposito");
        assertFalse("Campo nome mancante", UtenteDAO.insertUser(user2));
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo cognome di user null
     */
    @Test
    public void testInsertUser_Method_3() throws SQLException {
        Utente user2 = new Utente();
        user2.setCodiceFiscale("ABCDEFGHIJKLMNOX");
        user2.setPassword("ABCDEFGHIJ");
        user2.setNome("Simone");
        assertFalse("Campo cognome mancante", UtenteDAO.insertUser(user2));
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo password di user null
     */
    @Test
    public void testInsertUser_Method_4() throws SQLException {
        Utente user2 = new Utente();
        user2.setCodiceFiscale("ABCDEFGHIJKLMNOX");
        user2.setCognome("Esposito");
        user2.setNome("Simone");
        assertFalse("Campo password mancante", UtenteDAO.insertUser(user2));
    }

}
