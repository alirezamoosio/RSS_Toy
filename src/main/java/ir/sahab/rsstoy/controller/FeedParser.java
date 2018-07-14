package ir.sahab.rsstoy.controller;

import ir.sahab.rsstoy.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedParser {
    private String rssLink;
    private SitesTemplates sitesTemplates = SitesTemplates.getInstance();
    private Document document = null;
    private DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    
    public FeedParser(String rssLink) {
        this.rssLink = rssLink;

        try {
            Document document = Jsoup.connect(rssLink).validateTLSCertificates(false).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public News news(int number) throws IOException {
        Element e = document.getElementsByTag("item").get(number);

        return news(e);
    }

    public News news(Element e) {
        Date pubDate=null;
        String link=e.getElementsByTag("link").text();
        String title=e.getElementsByTag("title").text();
        String author=e.getElementsByTag("author").text();
        String description=e.getElementsByTag("description").text();
        try {

            pubDate = formatter.parse(e.getElementsByTag("pubDate").text());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return new News(title,author,description,getContent(link),link.split("/")[2],link,pubDate);
    }


    public News[] getAllNews() throws IOException {
        Elements elements = document.getElementsByTag("item");
        ArrayList<News> newses = new ArrayList<>();
        for (Element e : elements) {
            newses.add(news(e));
        }        return newses.toArray(new News[0]);
    }

    private String getContent(String url) {
        String key = url.split("/")[2];
        Template template = sitesTemplates.getSitesTemplate().get(key);
        String str = null;
        try {
            Document document = Jsoup.connect(url).validateTLSCertificates(false).get();
            Method method = Document.class.getMethod(template.getFuncName(), String.class);
            Object o = method.invoke(document, template.getKeyValue());
            if (o instanceof Elements) {
                str = ((Elements) o).text();

            } else if (o instanceof Element) {
                str = ((Element) o).text();

            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
