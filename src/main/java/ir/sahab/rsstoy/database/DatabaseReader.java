package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseReader extends DatabaseStream {

    public DatabaseReader(String userName, String password) {
        super();
    }

    public List<News> getAllNews() throws SQLException {
        return getNewsByCondition("", "");
    }


    public List<News> getNewsByExactTitle(String title) throws SQLException {
        return getNewsByCondition("", "WHERE Title = \'" + title + "\'");
    }

    public List<News> getNewsByTitleSearch(String query) throws SQLException {
        return getNewsByCondition("", "WHERE Title LIKE \'%" + query + "%\'");
    }

    public List<News> getNewsByContentSearch(String query) throws SQLException {
        return getNewsByCondition("", "WHERE Content LIKE \'%" + query + "%\'");
    }

    public List<News> getLastNews(String websiteName, int number) throws SQLException {
        websiteName = websiteName.replace(" ", "_");
        String websiteCondition = "WHERE WebsiteName = \'" + websiteName + "\'";
        String newsCondition = "ORDER BY PubDate DESC LIMIT " + number;
        return getNewsByCondition(websiteCondition, newsCondition);
    }

    public List<News> getNewsByDate(String websiteName, String dateFormat) throws SQLException {
        String websiteCondition = "WHERE WebsiteName = \'" + websiteName + "\'";
        String newsCondition = "WHERE DATEDIFF(PubDate, \'" + dateFormat + "\') = 0";
        return getNewsByCondition(websiteCondition, newsCondition);
    }

    private List<News> getNewsByCondition(String websiteCondition, String newsCondition) throws SQLException {
        List<News> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SITE_TABLE + " " + websiteCondition);
        ResultSet sitesSet = statement.executeQuery();
        while (sitesSet.next()) {
            String websiteName = sitesSet.getString("WebsiteName");
            PreparedStatement otherStatement = connection.prepareStatement("SELECT * FROM " + websiteName.replace(" ", "_") + " " + newsCondition);
            ResultSet newsSet = otherStatement.executeQuery();
            while (newsSet.next()) {
                list.add(fromResultSet(newsSet, websiteName));
            }
            otherStatement.close();
        }
        statement.close();
        return list;
    }

    private News fromResultSet(ResultSet resultSet, String websiteName) throws SQLException {
        String title, author, description, content, website;
        Date date;
        title = resultSet.getString("Title");
        author = resultSet.getString("Author");
        description = resultSet.getString("Description");
        content = resultSet.getString("Content");
        date = resultSet.getDate("PubDate");
        return News.newNews().title(title).author(author).date(date).website(websiteName)
                .description(description).content(content).build();
    }
}
