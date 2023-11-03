package bodyTech.model;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.util.TimeZone;
import java.sql.Connection;
import java.sql.SQLException;

public class ConPool {
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException {
        if (datasource == null) {
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://bodytechdb-bodytechdb.a.aivencloud.com:25280/BodyTechDB?sslmode=require");
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername("avnadmin");
            p.setPassword("AVNS_QjHHs6Uj8EFfO_7fi_j");
            p.setMaxActive(1000);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(10);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }
}
