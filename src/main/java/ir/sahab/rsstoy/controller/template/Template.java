package ir.sahab.rsstoy.controller.template;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Template implements Serializable {
    private String keyValue;
    private String funcName;
    private DateFormat dateFormat ;
    public Template(String keyValue, String keyModel, String dateFormat) {
        this.keyValue = keyValue;
        switch (keyModel.toLowerCase()) {
            case "id":
                funcName = "getElementById";
                break;
            default:
                funcName ="getElementById"+keyModel;
                break;
        }
        this.dateFormat= new SimpleDateFormat(dateFormat);
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
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
