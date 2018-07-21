package ir.sahab.rsstoy.template;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class TemplateTest {
    static Template template=new Template("salam1","Tag","dd/yyyy/MM","www.rss");

    @Test(expected = ParseException.class)
    public void getDateFormatter() throws ParseException {
        template.getDateFormatter().parse("12.06.2001");
    }
    @Test
    public void getDateFormatter2() throws ParseException {
        template.getDateFormatter().parse("12/2006/2");
    }

    @Test
    public void getFuncName() {
        Assert.assertEquals("getElementByTag",template.getFuncName());
    }

}