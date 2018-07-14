package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseReader extends DatabaseStream {

    public DatabaseReader(String userName, String password) {
        super(userName, password);
    }

    public List<News> getAllNews() throws SQLException {
        return getNewsByCondition("");
    }


    public List<News> getNewsByExactTitle(String title) throws SQLException {
        return getNewsByCondition("WHERE Title = title");
    }

    public List<News> getNewsByTitleSearch(String query) throws SQLException {
        return getNewsByCondition("WHERE Title LIKE '%query%'");
    }

    private List<News> getNewsByCondition(String condition) throws SQLException {
        List<News> list = new ArrayList<>();
        ResultSet sitesSet = statement.executeQuery("SELECT * FROM " + SITE_TABLE);
        while (sitesSet.next()) {
            String websiteName = sitesSet.getString("WebsiteName");
            ResultSet newsSet = statement.executeQuery("SELECT * FROM " + websiteName.replace(" ", "_") + " " + condition);
            while (newsSet.next()) {
                list.add(fromResultSet(newsSet));
            }
        }
        return list;
    }

    private News fromResultSet(ResultSet resultSet) throws SQLException {
        String title, author, description, content;
        Date date;
        title = resultSet.getString("Title");
        author = resultSet.getString("Author");
        description = resultSet.getString("Description");
        content = resultSet.getString("Content");
        date = resultSet.getDate("PubDate");
        return News.newNews().title(title).author(author).date(date)
                .description(description).content(content).build();
    }
}
