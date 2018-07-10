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
                if (counter > 5)
                    break;
                String content = getContent(element.select("link").text());
                news[counter++] = new News(element.select("title").text(), element.select("author").text()
                        , element.select("pubDate").text(), element.select("description").text(), content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }

    private String getContent(String url) {
        System.err.println(url);
        try {
            return Jsoup.connect(url).get().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
