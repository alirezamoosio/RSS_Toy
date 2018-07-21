package ir.sahab.rsstoy.search;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

public class GoogleSearchTest {

    @Test
    public void searchQuery() {
        try {
            Assert.assertEquals("https://www.google.com/",GoogleSearch.searchQuery("google"));
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}