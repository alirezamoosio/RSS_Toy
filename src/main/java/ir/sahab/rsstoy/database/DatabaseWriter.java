package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class DatabaseWriter extends DatabaseStream {

    public DatabaseWriter() {
        super();
    }

    public void write(News news) throws SQLException {
        Statement statement1 = connection.createStatement();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(news.getDate());
        String websiteName = news.getWebsite().replace(" ", "_");
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + websiteName +
                " (ID INTEGER, Title VARCHAR(1000), Author VARCHAR (1000), PubDate TIMESTAMP," +
                " Description TEXT, Content TEXT, PRIMARY KEY (ID))");
        statement.executeUpdate();
        statement = connection.prepareStatement("INSERT INTO " + websiteName + " VALUES(?, ?, ?, ?, ?, ?);");
        statement.setInt(1, news.getTitle().hashCode());
        statement.setString(2, news.getTitle());
        statement.setString(3, news.getAuthor());
        statement.setString(4, time);
        statement.setString(5, news.getDescription());
        statement.setString(6, news.getContent());
        statement.executeUpdate();
        statement.close();
    }

    public void remove(News news) throws SQLException {
        // TODO: 7/18/18 Implement
    }

}