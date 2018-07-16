package ir.sahab.rsstoy.parser;

import ir.sahab.rsstoy.template.SiteTemplates;
import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.template.Template;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedParser {
    private String rssLink;
    private SiteTemplates siteTemplates = SiteTemplates.getInstance();
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

    public News news(Element element) {
        String link = element.getElementsByTag("link").text();
        String title = element.getElementsByTag("title").text();
        String author = element.getElementsByTag("author").text();
        String description = element.getElementsByTag("description").text();
        String website = link.split("/")[2];
        return new News(title, author, description, getContent(link), website, link, getDate(website, element.getElementsByTag("pubDate").get(0).text()));
    }

    private Date getDate(String website, String e)  {
        try {
            return  siteTemplates.getTemplateByName(website).getDateFormater().parse(e);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public List<News> getAllNews()  {
        Elements elements = document.getElementsByTag("item");
        ArrayList<News> newses = new ArrayList<>();
        for (Element e : elements) {
            newses.add(news(e));
        }
        return newses;
    }

    private String getContent(String url) {
        String website = url.split("/")[2];
        Template template = siteTemplates.getTemplateByName(website);
        String newsContent = null;
        try {
            Document document = Jsoup.connect(url).validateTLSCertificates(false).get();
            Method method = Document.class.getMethod(template.getFuncName(), String.class);
            Object o = method.invoke(document, template.getKeyValue());
            if (o instanceof Elements) {
                newsContent = ((Elements) o).text();

            } else if (o instanceof Element) {
                newsContent = ((Element) o).text();

            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return newsContent;
    }
}
