package model;

import bodyTech.model.dao.EsercizioDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EsercizioDAOTest {

    /**
     * Verifica il funzionamento del metodo EsercizioDAO.findByName() nel caso in cui il nome dell'esercizio non sia presente
     * nel DB
     */
    @Test
    public void test_findByNameMethod_1 () throws SQLException {
        String nomeNonPresente = "EsercizioInesistente";
        assertNull("E' stato restituito qualcosa di diverso da null", EsercizioDAO.findByName(nomeNonPresente));
    }


    /**
     * Verifica il funzionamento del metodo EsercizioDAO.findByName() nel caso in cui il nome dell'esercizio sia presente
     * nel DB
     */
    @Test
    public void test_findByNameMethod_2 () throws SQLException {
        String nomePresente = EsercizioDAO.findAll().get(0).getNomeEsercizio();
        assertNotNull("Non è stato restituito alcun esercizio", EsercizioDAO.findByName(nomePresente));
    }
}
