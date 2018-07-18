package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.content.News;
import org.junit.*;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseWriterTest {

    private static Connection connection;
    private DatabaseWriter writer;
    private Statement statement;

    @BeforeClass
    public static void init() throws SQLException {
        connection = DriverManager.getConnection(DatabaseStream.DB_URL, "guest", "1234");
        Statement statement = connection.createStatement();
        statement.executeUpdate("use " + DatabaseStream.DB_NAME);
        statement.close();
    }

    @Before
    public void setUp() throws SQLException {
        statement = connection.createStatement();
        writer = new DatabaseWriter();
    }

    @Test
    public void write() throws SQLException {
        Date date = new Date(110, 1, 2, 3, 4, 5);
        News news = News.newNews().title("test title").author("test author").date(date)
                .website("test website").description("test description").content("test content").build();
        writer.write(news);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM test_website WHERE Title = 'test title'");
        while (resultSet.next()) {
            assertEquals("test title", resultSet.getString("Title"));
            assertEquals("test author", resultSet.getString("Author"));
            assertEquals("test description", resultSet.getString("Description"));
            assertEquals("test content", resultSet.getString("Content"));
        }
    }

    @Test
    public void remove() throws SQLException {
        // TODO: 7/18/18 Implement
    }

    @After
    public void tearDown() throws SQLException {
        writer.close();
        statement.executeUpdate("DROP TABLE IF EXISTS test_website");
        statement.close();
    }

    @AfterClass
    public static void dropTable() throws SQLException {
        connection.close();
    }
}