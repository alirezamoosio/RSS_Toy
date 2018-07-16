package ir.sahab.rsstoy.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TemplateFinder {
    public static Template findTemplate(String rss) throws IOException {
        Document rssDoc = Jsoup.connect(rss).validateTLSCertificates(false).get();
        Document goodPage = findMaxTextPage(rssDoc);
        String dateFormat = findDateFormat(rssDoc);
        String newsBodyAddress = findBodyAddress(goodPage);
        return new Template(newsBodyAddress, "Class", dateFormat);
    }

    private static String findBodyAddress(Document goodPage) {

        ArrayList<MyElement> myElements = new ArrayList<>();
        Elements classElements = goodPage.getElementsByAttribute("class");
        for (int i = classElements.size() - 1; i >= 0; i--) {
            if (classElements.get(i).outerHtml().contains("mobile"))
                continue;
            int numberOFDot = classElements.get(i).text().split("[.]+").length;
            myElements.add(new MyElement(classElements.get(i), numberOFDot));
        }
        Collections.sort(myElements);
        Collections.reverse(myElements);

        for (int i = 2; i < myElements.size() / 5; i++) {
            if (!myElements.get(i).getElement().text().contains(myElements.get(i + 1).getElement().text())) {
                return myElements.get(i).getElement().className();
            }
        }
        return null;
    }

    private static String findDateFormat(Document rssDoc) {
        Elements elementsOfPubDate = rssDoc.getElementsByTag("pubDate");
        return DateFomatFinder.parse(elementsOfPubDate.get(0).text());
    }

    private static Document findMaxTextPage(Document rssDoc) throws IOException {
        Elements elementsOfLinks = rssDoc.getElementsByTag("link");
        Document maxTextPage = null;
        Document tempDoc;
        int maxTextSize = -1;
        for (int i = 4; i < elementsOfLinks.size() / 4; i++) {
            Element element = elementsOfLinks.get(i);
            tempDoc = Jsoup.connect(element.text()).validateTLSCertificates(false).get();
            if (tempDoc.text().length() > maxTextSize) {
                maxTextSize = tempDoc.text().length();
                maxTextPage = tempDoc;
            }
        }
        return maxTextPage;
    }
}

class MyElement implements Comparable<MyElement> {
    private int numberOfDots;
    private Element element;

    MyElement(Element element, int numberOfDots) {
        this.numberOfDots = numberOfDots;
        this.element = element;
    }

    @Override
    public int compareTo(MyElement o) {
        if (numberOfDots == o.getNumberOfDots()) {
            return 0;
        } else if (numberOfDots > o.getNumberOfDots()) {
            return 1;
        }
        return -1;
    }


    int getNumberOfDots() {
        return numberOfDots;
    }

    void setNumberOfDots(int numberOfDots) {
        this.numberOfDots = numberOfDots;
    }

    Element getElement() {
        return element;
    }

    void setElement(Element element) {
        this.element = element;
    }
}

class DateFomatFinder {

    private static final String[] formats =
            {
                    "EEE, dd MMM yyyy HH:mm:ss Z",
                    "dd MMM yyyy HH:mm:ss Z",
                    "EEE, dd MMM yyyy HH:mm",
                    "dd MMM yyyy HH:mm",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                    "EEE, d MMM yyyy HH:mm:ss Z",
                    "EEE, dd MMM yyyy HH:mm:ss zzz",
                    "yyyy-mm-dd HH:mm:ss",
                    "yyyy-mm-dd hh:mm:ss",
                    "yyyy-MM-dd'T'HH:mm:ssZ",
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd'T'HH:mm:ssZ",
                    "yyyy-MM-dd'T'HH:mm:ss Z",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    "yyyy-MM-dd'T'hh:mm:ssXXX",
                    "dd MMM yyyy HH:mm:ss Z",
                    "MM/dd/yyyy",
            };

    public static String parse(String d) {
        if (d != null) {
            for (String parse : formats) {
                SimpleDateFormat sdf = new SimpleDateFormat(parse);
                try {
                    sdf.parse(d);
                    return parse;
                } catch (ParseException ignored) {
                }
            }
        }

        return null;
    }
}