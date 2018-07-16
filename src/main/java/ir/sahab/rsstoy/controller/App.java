package ir.sahab.rsstoy.controller;


import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.database.DatabaseReader;
import ir.sahab.rsstoy.database.DatabaseWriter;
import ir.sahab.rsstoy.parser.FeedParser;
import ir.sahab.rsstoy.template.SiteTemplates;
import ir.sahab.rsstoy.template.Template;
import ir.sahab.rsstoy.template.TemplateFinder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RSS Toy
 */
public class App {

    private App() {
    }

    public static void main(String[] args) throws IOException, SQLException {
        App app = new App();
        SiteTemplates.init();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (!input.equals("exit")) {
            input = scanner.nextLine();
            app.inputHandler(input);
        }
    }

    private void inputHandler(String input) {
        Pattern addSite = Pattern.compile("add (\\X+) (\\S+)");
        Pattern removeSite = Pattern.compile("remove (\\X+)");
        Pattern showTop = Pattern.compile("show top (\\d+) (\\X+)");
        Pattern searchContent = Pattern.compile("search content (\\X+)");
        Pattern searchTitle = Pattern.compile("search title (\\X+)");
        Pattern showByDate = Pattern.compile("show date (\\X+) (\\w+)");
        Pattern refresh = Pattern.compile("refresh");
        Matcher matcher = addSite.matcher(input);
        if (matcher.find()) {
            addSite(matcher.group(1), matcher.group(2));
        }
        matcher = removeSite.matcher(input);
        if (matcher.find()) {
            removeSite(matcher.group(1));
        }
        matcher = showTop.matcher(input);
        if (matcher.find()) {
            showTop(matcher.group(2), Integer.parseInt(matcher.group(1)));
        }
        matcher = searchContent.matcher(input);
        if (matcher.find()) {
            searchContent(matcher.group(1));
        }
        matcher = searchTitle.matcher(input);
        if (matcher.find()) {
            searchTitle(matcher.group(1));
        }
        matcher = showByDate.matcher(input);
        if (matcher.find()) {
            showByDate(matcher.group(1), matcher.group(2));
        }
        matcher = refresh.matcher(input);
        if (matcher.find()) {
            refresh();
        }
    }

    private void addSite(String websiteName, String rssFeed) {
        try {
            Template template = TemplateFinder.findTemplate(rssFeed);
            SiteTemplates.getInstance().add(websiteName, template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeSite(String websiteName) {
        SiteTemplates.getInstance().remove(websiteName);
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

    private void showByDate(String websiteName, String dateFormat) {
        try {
            DatabaseReader reader = new DatabaseReader("guest", "1234");
            List<News> news = reader.getNewsByDate(websiteName, dateFormat);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        update();
        System.err.println("refresh successful");
    }

    private void shutDown() {
        // TODO: 7/16/18 Implement
    }

    private void printNewsList(List<News> news) {
        for (News eachNews : news)
            System.out.println(eachNews);
    }

    private void update() {
        DatabaseWriter writer = new DatabaseWriter("guest", "1234");
        LinkedHashMap<String, Template> websites = SiteTemplates.getInstance().getSiteTemplates();
        websites.forEach((key, value) -> {
            List<News> news = new FeedParser(key).getAllNews();
            for (News eachNews : news) {
                try {
                    writer.write(eachNews);
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
    }
}