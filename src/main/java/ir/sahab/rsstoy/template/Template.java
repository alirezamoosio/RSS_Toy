package ir.sahab.rsstoy.template;

import java.io.Serializable;

public class Template implements Serializable {
    private String keyValue;
    private String funcName;
    private String dateFormat;

    public Template(String keyValue, String keyModel, String dateFormat) {
        this.keyValue = keyValue;
        switch (keyModel.toLowerCase()) {
            case "id":
                funcName = "getElementById";
                break;
            default:
                funcName = "getElementById" + keyModel;
                break;
        }
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
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
