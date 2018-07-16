package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTemplateWriter extends DatabaseStream {

    public DatabaseTemplateWriter(String userName, String password) {
        super(userName, password);
    }

    public void add(String websiteName, Template template) throws SQLException {
        websiteName = websiteName.replace(" ", "_");
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO " + SITE_TABLE +
        " VALUES (\'" + websiteName.hashCode() + "\', \'" + websiteName + "\', \'" + template.getAttName() + "\', \'" +
        template.getFuncName() + "\', \'" + template.getRssLink() + "\', \'" + template.getDateFormatString() + "\')");
        statement.close();
    }

    public void remove(String websiteName) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS " + websiteName.replace(" ", "_"));
        statement.executeUpdate("DELETE FROM " + SITE_TABLE + " WHERE WebsiteName = \'"
                + websiteName.replace(" ", "_") + "\'");
        statement.close();
    }
}
