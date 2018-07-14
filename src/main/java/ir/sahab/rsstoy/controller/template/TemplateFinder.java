package ir.sahab.rsstoy.controller.template;

import ir.sahab.rsstoy.controller.template.Template;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TemplateFinder {
    public static Template findTemplate(String rss) throws IOException {
        Document document = Jsoup.connect(rss).validateTLSCertificates(false).get();
        Elements elements = document.getElementsByTag("link");
        Document siteDoc = Jsoup.connect(elements.get(new Random().nextInt(elements.size())).text()).validateTLSCertificates(false).get();
        System.out.println(siteDoc.text());
        Elements classElements = siteDoc.getElementsByAttribute("class");
        ArrayList<Integer> numberOfDotArray = new ArrayList<>();
        for (int i = 0; i < classElements.size(); i++) {
            int numberOFDot = classElements.get(i).text().split("\\.").length;
            numberOfDotArray.add(numberOFDot * classElements.size() + i);
        }
        Collections.sort(numberOfDotArray);
        Collections.reverse(numberOfDotArray);
        for (int i = 0; i < numberOfDotArray.size(); i++) {
            numberOfDotArray.set(i, numberOfDotArray.get(i) % classElements.size());
        }
        for (int i = 0; i < numberOfDotArray.size() - 1; i++) {
            System.out.println(classElements.get(numberOfDotArray.get(i)).text());
            System.out.println();
            System.out.println();
//            if (!classElements.get(numberOfDotArray.get(i)).text().
//                    contains(classElements.get(numberOfDotArray.get(i - 1)).text()))
//                return new Template(classElements.get(i).className(), "Class");
        }
        return null;
    }
}

