package bodyTech.gestioneProfilo.service;

import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.SQLException;

/**
 * Questa classe fornisce tutte le operazioni di visualizzazione del profilo.
 */
public interface ProfiloService {

    /**
     * Questo metodo consente di verificare se il profilo passato come parametro è stato inizializzato o meno.
     * @param p Il Profilo da verificare
     * @return true, se il profilo passato è diverso da null. Lancia un'eccezione, altrimenti.
     */
    public boolean visualizzaProfilo (Profilo p);

    /**
     * Questo metodo consente la modifica dei dati nel DB, aggiornando le informazioni di oldProfile con le informazioni di newProfile.
     * Questo metodo verifica, tramite il metodo loggedUserLevel(), a quale classe appartiene oldProfile:
     * - Se oldProfile è di tipo Utente, allora lancia il metodo UtenteDAO.updateUser(oldUser, newUser);
     * - Se oldProfile è di tipo Istruttore, allora lancia il metodo IstruttoreDAO.updateInstructor(oldInstructor, newInstructor);
     * - Se oldProfile è di tipo Amministratore, allora lancia il metodo AmministratoreDAO.updateAdmin(oldAdmin, newAdmin).
     * A seguito dell'operazione di aggiornamento, controlla se nella lista di profili corrispondenti al tipo di oldProfile
     * è presente newProfile. Se lo è, allora resituisci true, altrimenti restituisci false.
     * @param oldProfile il profilo da aggiornare
     * @param newProfile il profilo con le informazioni aggiornate
     * @return l'esito dell'aggiornamento: true, se andato a buon fine; false, altrimenti.
     * @throws SQLException
     */
    public boolean modificaDati (Profilo oldProfile, Profilo newProfile) throws SQLException;


    /**
     * Questo metodo implementa l'operazione di ricerca nel DB della scheda di allenamento associata al Profilo p.
     * Se p non è un Utente, allora lancia un'eccezione con messaggio "Accesso non autorizzato al metodo".
     * Se p è un Utente, trova la SchedaAllenamento associata all'Utente, tramite il metodo
     * SchedaAllenamentoDAO.findSchedaByUtente(codiceFiscale).
     * @param p il Profilo di cui trovare la Scheda di Allenamento
     * @return scheda di allenamento associata al profilo passato come parametro.
     * @throws SQLException
     */
    public SchedaAllenamento visualizzaScheda (Profilo p) throws SQLException;
}
