package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {

    private static final String configSetting = "config/config.settings";
    private SQLServerDataSource dataSource;

    public DatabaseConnector() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(configSetting)));
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(properties.getProperty("Server"));
        dataSource.setDatabaseName(properties.getProperty("Database"));
        dataSource.setUser(properties.getProperty("User"));
        dataSource.setPassword(properties.getProperty("Password"));
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }
}
