package ir.sahab.rsstoy.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseStream {
    Connection connection;
    Statement statement;
    static final String DB_NAME = "NewsDB";
    static final String TABLE_NAME = "NewsTable";
    private static final String DB_URL = "jdbc:mysql://localhost/" + DB_NAME + "?autoReconnect=true&useSSL=false";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void startConnection(String userName, String password) throws SQLException {
        connection = DriverManager.getConnection(DB_URL, userName, password);
        statement = connection.createStatement();
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}
