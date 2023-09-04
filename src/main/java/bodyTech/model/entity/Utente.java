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

    public String getMatricolaIstruttore() {
        return matricolaIstruttore;
    }

    public void setMatricolaIstruttore(String matricolaIstruttore) {
        this.matricolaIstruttore = matricolaIstruttore;
    }

    private String codiceFiscale, matricolaIstruttore;
    private List<RichiestaModificaScheda> listeRichiesteModifica;


}
