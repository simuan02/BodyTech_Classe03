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

    private String volume;
}
