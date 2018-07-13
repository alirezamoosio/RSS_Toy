package ir.sahab.rsstoy.controller.database;

import ir.sahab.rsstoy.model.News;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseReader extends DataBaseStream {
    public DataBaseReader(String userName, String password) throws SQLException {
        startConnection(userName, password);
    }

    public List<News> getNewsByExactTitle(String title) throws SQLException {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Title = \'" + title + "\'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(fromResultSet(resultSet));
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
