package ir.sahab.rsstoy.controller;

import ir.sahab.rsstoy.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public class FeedParser {
    private String rssLink;
    private SitesTemplates sitesTemplates=SitesTemplates.getInstance();
    public FeedParser(String rssLink) {
        this.rssLink = rssLink;
    }
    public News[] newNews(){
        return null;
    }
    public News[] getAllNews() {
        return null;
    }

    private String getContent(String url) {
        return null;
    }

}
