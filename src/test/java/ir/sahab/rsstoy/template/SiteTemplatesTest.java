package ir.sahab.rsstoy.template;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SiteTemplatesTest {

    @BeforeClass
    public static void init() {
        SiteTemplates.init();
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getSiteTemplates() {
    }

    @Test
    public void getTemplateByName() {
    }

    @Test
    public void add() {
        SiteTemplates.getInstance().add("asr",new Template("salam",
                "salam","dd/MM/yyyy","asr.rss"));
        Assert.assertEquals("asr.rss", SiteTemplates.getInstance().getTemplateByName("asr").getRssLink());
    }

    @Test
    public void remove() {
    }
}