package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTemplateWriter extends DatabaseStream {

    public DatabaseTemplateWriter(String userName, String password) {
        super();
    }

    public void add(String websiteName, Template template) throws SQLException {
        websiteName = websiteName.replace(" ", "_");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + SITE_TABLE + " VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, websiteName.hashCode());
        statement.setString(2, websiteName);
        statement.setString(3, template.getAttName());
        statement.setString(4, template.getFuncName());
        statement.setString(5, template.getRssLink());
        statement.setString(6, template.getDateFormatString());
        statement.executeUpdate();
        statement.close();
    }

    public void remove(String websiteName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS " + websiteName.replace(" ", "_"));
        statement.executeUpdate();
        statement = connection.prepareStatement("DELETE FROM " + SITE_TABLE + " WHERE WebsiteName = ?");
        statement.setString(1, websiteName.replace(" ", "_"));
        statement.executeUpdate();
    }
}
