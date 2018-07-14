package ir.sahab.rsstoy.controller;

import java.util.LinkedHashMap;

public class SitesTemplates implements Refreshable {
    private static SitesTemplates ourInstance = new SitesTemplates();
    public static SitesTemplates getInstance() {
        return ourInstance;
    }
    private  LinkedHashMap<String,Template> sitesTemplate=new LinkedHashMap<>();

    public LinkedHashMap<String, Template> getSitesTemplate() {
        return sitesTemplate;
    }

    private SitesTemplates() {

    }
    public void refresh(RefreshModel refreshModel){
        refreshModel.refresh(this);

    }
}
