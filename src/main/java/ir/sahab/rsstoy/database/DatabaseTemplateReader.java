package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DatabaseTemplateReader extends DatabaseStream {
    public DatabaseTemplateReader(String userName, String password) {
        super(userName, password);
    }

    public void load(LinkedHashMap<String, Template> siteTemplates) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM " + SITE_TABLE;
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String webisteName = resultSet.getString("WebsiteName");
            String attName = resultSet.getString("AttName");
            String funcName = resultSet.getString("FuncName");
            String dateFormat = resultSet.getString("DateFormat");
            Template template = new Template(attName, funcName, dateFormat);
            siteTemplates.put(webisteName, template);
        }
        statement.close();
    }
}
