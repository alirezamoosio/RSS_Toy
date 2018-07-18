package ir.sahab.rsstoy.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DatabaseStream {
    Connection connection;
    static final String DB_NAME = "NewsDB";
    static final String DB_URL = "jdbc:mysql://localhost/?&useSSL=false";
    static final String SITE_TABLE = "Websites";
    static ComboPooledDataSource source;

    static {
        source = new ComboPooledDataSource();
        source.setJdbcUrl(DB_URL);
        source.setUser("guest");
        source.setPassword("1234");
        source.setInitialPoolSize(5);
        source.setMinPoolSize(5);
        source.setAcquireIncrement(5);
        source.setMaxPoolSize(5);
        source.setMaxStatements(100);
    }

    DatabaseStream() {
        try {
            connection = source.getConnection();
            PreparedStatement statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            statement.executeUpdate();
            statement = connection.prepareStatement("use " + DB_NAME);
            statement.executeUpdate();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + SITE_TABLE +
                    " (ID INTEGER, WebsiteName VARCHAR (100), AttName VARCHAR (100), FuncName VARCHAR (100)," +
                    " RSSLink VARCHAR (1000), DateFormat VARCHAR (100), PRIMARY KEY (ID))");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
