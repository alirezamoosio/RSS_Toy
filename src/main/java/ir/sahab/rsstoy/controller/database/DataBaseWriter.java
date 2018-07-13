package ir.sahab.rsstoy.controller.database;

import ir.sahab.rsstoy.model.News;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DataBaseWriter extends DataBaseStream{
    private static final String DB_RAW_URL = "jdbc:mysql://localhost/?useSSL=false";

    public DataBaseWriter(String username, String password) {
        try {
            startConnection(username, password);
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(DB_RAW_URL, username, password);
                statement = connection.createStatement();
                statement.executeUpdate("CREATE DATABASE " + DB_NAME);
                statement.executeUpdate("use " + DB_NAME);
                statement.executeUpdate("CREATE TABLE " +
                        TABLE_NAME + " (Title VARCHAR(100), Author VARCHAR (100), "
                        + "Website VARCHAR(100), PubDate TIMESTAMP, "
                        + "Description VARCHAR(1000), Content VARCHAR(10000)) ");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void writeToDatabase(News news) throws SQLException {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(news.getDate());
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "VALUES (\'" + news.getTitle() + "\', \'" +
                news.getAuthor() + "\', \'" + news.getWebsite() +
                "\', \'" + time + "\', \'" +
                news.getDescription() + "\', \'" + news.getContent() + "\')";
        statement.executeUpdate(sql);
    }
}