package ir.sahab.rsstoy.template;

import ir.sahab.rsstoy.database.DatabaseTemplateReader;
import ir.sahab.rsstoy.database.DatabaseTemplateWriter;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class SiteTemplates {
    private static SiteTemplates ourInstance = new SiteTemplates();

    public static SiteTemplates getInstance() {
        return ourInstance;
    }

    private LinkedHashMap<String, Template> siteTemplates = new LinkedHashMap<>();

    public Template getTemplateByName(String webstieName) {
        return siteTemplates.get(webstieName);
    }

    public void add(String websiteName, Template template) {
        try {
            DatabaseTemplateWriter writer = new DatabaseTemplateWriter("guest", "1234");
            writer.add(websiteName, template);
            siteTemplates.put(websiteName, template);
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String websiteName) {
        try {
            DatabaseTemplateWriter writer = new DatabaseTemplateWriter("guest", "1234");
            writer.remove(websiteName);
            siteTemplates.remove(websiteName);
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SiteTemplates() {
        try {
            DatabaseTemplateReader reader = new DatabaseTemplateReader("guest", "1234");
            reader.load(siteTemplates);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
