package bodyTech.model.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataCompletamento() {
        return dataCompletamento;
    }

    public void setDataCompletamento(Date dataCompletamento) {
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
    private Date dataInizio;
    private Date dataCompletamento;
    private String tipo;
    private Utente utente;
    private Istruttore istruttore;
    private List<EsercizioAllenamento> listaEsercizi;
}
