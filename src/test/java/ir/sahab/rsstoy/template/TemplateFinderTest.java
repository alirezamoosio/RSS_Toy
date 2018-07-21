package ir.sahab.rsstoy.template;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class TemplateFinderTest {
    static Template template1;
    static Template template2;
    @BeforeClass
    public static void beforeClass(){
        try {
            template1 =TemplateFinder.findTemplate("http://www.entekhab.ir/fa/rss/allnews");
            template2=TemplateFinder.findTemplate("http://www.asriran.com/fa/rss/allnews");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findNewsAttr1() {
        Assert.assertEquals("body col-xs-36", template1.getAttValue());
    }
    @Test
    public void findNewsAttr2() {
        Assert.assertEquals("body", template2.getAttValue());
    }
    @Test
    public void findDateFormat1(){

        Assert.assertEquals("dd MMM yyyy HH:mm:ss Z", template1.getDateFormatString());
    }

    @Test
    public void findDateFormat2(){
        Assert.assertEquals("dd MMM yyyy HH:mm:ss Z", template2.getDateFormatString());
    }
}