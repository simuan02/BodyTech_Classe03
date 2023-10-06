package model;

import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Utente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtenteDAOTest {

    private Utente user;

    @BeforeEach
    public void init (){
        user = new Utente();
        user.setNome("Simone");
        user.setCognome("Esposito");
        user.setCodiceFiscale("SPSSMN01B04L845A");
        user.setPassword("ABCDEFGHIJ");
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
        user.setCodiceFiscale(s);
        assertTrue("Errore nella registrazione dell'utente", UtenteDAO.insertUser(user));
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso di user.CodiceFiscale già esistente
     */
    @Test
    public void testInsertUser_Method_1() throws SQLException {
        user.setCodiceFiscale("ABCDEFGHIJKLMNOP");
        UtenteDAO.insertUser(user);
        Assertions.assertFalse(UtenteDAO.insertUser(user), "Registrazione riuscita nonostante il codice fiscale di user " +
                "fosse già presente nel DB");
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo nome di user null
     */
    @Test
    public void testInsertUser_Method_2() throws SQLException {
        user.setNome(null);
        assertFalse("Registrazione riuscita nonostante il nome di user fosse null", UtenteDAO.insertUser(user));
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo cognome di user null
     */
    @Test
    public void testInsertUser_Method_3() throws SQLException {
        user.setCognome(null);
        assertFalse("Registrazione riuscita nonostante il campo cognome di user fosse null", UtenteDAO.insertUser(user));
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo password di user null
     */
    @Test
    public void testInsertUser_Method_4() throws SQLException {
        user.setPassword(null);
        assertFalse("Registrazione riuscita nonostante il campo password di user fosse null", UtenteDAO.insertUser(user));
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.insertUser(user) in caso del campo codice fiscale null
     */
    @Test
    public void testInsertUser_Method_5() throws SQLException {
        user.setCodiceFiscale(null);
        assertFalse("Registrazione riuscita nonostante il campo codice fiscale di user fosse null", UtenteDAO.insertUser(user));
    }


    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.updateUser(oldUser, newUser) nel caso in cui newUser rispetti
     * tutte le pre-condizioni:
     * - codiceFiscale di 16 caratteri, non esistente o identico a quello di oldUser;
     * - nome, cognome non nulli.
     */
    @Test
    public void testUpdateUser_Method_OK() throws SQLException, IOException {
        Utente user1 = new Utente();
        user1.setNome("Simon");
        user1.setCognome("ESP");
        user1.setCodiceFiscale("SPSSMN02B05L834Q");
        user1.setPassword(user.getPassword());
        UtenteDAO.insertUser(user);
        assertTrue("Modifica Non Effettuata", UtenteDAO.updateUser(user, user1));
        UtenteDAO.deleteUser(user1);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.updateUser(oldUser, newUser) in caso di
     * newUser.CodiceFiscale già esistente, diverso da quello di oldUser
    */
    @Test
    public void testUpdateUser_Method_1() throws SQLException, IOException {
        Utente user1 = new Utente();
        user1.setNome("A");
        user1.setCognome("B");
        user1.setPassword("ABCDEFGH");
        user1.setCodiceFiscale("ABCDEFGHIJKLMNOP");
        UtenteDAO.insertUser(user);
        UtenteDAO.insertUser(user1);
        boolean existingCF = false;
        try{
            UtenteDAO.updateUser(user, user1);
        }
        catch(IOException e){
            existingCF = true;
        }
        assertTrue("Codice Fiscale già Presente non rilevato dal metodo", existingCF);
        UtenteDAO.deleteUser(user1);
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.updateUser(oldUser, newUser) in caso del campo
     * nome di newUser null
    */
    @Test
    public void testUpdateUser_Method_2() throws SQLException, IOException {
        Utente user2 = new Utente();
        user2.setCodiceFiscale("ABCDEFGHIJKLMNOX");
        user2.setPassword("ABCDEFGHIJ");
        user2.setCognome("Esposito");
        UtenteDAO.insertUser(user);
        assertFalse("Modifica riuscita nonostante il campo nome fosse null", UtenteDAO.updateUser(user, user2));
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.updateUser(oldUser, newUser) in caso del campo
     * cognome di newUser null
     */
    @Test
    public void testUpdateUser_Method_3() throws SQLException, IOException {
        Utente user2 = new Utente();
        user2.setCodiceFiscale("ABCDEFGHIJKLMNOX");
        user2.setPassword("ABCDEFGHIJ");
        user2.setNome("Simone");
        UtenteDAO.insertUser(user);
        assertFalse("Modifica riuscita nonostante il campo cognome fosse null", UtenteDAO.updateUser(user, user2));
        UtenteDAO.deleteUser(user);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo UtenteDAO.updateUser(oldUser, newUser) in caso del campo
     * codiceFiscale di newUser null
     */
    @Test
    public void testUpdateUser_Method_4() throws SQLException, IOException {
        Utente user2 = new Utente();
        user2.setCognome("Esposito");
        user2.setPassword("ABCDEFGHIJ");
        user2.setNome("Simone");
        UtenteDAO.insertUser(user);
        assertFalse("Modifica riuscita nonostante il campo codice fiscale fosse null", UtenteDAO.updateUser(user, user2));
        UtenteDAO.deleteUser(user);
    }
}
