package ir.sahab.rsstoy.controller;


import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.database.DatabaseReader;
import ir.sahab.rsstoy.parser.FeedParser;
import ir.sahab.rsstoy.template.Template;
import ir.sahab.rsstoy.template.TemplateFinder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * RSS Toy
 */
public class App {

    private App() {
    }

    public static void main(String[] args) throws IOException, SQLException {
        Template template = TemplateFinder.findTemplate("http://www.asriran.com/fa/rss/1");
        FeedParser parser = new FeedParser("http://www.asriran.com/fa/rss/1");
        List<News> news = parser.getAllNews();
        for (News news1 : news) {
            System.out.println(news1);
        }
    }

    private void inputHandler(String input) {

    }

    private void showTop(String websiteName, int number) {
        try {
            DatabaseReader reader = new DatabaseReader("guest", "1234");
            List<News> news = reader.getLastNews(websiteName, number);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchContent(String query) {
        try {
            DatabaseReader reader = new DatabaseReader("guest", "1234");
            List<News> news = reader.getNewsByContentSearch(query);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchTitle(String query) {
        try {
            DatabaseReader reader = new DatabaseReader("guest", "1234");
            List<News> news = reader.getNewsByTitleSearch(query);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showByDate(int number, Date date) {
        // TODO: 7/16/18 Implement
    }

    private void refresh() {
        // TODO: 7/16/18 Implement
    }

    private void shutDown() {
        // TODO: 7/16/18 Implement
    }

    private void printNewsList(List<News> news) {
        for (News eachNews : news)
            System.out.println(eachNews);
    }
}