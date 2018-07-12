package ir.sahab.rsstoy.controller;

public class Template {
    private String keyValue;
    private String keyModel;
    private String funcName;

    public Template(String keyValue, String keyModel) {
        this.keyValue = keyValue;
        this.keyModel = keyModel;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyModel() {
        return keyModel;
    }

    public void setKeyModel(String keyModel) {
        this.keyModel = keyModel;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
}
