package bodyTech.login.service;

import bodyTech.model.entity.Profilo;

import java.sql.SQLException;

public interface LoginService {

    /**
     * Questa funzionalità consente a Utente, Istruttore e Amministratore il login alla piattaforma.
     * @param profilo Questo parametro contiene le credenziali, ossia identificativo e password, del profilo a cui si tenta di accedere.
     * @return Se viene trovata una corrispondenza con l'oggetto profilo all'interno del db, allora restituisci true. Altrimenti, restituisci false.
     * @throws SQLException
     */
    public boolean login(Profilo profilo) throws SQLException;

    /**
     * Questa funzionalità consente il logout di Utente, Istruttore e Amministratore dalla piattaforma.
     * @param profilo
     * @return
     */
    public boolean logout(Profilo profilo);
}
