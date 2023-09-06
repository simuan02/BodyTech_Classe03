package bodyTech.model.entity;

/**
 * Contiene i dati relativi ad un esercizio facente parte di una scheda di allenamento.
 */
public class EsercizioAllenamento extends Esercizio{

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getIdScheda() {
        return idScheda;
    }

    public void setIdScheda(int idScheda) {
        this.idScheda = idScheda;
    }

    private String volume;
    private int idScheda;
}
