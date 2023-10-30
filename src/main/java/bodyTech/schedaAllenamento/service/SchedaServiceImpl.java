package bodyTech.schedaAllenamento.service;

import bodyTech.model.dao.EsercizioAllenamentoDAO;
import bodyTech.model.dao.EsercizioDAO;
import bodyTech.model.dao.SchedaAllenamentoDAO;
import bodyTech.model.dao.UtenteDAO;
import bodyTech.model.entity.*;

import java.sql.SQLException;
import java.util.List;

public class SchedaServiceImpl implements SchedaService{
    @Override
    public List<SchedaAllenamento> visualizzaSchede(Profilo p) throws SQLException {
        if (p.loggedUserLevel().equalsIgnoreCase("Istruttore")){
            return SchedaAllenamentoDAO.findAll();
        }
        throw new RuntimeException("Operazione non consentita");
    }

    @Override
    public void eliminaEsercizio(int schedaID, String nomeEsercizio) throws SQLException {
        EsercizioAllenamentoDAO.deleteExercise(schedaID, nomeEsercizio);
    }

    @Override
    public void modificaSchedaUtente(SchedaAllenamento sa, Utente u) throws SQLException {
        List<Utente> users = UtenteDAO.visualizzaUtenti();
        boolean utenteTrovato = false;
        for (Utente user: users){
            if (user.getCodiceFiscale().equalsIgnoreCase(u.getCodiceFiscale())) {
                utenteTrovato = true;
                break;
            }
        }
        if (!utenteTrovato)
            throw new RuntimeException("Utente inesistente");
        SchedaAllenamento currentSa = SchedaAllenamentoDAO.findSchedaByUtente(u.getCodiceFiscale());
        if (currentSa != null){
            SchedaAllenamentoDAO.updateScheda(currentSa, sa);
            for (EsercizioAllenamento ea : sa.getListaEsercizi()) {
                EsercizioAllenamentoDAO.updateEsercizio(ea, sa.getIdScheda());
            }
        }
        else
            throw new RuntimeException("Nessuna scheda associata all'utente");
    }

    @Override
    public SchedaAllenamento rimuoviSchedaUtente(SchedaAllenamento scheda) throws SQLException {
        if (schedaEsistente(scheda)){
            SchedaAllenamentoDAO.deleteScheda(scheda.getIdScheda());
            return scheda;
        }
        return null;
    }

    @Override
    public void aggiungiEsercizio(Esercizio es, String volume, SchedaAllenamento scheda) throws SQLException {
        if (!schedaEsistente(scheda)){
            throw new RuntimeException("Scheda inesistente!");
        }
        boolean esercizioNonPresente = true;
        List<EsercizioAllenamento> listaEserciziScheda = EsercizioAllenamentoDAO.findBySchedaID(scheda.getIdScheda());
        for (EsercizioAllenamento ea: listaEserciziScheda){
            if (ea.getNomeEsercizio().equalsIgnoreCase(es.getNomeEsercizio())) {
                esercizioNonPresente = false;
                break;
            }
        }
        if (!esercizioNonPresente) {
            throw new RuntimeException("Esercizio gia' presente nella scheda!");
        }
        List<Esercizio> listaEsercizi = EsercizioDAO.findAll();
        boolean esercizioEsistente = false;
        for (Esercizio es2: listaEsercizi){
            if (es2.getNomeEsercizio().equalsIgnoreCase(es.getNomeEsercizio())){
                esercizioEsistente = true;
                break;
            }
        }
        if (!esercizioEsistente){
            throw new RuntimeException("Esercizio inesistente!");
        }
        EsercizioAllenamentoDAO.insertEsercizioAllenamento(es, volume, scheda.getIdScheda());
    }

    @Override
    public void aggiungiSchedaUtente(Profilo p, SchedaAllenamento scheda, Utente utente) throws SQLException {
        if (p.loggedUserLevel().equals("Istruttore")){
            scheda.setUtente(utente);
            SchedaAllenamentoDAO.insertScheda(scheda);
        }
        else
            throw new RuntimeException("Utente non autorizzato!");
    }

    private boolean schedaEsistente (SchedaAllenamento scheda) throws SQLException {
        List<SchedaAllenamento> listaSchede = SchedaAllenamentoDAO.findAll();
        boolean schedaEsistente = false;
        for (SchedaAllenamento sa: listaSchede){
            if (sa.getIdScheda() == scheda.getIdScheda()){
                schedaEsistente = true;
                break;
            }
        }
        return schedaEsistente;
    }
}
