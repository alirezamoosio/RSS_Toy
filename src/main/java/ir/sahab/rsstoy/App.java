package ir.sahab.rsstoy;

import ir.sahab.rsstoy.controller.FeedParser;
import ir.sahab.rsstoy.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;

/**
 * RSS Toy
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        FeedParser feedParser = new FeedParser("https://fararu.com/fa/rss/1");
//        News[] news = feedParser.getAllNews();
//        for (News eachNews : news) {
//            System.out.println(eachNews);
//        }
        try {
            Document document = Jsoup.connect("http://www.tabnak.ir/fa/news/814438/%DA%A9%D8%B1%D9%88%D8%A8%DB%8C-%D8%B2%D8%AF%D8%A7%DB%8C%DB%8C-%D9%88-%DA%A9%D9%88%D8%AF%D8%AA%D8%A7-%D8%AF%D8%B1-%D8%A7%D8%B9%D8%AA%D9%85%D8%A7%D8%AF-%D9%85%D9%84%DB%8C-%DB%8C%D8%A7-%D8%AA%D8%AC%D8%B2%DB%8C%D9%87-%D8%AD%D8%B2%D8%A8-%D8%B4%DB%8C%D8%AE-%D8%A7%D8%B5%D9%84%D8%A7%D8%AD%D8%A7%D8%AA").get();
            Elements elements = document.getElementsByClass("body");
            FileOutputStream fileOutputStream = new FileOutputStream("/home/mousavi/Desktop/log.txt");
            Formatter formatter = new Formatter(fileOutputStream);
            for (Element element : elements) {
                formatter.format(element.text());
                formatter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
