package bodyTech.registrazione.service;

import bodyTech.model.entity.Utente;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Questa classe consente le operazioni relative alla registrazione di Utenti
 */
public interface RegistrazioneService {
    public List<String> registrazioneUtente(Utente utente, String password);
}
