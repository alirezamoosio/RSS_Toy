package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DatabaseWriter extends DatabaseStream {

    public DatabaseWriter(String userName, String password) {
        super(userName, password);
    }

    public void writeToDatabase(News news) throws SQLException {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(news.getDate());
        String websiteName = news.getWebsite().replace(" ", "_");
        String sql = "CREATE TABLE IF NOT EXISTS " +
                websiteName + " (Title VARCHAR(1000), Author VARCHAR (1000), "
                + "PubDate TIMESTAMP, "
                + "Description TEXT, Content TEXT) ";
        statement.executeUpdate(sql);
        statement.executeUpdate("INSERT INTO " + websiteName + " VALUES(\'"
        + news.getTitle() + "\', \'" + news.getAuthor() + "\', \'" + time + "\', \'"
        + news.getDescription() + "\', \'" + news.getContent() + "\')");
    }
}