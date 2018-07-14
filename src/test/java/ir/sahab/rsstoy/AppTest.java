package ir.sahab.rsstoy;

import static org.junit.Assert.assertTrue;

import ir.sahab.rsstoy.content.News;
import ir.sahab.rsstoy.database.DatabaseReader;
import ir.sahab.rsstoy.database.DatabaseWriter;
import ir.sahab.rsstoy.parser.FeedParser;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testWriteToDB() throws SQLException {
        DatabaseWriter dataBaseWriter = new DatabaseWriter("guest", "1234");
        Date date = new Date(110, 2, 3, 4, 5, 6);
        News news = News.newNews().title("اوهوی").author("هوو").website("عضر ایران").date(date)
                .description("توضیح").content("محتوا").build();
        dataBaseWriter.writeToDatabase(news);
    }

    @Test
    public void testReadFromDB() throws SQLException {
        DatabaseReader dataBaseReader = new DatabaseReader("guest", "1234");
        List<News> list = dataBaseReader.getAllNews();
        for (News news : list)
            System.out.println(news);
    }

    @Test
    public void testSearchFromDB() throws SQLException {
        DatabaseReader dataBaseReader = new DatabaseReader("guest", "1234");
        List<News> list = dataBaseReader.getNewsByTitleSearch("%ها%");
        for (News news : list) {
            System.out.println(news);
        }
    }

    @Test
    public void testGetFromSite() throws IOException {
        FeedParser feedParser = new FeedParser("http://www.asriran.com/fa/rss/1");
        News[] news = feedParser.getAllNews();

        for (News eachNews : news) {
            System.out.println(eachNews);
        }
    }
}
