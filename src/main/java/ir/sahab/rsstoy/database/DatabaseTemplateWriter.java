package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;

import java.sql.SQLException;

public class DatabaseTemplateWriter extends DatabaseStream {

    public DatabaseTemplateWriter(String userName, String password) {
        super(userName, password);
    }

    public void addTemplate(Template template) throws SQLException {
        statement.executeUpdate("INSERT INTO " + SITE_TABLE +
        " VALUES (\'" + template.getKeyValue() + "\', \'" +
        template.getFuncName() + "\', \'" + template.getDateFormat() + "\')");
    }
}
