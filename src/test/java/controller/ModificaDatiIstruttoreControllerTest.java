package controller;

import bodyTech.gestioneProfilo.controller.GestioneProfiloController;
import bodyTech.model.dao.IstruttoreDAO;
import bodyTech.model.entity.Amministratore;
import bodyTech.model.entity.Istruttore;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ModificaDatiIstruttoreControllerTest {
    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Istruttore istr;

    @BeforeEach
    public void setUp() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        inserisciIstruttoreTest();
    }

    public void inserisciIstruttoreTest() throws SQLException {
        istr = new Istruttore();
        istr.setNome("Nome");
        istr.setCognome("Cognome");
        istr.setPassword("ooo909ioio");
        istr.setMatricolaIstruttore("0129032143");
        istr.setSpecializzazione("Specializzazione");
        IstruttoreDAO.insertInstructor(istr);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr.nome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_1() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn("FieryEboseleEnzoEbosseKingsleyEhizibueeee");
        when(request.getParameter("CognomeIstruttore")).thenReturn(istr.getCognome());
        when(request.getParameter("MatricolaIstruttore")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn(istr.getSpecializzazione());
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        verify(request).setAttribute("LunghezzaNomeErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr.cognome di lunghezza maggiore di 40 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_2() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn(istr.getNome());
        when(request.getParameter("CognomeIstruttore")).thenReturn("EboseleFieryEnzoEbosseKingsleyEhizibueeee");
        when(request.getParameter("MatricolaIstruttore")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn(istr.getSpecializzazione());
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        verify(request).setAttribute("LunghezzaCognomeErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr.matricolaIstruttore di lunghezza maggiore di 10 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_3() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn(istr.getNome());
        when(request.getParameter("CognomeIstruttore")).thenReturn(istr.getCognome());
        when(request.getParameter("MatricolaIstruttore")).thenReturn("103065012700");
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn(istr.getSpecializzazione());
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        verify(request).setAttribute("LunghezzaMatricolaErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr.matricolaIstruttore di lunghezza minore di 10 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_4() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn(istr.getNome());
        when(request.getParameter("CognomeIstruttore")).thenReturn(istr.getCognome());
        when(request.getParameter("MatricolaIstruttore")).thenReturn("10306501");
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn(istr.getSpecializzazione());
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        verify(request).setAttribute("LunghezzaMatricolaErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr.specializzazione di lunghezza maggiore di 30 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_5() throws ServletException, IOException {
        when(request.getParameter("NomeIstruttore")).thenReturn(istr.getNome());
        when(request.getParameter("CognomeIstruttore")).thenReturn(istr.getCognome());
        when(request.getParameter("MatricolaIstruttore")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn("BodybuildingPowerliftingCrossfit");
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        verify(request).setAttribute("LunghezzaSpecializzazioneErrata", true);
        verify(dispatcher).forward(request, response);
    }

    /**
     * Questo caso di test verifica il comportamento del metodo GestioneProfiloController.modificaIstruttoreMethod
     * in caso di newIstr che rispetti le seguenti condizioni:
     * - nome di lunghezza minore o uguale di 40 caratteri
     * - cognome di lunghezza minore o uguale di 40 caratteri
     * - matricola di lunghezza 10
     * - specializzazione di lunghezza minore o uguale di 30 caratteri
     */
    @Test
    public void Test_ModificaDatiIstruttore_2_6() throws ServletException, IOException, SQLException {
        Istruttore istr2 = new Istruttore();
        istr2.setNome("Fiery");
        istr2.setCognome("Ebosele");
        istr2.setMatricolaIstruttore("0129032143");
        istr2.setSpecializzazione("Bodybuilding");
        when(request.getParameter("NomeIstruttore")).thenReturn(istr2.getNome());
        when(request.getParameter("CognomeIstruttore")).thenReturn(istr2.getCognome());
        when(request.getParameter("MatricolaIstruttore")).thenReturn(istr2.getMatricolaIstruttore());
        when(request.getParameter("MatricolaVecchia")).thenReturn(istr.getMatricolaIstruttore());
        when(request.getParameter("PasswordIstruttore")).thenReturn(istr2.getPassword());
        when(request.getParameter("SpecializzazioneIstruttore")).thenReturn(istr2.getSpecializzazione());
        when(request.getRequestDispatcher("/listaIstruttori")).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("Profilo")).thenReturn(new Amministratore());
        GestioneProfiloController.modificaIstruttoreMethod(request, response);
        Assertions.assertTrue(IstruttoreDAO.visualizzaIstruttori().contains(istr2), "Modifica non effettutata!");
        IstruttoreDAO.deleteInstructor(istr2);
        verify(dispatcher).forward(request, response);
    }

}
