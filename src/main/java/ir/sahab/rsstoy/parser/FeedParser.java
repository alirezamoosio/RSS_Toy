package ir.sahab.rsstoy.parser;

import ir.sahab.rsstoy.template.SitesTemplates;
import ir.sahab.rsstoy.content.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FeedParser {
    private String rssLink;
    private SitesTemplates sitesTemplates = SitesTemplates.getInstance();
    private Document document = null;

    public FeedParser(String rssLink) {
        this.rssLink = rssLink;

        try {
            document = Jsoup.connect(rssLink).validateTLSCertificates(false).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public News news(int number) throws IOException {
        Element e = document.getElementsByTag("item").get(number);

        return news(e);
    }

    public News news(Element e) {
        String link = e.getElementsByTag("link").text();
        String title = e.getElementsByTag("title").text();
        String author = e.getElementsByTag("author").text();
        String description = e.getElementsByTag("description").text();
        String website = link.split("/")[2];
        return new News(title, author, description, getContent(link), website, link, getDate(website, e));
    }

    private Date getDate(String website, Element e) {
//        try {
//            return new SimpleDateFormat(sitesTemplates.getSitesTemplate().get(website).getDateFormat()).parse(e.getElementsByTag("pubDate").text());
//        } catch (ParseException e1) {
//            e1.printStackTrace();
//        }
        return new Date(117, 10, 2, 3, 4, 5);
    }


    public News[] getAllNews() throws IOException {
        Elements elements = document.getElementsByTag("item");
        ArrayList<News> newses = new ArrayList<>();
        for (Element e : elements) {
            newses.add(news(e));
        }
        return newses.toArray(new News[0]);
    }

    private String getContent(String url) {
//        String key = url.split("/")[2];
//        Template template = sitesTemplates.getSitesTemplate().get(key);
//        String str = null;
//        try {
//            Document document = Jsoup.connect(url).validateTLSCertificates(false).get();
//            Method method = Document.class.getMethod(template.getFuncName(), String.class);
//            Object o = method.invoke(document, template.getKeyValue());
//            if (o instanceof Elements) {
//                str = ((Elements) o).text();
//
//            } else if (o instanceof Element) {
//                str = ((Element) o).text();
//
//            }
//
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
//            e.printStackTrace();
//        }
//        return str;
        return "TBD";
    }
}
