package ir.sahab.rsstoy.controller.template;

import ir.sahab.rsstoy.controller.RefreshModel;
import ir.sahab.rsstoy.controller.Refreshable;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class SitesTemplates implements Refreshable ,Serializable {
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
