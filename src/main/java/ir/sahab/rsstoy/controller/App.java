package ir.sahab.rsstoy.controller;


import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;
import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.database.DatabaseReader;
import ir.sahab.rsstoy.database.DatabaseTemplateReader;
import ir.sahab.rsstoy.database.DatabaseWriter;
import ir.sahab.rsstoy.parser.FeedParser;
import ir.sahab.rsstoy.template.SiteTemplates;
import ir.sahab.rsstoy.template.Template;
import ir.sahab.rsstoy.template.TemplateFinder;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RSS Toy
 */
public class App {
    private ExecutorService executor;
    final static Logger logger = Logger.getLogger(App.class);
    public static Properties properties = new Properties();

    private App() {
        executor = Executors.newFixedThreadPool(5);
    }

    public static void main(String[] args) throws IOException {
        //loading properties
        properties.load(new FileInputStream("src/main/resources/config.properties"));
        //initializing templates map
        SiteTemplates.init();
        //starting the app
        App app = new App();
        UpdateThread updateThread = new UpdateThread(app);
        updateThread.setDaemon(true);
        updateThread.start();
        ShellFactory.createConsoleShell("rss_toy: ", "", app).commandLoop();
        app.executor.shutdown();
    }

    @Command(name = "add", description = "Register a new website")
    public void addSite(@Param(name = "WebsiteName") String websiteName, @Param(name = "rssLink") String rssFeed) {
        logger.info("add command called with params " + websiteName + " " + rssFeed);
        try {
            Template template = TemplateFinder.findTemplate(rssFeed);
            SiteTemplates.getInstance().add(websiteName, template);
            System.out.println("Site added successfully");
        } catch (IOException e) {
            logger.error("Exception at App.add", e);
        } catch (IllegalArgumentException e) {
            logger.error("Exception at App.add", e);
            System.out.println("Error: Malformed URL");
        } catch (IndexOutOfBoundsException e) {
            logger.error("Exception at App.add", e);
            System.out.println("Error: Not an rss link");
        }
    }

    @Command(name = "remove", description = "Remove a website")
    public void removeSite(@Param(name = "WebsiteName") String websiteName) {
        logger.info("removeSite command called with params " + websiteName);
        try {
            SiteTemplates.getInstance().remove(websiteName);
            System.out.println("site removed successfully");
        } catch (IllegalAccessException e) {
            logger.error("Exception at App.remove", e);
            printWrongWebsite(websiteName);
        }
    }

    @Command(name = "showTop", description = "show top news")
    public void showTop(@Param(name = "WebsiteName") String websiteName, @Param(name = "Number") int number) throws IllegalAccessException {
        logger.info("showTop command called with params " + websiteName + " " + number);
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getLastNews(websiteName, number);
            printList(news);
            reader.close();
        } catch (SQLException e) {
            logger.error("Exception at App.showTop", e);
        } catch (IllegalAccessException e) {
            logger.error("Exception at App.showTop", e);
            printWrongWebsite(websiteName);
        }
    }

    @Command(name = "searchContent", description = "Search in news content")
    public void searchContent(@Param(name = "query") String query) throws IllegalAccessException {
        logger.info("searchContent command called with params " + query);
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByContentSearch(query);
            printList(news);
            reader.close();
        } catch (SQLException | IllegalAccessException e) {
            logger.error("Exception at App.searchContent");
            printList(new ArrayList<>());
        }
    }

    @Command(name = "searchTitle", description = "Search in news titles")
    public void searchTitle(@Param(name = "query") String query) throws IllegalAccessException {
        logger.info("searchTitle command called with params " + query);
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByTitleSearch(query);
            printList(news);
            reader.close();
        } catch (SQLException | IllegalAccessException e) {
            logger.error("Exception at App.searchTitle", e);
            printList(new ArrayList<>());
        }
    }

    @Command(name = "showByDate", description = "shows by date")
    public void showByDate(@Param(name = "WebsiteName") String websiteName, @Param(name = "Date") String dateFormat) {
        logger.info("showByDate command called with params " + websiteName + " " + dateFormat);
        try {
            DatabaseReader reader = new DatabaseReader();
            List<News> news = reader.getNewsByDate(websiteName, dateFormat);
            printList(news);
            reader.close();
        } catch (SQLException e) {
            logger.error("Exception at App.showByDate", e);
        } catch (IllegalAccessException e) {
            logger.error("Exception at App.showByDate", e);
            printWrongWebsite(websiteName);
        }
    }

    @Command(name = "list", description = "shows all registered websites")
    public void list() {
        logger.info("list command called");
        try {
            {
                DatabaseTemplateReader reader = new DatabaseTemplateReader();
                List<String> websites = reader.getAllWebsites();
                printList(websites);
                reader.close();
            }
        } catch (SQLException e) {
            logger.error("Exception at App.list", e);
        }
    }

    @Command(name = "refresh", description = "fetches new messages")
    public void refresh() {
        logger.info("refresh command called");
        System.out.println("refresh started");
        update();
    }

    @Command(name = "quit", description = "kills the app with exit(0)")
    public void quit() {
        logger.info("force quitting");
        System.exit(0);
    }

    private <T> void printList(List<T> objects) {
        if (objects.size() == 0)
            System.out.println("Empty list");
        else
            for (T object : objects)
                System.out.println(object);
    }

    private void printWrongWebsite(String websiteName) {
        System.out.println(websiteName + " is not registered");
    }

    void update() {
        LinkedHashMap<String, Template> websites = SiteTemplates.getInstance().getSiteTemplates();
        websites.forEach((key, value) -> {
            NewsFetcher fetcher = new NewsFetcher(key);
            executor.execute(fetcher);
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
        try {
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
                int secondsToSleep = Integer.parseInt(App.properties.getProperty("UpdateRate"));
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            App.logger.info("one minute update started");
            app.update();
        }
    }
}