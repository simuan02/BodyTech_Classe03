package bodyTech.model.entity;

import java.util.List;

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

    private String codiceFiscale;
    private List<RichiestaModificaScheda> listeRichiesteModifica;
}
