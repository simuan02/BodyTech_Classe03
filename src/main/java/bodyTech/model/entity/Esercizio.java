package bodyTech.model.entity;

/**
 * Contiene i dati relativi ad un esercizio.
 */
public class Esercizio {

    public String getNomeEsercizio() {
        return nomeEsercizio;
    }

    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    private String nomeEsercizio;
    private String descrizione;
}
