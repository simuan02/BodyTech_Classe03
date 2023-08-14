package bodyTech.login.service;

import bodyTech.model.entity.Profilo;

import java.sql.SQLException;

public interface LoginService {

    public boolean login(Profilo profilo) throws SQLException;
    public boolean logout(Profilo profilo);
}
