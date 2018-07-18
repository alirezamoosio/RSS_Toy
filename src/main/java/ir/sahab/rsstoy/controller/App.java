package ir.sahab.rsstoy.controller;


import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.database.DatabaseReader;
import ir.sahab.rsstoy.database.DatabaseWriter;
import ir.sahab.rsstoy.parser.FeedParser;
import ir.sahab.rsstoy.template.SiteTemplates;
import ir.sahab.rsstoy.template.Template;
import ir.sahab.rsstoy.template.TemplateFinder;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RSS Toy
 */
public class App {
    private ExecutorService executor;

    private App() {
        executor = Executors.newFixedThreadPool(5);
    }

    public static void main(String[] args) throws IOException {
        SiteTemplates.init();
        App app = new App();
        UpdateThread updateThread = new UpdateThread(app);
        updateThread.setDaemon(true);
        updateThread.start();
        ShellFactory.createConsoleShell("rss: ", "RSSFeed", app).commandLoop();
        app.executor.shutdown();
    }

    @Command(name = "add", description = "Register a new website")
    public void addSite(@Param(name = "WebsiteName") String websiteName, @Param(name = "rssLink") String rssFeed) {
        try {
            Template template = TemplateFinder.findTemplate(rssFeed);
            SiteTemplates.getInstance().add(websiteName, template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "remove", description = "Remove a website")
    public void removeSite(@Param(name = "WebsiteName") String websiteName) {
        SiteTemplates.getInstance().remove(websiteName);
    }

    @Command(name = "showTop", description = "show top news")
    public void showTop(@Param(name = "WebsiteName") String websiteName, @Param(name = "Number") int number) {
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getLastNews(websiteName, number);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "searchContent", description = "Search in news content")
    public void searchContent(@Param(name = "query") String query) {
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByContentSearch(query);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "searchTitle", description = "Search in news titles")
    public void searchTitle(@Param(name = "query") String query) {
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByTitleSearch(query);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "show‌ByDate", description = "shows by date")
    public void showByDate(@Param(name = "WebsiteName") String websiteName, @Param(name = "Date") String dateFormat) {
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByDate(websiteName, dateFormat);
            printNewsList(news);
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "refresh", description = "fetches new messages")
    public void refresh() {
        update();
    }

    @Command(name = "forceQuit", description = "kills the app with exit(0)")
    public void forceQuit() {
        System.exit(0);
    }

    private void printNewsList(List<News> news) {
        for (News eachNews : news)
            System.out.println(eachNews);
    }

    void update() {
        LinkedHashMap<String, Template> websites = SiteTemplates.getInstance().getSiteTemplates();
        websites.forEach((key, value) -> {
            NewsFetcher fetcher = new NewsFetcher(key);
            executor.execute(fetcher);
            System.out.println("executor executed");
        });
    }
}

class NewsFetcher implements Runnable {
    private String websiteName;

    public NewsFetcher(String websiteName) {
        this.websiteName = websiteName;
    }

    @Override
    public void run() {
        List<News> news = new FeedParser(websiteName).getAllNews();
        DatabaseWriter writer = new DatabaseWriter();
        for (News eachNews : news) {
            try {
                writer.write(eachNews);
            } catch (SQLException e) {
                if (!(e instanceof SQLIntegrityConstraintViolationException))
                    e.printStackTrace();
                break;
            }
        }
    }
}

class UpdateThread extends Thread {
    private App app;

    UpdateThread(App app) {
        this.app = app;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            app.update();
        }
    }
}