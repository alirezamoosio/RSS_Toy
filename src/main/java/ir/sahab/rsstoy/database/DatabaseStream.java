package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseStream {
    Connection connection;
    private static final String DB_NAME = "NewsDB";
    private static final String DB_URL = "jdbc:mysql://localhost/?&useSSL=false";
    static final String SITE_TABLE = "Websites";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    DatabaseStream(String userName, String password) {
        try {
            connection = DriverManager.getConnection(DB_URL, userName, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            statement.executeUpdate("use " + DB_NAME);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + SITE_TABLE
            + " (ID INTEGER , WebsiteName VARCHAR(100), AttName VARCHAR (100), FuncName VARCHAR(100)," +
                    " RSSLink VARCHAR (1000), DateFormat VARCHAR (100), PRIMARY KEY (ID))");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
