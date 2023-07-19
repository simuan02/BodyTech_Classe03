package bodyTech.model.entity;

public abstract class Profilo {

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String loggedUserLevel() {
        return this.getClass().getSimpleName();
    }

    private String nome;
    private String cognome;
    private String password;

}
