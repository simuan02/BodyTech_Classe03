package bodyTech.model.entity;


/**
 * Contiene i dati relativi ad una richiesta fatta da un utente per la modifica della propria scheda.
 */
public class RichiestaModificaScheda {

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Boolean isEsito() {
        return esito;
    }

    public void setEsito(Boolean esito) {
        this.esito = esito;
    }

    private int idRichiesta;
    private String messaggio;
    private Boolean esito;
}
