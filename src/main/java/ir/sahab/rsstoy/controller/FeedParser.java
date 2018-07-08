package ir.sahab.rsstoy.controller;

import ir.sahab.rsstoy.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FeedParser {
    private String pageURL;

    public FeedParser(String pageURL) {
        this.pageURL = pageURL;
    }

    public News[] getAllNews() {
        News[] news = null;
        try {
            Document doc = Jsoup.connect(pageURL).get();
            Elements elements = doc.select("item");
            news = new News[elements.size()];
            int counter = 0;
            for (Element element : elements) {
                news[counter++] = new News(element.select("title").toString(), element.select("author").toString()
                        , element.select("pubDate").toString(), element.select("description").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }
}
