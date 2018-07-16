package ir.sahab.rsstoy.template;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Template implements Serializable {
    private String keyValue;
    private String funcName;
    private String dateFormatString;
    private SimpleDateFormat dateFormater;

    public Template(String attrValue, String attrModel, String dateFormat) {
        this.keyValue = attrValue;
        switch (attrModel.toLowerCase()) {
            case "id":
                funcName = "getElementById";
                break;
            default:
                funcName = "getElementsBy" + attrModel;
                break;
        }
        this.dateFormatString = dateFormat;
        this.dateFormater = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    }

    public SimpleDateFormat getDateFormater() {
        return dateFormater;
    }

    public void setDateFormater(SimpleDateFormat dateFormater) {
        this.dateFormater = dateFormater;
    }

    public String getDateFormatString() {
        return dateFormatString;
    }

    public void setDateFormatString(String dateFormatString) {
        this.dateFormatString = dateFormatString;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
}
