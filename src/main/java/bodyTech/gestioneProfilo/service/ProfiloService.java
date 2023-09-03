package bodyTech.gestioneProfilo.service;

import bodyTech.model.entity.Profilo;
import bodyTech.model.entity.SchedaAllenamento;

import java.sql.SQLException;

/**
 * Questa classe fornisce tutte le operazioni di visualizzazione del profilo.
 */
public interface ProfiloService {

    public boolean visualizzaProfilo (Profilo p) throws SQLException;
    public boolean modificaDati (Profilo oldProfile, Profilo newProfile) throws SQLException;
    public SchedaAllenamento visualizzaScheda (Profilo p) throws SQLException;
}
