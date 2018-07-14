package ir.sahab.rsstoy.template;

import java.util.LinkedHashMap;

public class SitesTemplates {
    private static SitesTemplates ourInstance = new SitesTemplates();

    public static SitesTemplates getInstance() {
        return ourInstance;
    }

    private LinkedHashMap<String, Template> sitesTemplate;

    public LinkedHashMap<String, Template> getSitesTemplate() {
        return sitesTemplate;
    }

    private SitesTemplates() {
    }

}
