package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;
import org.junit.*;

import javax.xml.crypto.Data;
import java.sql.*;

import static org.junit.Assert.*;

public class DatabaseTemplateWriterTest {
    private static Connection connection;
    private Statement statement;
    private DatabaseTemplateWriter writer;

    @BeforeClass
    public static void init() throws SQLException {
        connection = DriverManager.getConnection(DatabaseReader.DB_URL, DatabaseStream.DB_USERNAME, DatabaseStream.DB_PASS);
        Statement statement = connection.createStatement();
        statement.executeUpdate("use " + DatabaseReader.DB_NAME);
        statement.close();
    }

    @Before
    public void setUp() throws SQLException {
        statement = connection.createStatement();
        writer = new DatabaseTemplateWriter();
    }

    @Test
    public void add() throws SQLException {
        Template template = new Template("attVal", "attMod", "yyyy-mm-dd HH:MM:SS", "test");
        String websiteName = "test site";
        writer.add(websiteName, template);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Websites WHERE WebsiteName = 'test_site'");
        resultSet.next();
        assertEquals("test_site", resultSet.getString("WebsiteName"));
        assertEquals("attVal", resultSet.getString("AttName"));
        assertEquals("yyyy-mm-dd HH:MM:SS", resultSet.getString("DateFormat"));
        assertEquals("test", resultSet.getString("RSSLink"));
        statement.executeUpdate("DELETE FROM " + DatabaseStream.SITE_TABLE + " WHERE WebsiteName = 'test_site'");
    }

    @Test
    public void remove() throws SQLException, IllegalAccessException {
        statement.executeUpdate("INSERT INTO " + DatabaseStream.SITE_TABLE + " (ID, WebsiteName) " +
                "VALUES (" + "test_site".hashCode() + ", 'test_site')");
        writer.remove("test site");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Websites WHERE WebsiteName = 'test site'");
        assertFalse(resultSet.next());
    }

    @After
    public void tearDown() throws SQLException {
        writer.close();
        statement.close();
    }

    @AfterClass
    public void end() throws SQLException {
        connection.close();
    }

}