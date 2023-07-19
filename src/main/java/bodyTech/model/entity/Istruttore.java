package bodyTech.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Istruttore extends Profilo{

    public String getMatricolaIstruttore() {
        return matricolaIstruttore;
    }

    public void setMatricolaIstruttore(String matricolaIstruttore) {
        this.matricolaIstruttore = matricolaIstruttore;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    public List<SchedaAllenamento> getListaSchedeCreate() {
        return listaSchedeCreate;
    }

    public void setListaSchedeCreate(List<SchedaAllenamento> listaSchedeCreate) {
        this.listaSchedeCreate = listaSchedeCreate;
    }

    private String matricolaIstruttore;
    private String specializzazione;
    private List<SchedaAllenamento> listaSchedeCreate = new ArrayList<>();
}
