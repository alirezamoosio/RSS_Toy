package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;
import org.junit.*;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseReaderTest {
    private static Connection connection;
    private DatabaseReader reader;

    @BeforeClass
    public static void init() throws SQLException {
        connection = DriverManager.getConnection(DatabaseStream.DB_URL, DatabaseStream.DB_USERNAME, DatabaseStream.DB_PASS);
        Statement statement = connection.createStatement();
        statement.executeUpdate("use " + DatabaseStream.DB_NAME);
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS test_website_1 " +
                " (ID INTEGER, Title VARCHAR(1000), Author VARCHAR (1000), PubDate TIMESTAMP," +
                " Description TEXT, Content TEXT, PRIMARY KEY (ID))");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS test_website_2" +
                " (ID INTEGER, Title VARCHAR(1000), Author VARCHAR (1000), PubDate TIMESTAMP," +
                " Description TEXT, Content TEXT, PRIMARY KEY (ID))");
        statement.executeUpdate("INSERT INTO test_website_1 VALUES( " + "title1".hashCode() +
                ", 'title1', 'author1', '2018-01-02 03:04:05', 'description1', 'content1')");
        statement.executeUpdate("INSERT INTO test_website_1 VALUES( " + "title2".hashCode() +
                ", 'title2', 'author2', '2018-01-03 12:03:34', 'description2', 'content2')");
        statement.executeUpdate("INSERT INTO test_website_2 VALUES( " + "title3".hashCode() +
                ", 'title3', 'author3', '2018-02-03 13:04:04', 'description3', 'content3')");
        statement.executeUpdate("INSERT INTO " + DatabaseStream.SITE_TABLE +
                " (ID, WebsiteName) VALUES (" + "test_website_1".hashCode() + ", 'test_website_1')");
        statement.executeUpdate("INSERT INTO " + DatabaseStream.SITE_TABLE +
                " (ID, WebsiteName) VALUES (" + "test_website_2".hashCode() + ", 'test_website_2')");
        statement.close();
    }

    @Before
    public void setUp() {
        reader = new DatabaseReader();
    }

    @Test
    public void getAllNews() throws SQLException, IllegalAccessException {
        List<News> list = reader.getAllNews();
        assertEquals("title1", list.get(0).getTitle());
        assertEquals("title2", list.get(1).getTitle());
        assertEquals("title3", list.get(2).getTitle());
    }

    @Test
    public void getNewsByExactTitle() throws SQLException, IllegalAccessException {
        List<News> list = reader.getNewsByExactTitle("title1");
        assertEquals("title1", list.get(0).getTitle());
    }

    @Test
    public void getNewsByTitleSearch() throws SQLException, IllegalAccessException {
        List<News> list = reader.getNewsByTitleSearch("2");
        assertEquals("title2", list.get(0).getTitle());
        list = reader.getNewsByTitleSearch("title");
        assertEquals("title1", list.get(0).getTitle());
        assertEquals("title2", list.get(1).getTitle());
        assertEquals("title3", list.get(2).getTitle());
    }

    @Test
    public void getNewsByContentSearch() throws SQLException, IllegalAccessException {
        List<News> list = reader.getNewsByContentSearch("3");
        assertEquals("title3", list.get(0).getTitle());
        list = reader.getNewsByContentSearch("content");
        assertEquals("title1", list.get(0).getTitle());
        assertEquals("title2", list.get(1).getTitle());
        assertEquals("title3", list.get(2).getTitle());
    }

    @Test
    public void getLastNews() throws SQLException, IllegalAccessException {
        List<News> list = reader.getLastNews("test_website_1", 1);
        assertEquals("title2", list.get(0).getTitle());
        assertEquals(1, list.size());
    }

    @Test
    public void getNewsByDate() throws SQLException, IllegalAccessException {
        List<News> list = reader.getNewsByDate("test_website_1", "2018-01-02");
        assertEquals("title1", list.get(0).getTitle());
        assertEquals(1, list.size());
        list = reader.getNewsByDate("test_website_1", "2017-01-01");
        assertEquals(0, list.size());
    }

    @After
    public void tearDown() throws SQLException {
        reader.close();
    }

    @AfterClass
    public static void end() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS test_website_1");
        statement.executeUpdate("DROP TABLE IF EXISTS test_website_2");
        statement.executeUpdate("DELETE FROM " + DatabaseStream.SITE_TABLE + " WHERE WebsiteName = 'test_website_1'");
        statement.executeUpdate("DELETE FROM " + DatabaseStream.SITE_TABLE + " WHERE WebsiteName = 'test_website_2'");
    }
}