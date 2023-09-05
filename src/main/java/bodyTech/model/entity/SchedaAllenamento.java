package bodyTech.model.entity;

import java.util.Date;
import java.util.List;

/**
 * Contiene i dati relativi ad una scheda di allenamento.
 */
public class SchedaAllenamento {

    public int getIdScheda() {
        return idScheda;
    }

    public void setIdScheda(int idScheda) {
        this.idScheda = idScheda;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataCompletamento() {
        return dataCompletamento;
    }

    public void setDataCompletamento(String dataCompletamento) {
        this.dataCompletamento = dataCompletamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Istruttore getIstruttore(){
        return istruttore;
    }

    public void setIstruttore(Istruttore istruttore){
        this.istruttore = istruttore;
    }

    public List<EsercizioAllenamento> getListaEsercizi() {
        return listaEsercizi;
    }

    public void setListaEsercizi(List<EsercizioAllenamento> listaEsercizi) {
        this.listaEsercizi = listaEsercizi;
    }

    private int idScheda;
    private String dataInizio;
    private String dataCompletamento;
    private String tipo;
    private Utente utente;
    private Istruttore istruttore;
    private List<EsercizioAllenamento> listaEsercizi;
}
