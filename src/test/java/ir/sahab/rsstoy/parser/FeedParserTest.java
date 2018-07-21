package ir.sahab.rsstoy.parser;

import ir.sahab.rsstoy.template.SiteTemplates;
import ir.sahab.rsstoy.template.Template;
import org.junit.*;
import org.mockito.Mockito;

import javax.management.MalformedObjectNameException;

import java.io.IOException;

import static org.junit.Assert.*;

public class FeedParserTest {
    FeedParser feedParser=null;
    @BeforeClass
    public static void  beforeClass(){
    }

    @Before
    public void setUp() throws Exception {
        feedParser = new FeedParser("http://localhost/rss.xml");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void news() {
    }

    @Test
    public void news1() {
    }

    @Test
    public void getAllNews() {
        try {
            Assert.assertEquals(feedParser.news(1).getLink(),"http://www.entekhab.ir/fa/news/419551/کشف-لاشه-کشتی-روسی-حامل-گنجینه-طلا-به-ارزش-۱۰۰-میلیارد-پوند-بعد-از-۱۱۳-سال");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}