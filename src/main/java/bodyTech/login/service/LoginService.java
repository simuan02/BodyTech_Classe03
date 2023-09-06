package bodyTech.login.service;

import bodyTech.model.entity.Profilo;

import java.sql.SQLException;

public interface LoginService {

    /**
     * Questo metodo verifica se le credenziali inserite in fase di accesso sono corrette o meno.
     * @param profilo Questo parametro contiene le credenziali, ossia identificativo e password, del profilo a cui si tenta di accedere.
     * @return Se viene trovata una corrispondenza con l'oggetto profilo all'interno del db, allora restituisci true. Altrimenti, restituisci false.
     * @throws SQLException
     */
    public boolean login(Profilo profilo) throws SQLException;

    /**
     * Questo metodo assegna a null il profilo passato come parametro
     * @param profilo
     * @return
     */
    public boolean logout(Profilo profilo);
}
