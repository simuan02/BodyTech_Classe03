package bodyTech.model.entity;

import java.util.List;
import java.util.Objects;

/**
 * Contiene i dati relativi ad un utente registrato, cliente della palestra.
 */
public class Utente extends Profilo{

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public List<RichiestaModificaScheda> getListeRichiesteModifica() {
        return listeRichiesteModifica;
    }

    public void setListeRichiesteModifica(List<RichiestaModificaScheda> listeRichiesteModifica) {
        this.listeRichiesteModifica = listeRichiesteModifica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return codiceFiscale.equals(utente.codiceFiscale) && super.equals(utente);
    }

    private String codiceFiscale;
    private List<RichiestaModificaScheda> listeRichiesteModifica;
}
