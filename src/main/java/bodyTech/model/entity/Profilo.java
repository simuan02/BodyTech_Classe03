package bodyTech.model.entity;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Superclasse contenente i campi nome, cognome e password comuni alle sottoclassi Utente, Istruttore e Amministratore.
 */

public abstract class Profilo {

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Questo metodo setta la password del Profilo, codificando la stringa passata come parametro utilizzando il cifrario SHA-1
     * @param password
     */
    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String loggedUserLevel() {
        return this.getClass().getSimpleName();
    }

    private String nome;
    private String cognome;
    private String password;

}
