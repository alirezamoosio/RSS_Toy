package ir.sahab.rsstoy.controller;

import ir.sahab.rsstoy.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class FeedParser {
    private String pageURL;

    public FeedParser(String pageURL) {
        this.pageURL = pageURL;
    }

    public News[] getAllNews() {
        return null;
    }
}
