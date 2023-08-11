package bodyTech.login;

import bodyTech.model.entity.Profilo;

public interface LoginService {

    public boolean login(Profilo profilo);
    public boolean logout(Profilo profilo);
}
