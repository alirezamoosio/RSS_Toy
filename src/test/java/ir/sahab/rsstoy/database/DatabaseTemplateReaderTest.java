package ir.sahab.rsstoy.database;

import ir.sahab.rsstoy.template.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class DatabaseTemplateReaderTest {
    private Connection connection;
    private Statement statement;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(DatabaseStream.DB_URL, "guest", "1234");
        statement = connection.createStatement();
        statement.executeUpdate("use " + DatabaseStream.DB_NAME);
        statement.executeUpdate("INSERT INTO " + DatabaseStream.SITE_TABLE + " VALUES (" +
                "test_site".hashCode() + ", 'test_site', 'attVal', 'getElementById', 'test', 'yyyy-mm-dd HH:MM:SS')");
    }

    @Test
    public void load() throws SQLException {
        DatabaseTemplateReader reader = new DatabaseTemplateReader();
        LinkedHashMap<String, Template> map = new LinkedHashMap<>();
        reader.load(map);
        assertTrue(map.containsKey("test_site"));
        assertEquals("test", map.get("test_site").getRssLink());
        reader.close();
    }

    @After
    public void tearDown() throws SQLException {
        statement.executeUpdate("DELETE FROM " + DatabaseTemplateReader.SITE_TABLE + " WHERE WebsiteName = 'test_site'");
        statement.close();
        connection.close();
    }
}