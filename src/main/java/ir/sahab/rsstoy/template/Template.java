package ir.sahab.rsstoy.template;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Template implements Serializable {
    private String attName;
    private String funcName;
    private String dateFormatString;
    private String rssLink;
    private SimpleDateFormat dateFormatter;

    public Template(String attrValue, String attrModel, String dateFormat, String rssLink) {
        this.attName = attrValue;
        this.rssLink = rssLink;
        switch (attrModel.toLowerCase()) {
            case "id":
                funcName = "getElementById";
                break;
            default:
                funcName = "getElementsBy" + attrModel;
                break;
        }
        this.dateFormatString = dateFormat;
        this.dateFormatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    }

    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public String getDateFormatString() {
        return dateFormatString;
    }

    public void setDateFormatString(String dateFormatString) {
        this.dateFormatString = dateFormatString;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getRssLink() {
        return rssLink;
    }
}
