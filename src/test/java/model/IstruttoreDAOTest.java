package model;

import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Utente;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IstruttoreDAOTest {

    private Istruttore istr;

    @BeforeEach
    public void init (){
        istr = new Istruttore();
        istr.setNome("Marco");
        istr.setCognome("Guida");
        istr.setPassword("QqWwEeRrTt");
        istr.setMatricolaIstruttore("1030650121");
        istr.setSpecializzazione("Powerlifting");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) nel caso in cui istr rispetti
     * tutte le pre-condizioni:
     * - matricola di 10 caratteri, non esistente;
     * - nome, password, cognome, specializzazione non nulli.
     */
    @Test
    public void testInsertInstructor_Method_OK() throws SQLException {
        Random rand = new Random();
        String s = "";
        for (int i = 0; i < 10; i++){
            char c = (char) (rand.nextInt(43) + 48);
            s += c;
        }
        istr.setMatricolaIstruttore(s);
        Assertions.assertTrue(IstruttoreDAO.insertInstructor(istr), "Errore nella registrazione dell'istruttore");
        IstruttoreDAO.deleteInstructor(istr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso di istr.Matricola già esistente
     */
    @Test
    public void testInsertInstructor_Method_1() throws SQLException {
        istr.setMatricolaIstruttore("1030650105");
        IstruttoreDAO.insertInstructor(istr);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante la matricola di istr " +
                "fosse già presente nel DB");
        IstruttoreDAO.deleteInstructor(istr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso del campo nome di istr null
     */
    @Test
    public void testInsertInstructor_Method_2() throws SQLException {
        istr.setNome(null);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante il nome di istr fosse null");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso del campo cognome di istr null
     */
    @Test
    public void testInsertInstructor_Method_3() throws SQLException {
        istr.setCognome(null);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante il campo cognome di istr fosse null");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso del campo password di istr null
     */
    @Test
    public void testInsertInstructor_Method_4() throws SQLException {
        istr.setPassword(null);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante il campo password di istr fosse null");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso del campo matricola null
     */
    @Test
    public void testInsertInstructor_Method_5() throws SQLException {
        istr.setMatricolaIstruttore(null);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante il campo matricola di istr fosse null");
    }

    /**
     * Questo caso di test verifica il comportamento del metodo IstruttoreDAO.insertInstructor(istr) in caso del campo specializzazione null
     */
    @Test
    public void testInsertInstructor_Method_6() throws SQLException {
        istr.setSpecializzazione(null);
        Assertions.assertFalse(IstruttoreDAO.insertInstructor(istr), "Registrazione riuscita nonostante il campo specializzazione di istr fosse null");
    }










}
