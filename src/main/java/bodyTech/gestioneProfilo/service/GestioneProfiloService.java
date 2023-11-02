package bodyTech.gestioneProfilo.service;

import bodyTech.model.entity.Istruttore;
import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Questa classe fornisce tutte le operazioni di gestione degli utenti da parte di Istruttori e Amministratori.
 */
public interface GestioneProfiloService {

    /**
     * Questa funzionalità consente a Istruttori ed Amministratori di visualizzare
     * una lista di tutti gli Utenti iscritti alla piattaforma.
     * @param p il profilo che utilizza questa funzionalità.
     * @return la lista di tutti gli utenti iscritti alla piattaforma.
     */
    public List<Utente> visualizzaUtenti(Profilo p) throws SQLException;

    /**
     * Questa funzionalità consente agli Amministratori di visualizzare
     * una lista di tutti gli Istruttori iscritti alla piattaforma.
     * @param p il profilo che utilizza questa funzionalità.
     * @return la lista di tutti gli istruttori iscritti alla piattaforma.
     */
    public List<Istruttore> visualizzaIstruttori(Profilo p) throws SQLException;

    /**
     * Questa funzionalità consente agli Amministratori di creare nuovi Istruttori.
     * @param p il profilo che utilizza questa funzionalità.
     * @param istr l'istruttore da inserire all'interno del DB.
     * @param password la password dell'istruttore da inserire all'interno del DB.
     */
    public void aggiungiIstruttore(Profilo p, Istruttore istr, String password) throws SQLException, IOException;

    /**
     * Questa funzionalità consente agli Amministratori di modificare le
     * informazioni associate ad un Utente iscritto alla piattaforma.
     * @param p il profilo che utilizza questa funzionalità.
     * @param u l'Utente che deve essere modificato nel DB.
     * @param u2 l'Utente con le informazioni aggiornate.
     * @return l'utente con le informazioni aggiornate.
     */
    public Utente modificaUtente(Profilo p, Utente u, Utente u2) throws SQLException, IOException;

    /**
     * Questa funzionalità consente agli Amministratori di modificare le informazioni associate
     * ad un Istruttore iscritto alla piattaforma.
     * @param p il profilo che utilizza questa funzionalità.
     * @param istr l'Istruttore che deve essere modificato nel DB.
     * @param istr2 l'Istruttore con le informazioni aggiornate.
     * @return l'istruttore con le informazioni aggiornate.
     */
    public Istruttore modificaIstruttore(Profilo p, Istruttore istr, Istruttore istr2) throws SQLException, IOException;

    /**
     * Questa funzionalità consente ad Istruttori ed Amministratori di eliminare dal Database un Utente iscritto alla piattaforma.
     * @param p il profilo che utilizza questa funzionalità.
     * @param u l'Utente da eliminare.
     */
    public void eliminaUtente(Profilo p, Utente u) throws SQLException;

    /**
     * Questa funzionalità consente agli Amministratori di eliminare dal Database un Istruttore.
     * @param p il profilo che utilizza questa funzionalità.
     * @param istr l'istruttore da eliminare.
     */
    public void eliminaIstruttore(Profilo p, Istruttore istr) throws SQLException;

}
