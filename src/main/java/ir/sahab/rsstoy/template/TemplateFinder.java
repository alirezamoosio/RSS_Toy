package ir.sahab.rsstoy.template;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TemplateFinder {
    public static Template findTemplate(String rss) throws IOException {
        Document document = Jsoup.connect(rss).validateTLSCertificates(false).get();
        Elements elements = document.getElementsByTag("link");
        Document sampleDoc = null;
        Document tempDoc;
        int maxTextSize = -1;
        for (int i = 4; i < elements.size() / 4; i++) {
            Element element = elements.get(i);
            tempDoc = Jsoup.connect(element.text()).validateTLSCertificates(false).get();
            if (tempDoc.text().length() > maxTextSize) {
                maxTextSize = tempDoc.text().length();
                sampleDoc = tempDoc;
            }
        }
        ArrayList<MyElement> myElements=new ArrayList<>();
        System.out.println(sampleDoc.text());
        Elements classElements = sampleDoc.getElementsByAttribute("class");
        for (int i = 0; i < classElements.size(); i++) {
            int numberOFDot = classElements.get(i).text().split("[.]+").length;
            myElements.add(new MyElement(classElements.get(i),numberOFDot));
        }
        Collections.reverse(myElements);
        Collections.sort(myElements);
        Collections.reverse(myElements);

        for (int i = 1; i < myElements.size() / 2; i++) {
//            if (myElements.get(i).getElement().outerHtml().contains("hidden") ||myElements.get(i).getElement().outerHtml().contains("mobile") )
//                continue;
            System.out.println(myElements.get(i).getElement().text().contains(myElements.get(i+1).getElement().text()));
            System.out.println(myElements.get(i).getElement().className());
            System.out.println(myElements.get(i).getElement().text());
            System.out.println();
            System.out.println();
        }
        for (int i = 3; i < myElements.size() / 5; i++) {
            if (myElements.get(i).getElement().outerHtml().contains("mobile") )
                continue;
            if (!myElements.get(i).getElement().text().contains(myElements.get(i + 1).getElement().text())) {
                System.out.println(myElements.get(i).getElement().className());
                System.out.println(myElements.get(i).getElement().text());
                break;
            }

//            if (!classElements.get(numberOfDotArray.get(i)).text().
//                    contains(classElements.get(numberOfDotArray.get(i - 1)).text()))
//                return new Template(classElements.get(i).className(), "Class");
        }
        return null;
    }
}
class MyElement implements Comparable<MyElement>{
    private int numberOfDots;
    private Element element;

    public MyElement(Element element, int numberOfDots ) {
        this.numberOfDots = numberOfDots;
        this.element = element;
    }

    @Override
    public int compareTo(MyElement o) {
        if (numberOfDots==o.getNumberOfDots()){
            return 0;
        }else if (numberOfDots> o.getNumberOfDots()){
            return 1;
        }
        return -1;
    }


    public int getNumberOfDots() {
        return numberOfDots;
    }

    public void setNumberOfDots(int numberOfDots) {
        this.numberOfDots = numberOfDots;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}