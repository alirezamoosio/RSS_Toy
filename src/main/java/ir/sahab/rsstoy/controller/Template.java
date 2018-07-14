package ir.sahab.rsstoy.controller;

public class Template {
    private String keyValue;
    private String funcName;

    public Template(String keyValue, String keyModel) {
        this.keyValue = keyValue;
        switch (keyModel.toLowerCase()) {
            case "id":
                funcName = "getElementById";
                break;
            default:
                funcName ="getElementById"+keyModel;
                break;
        }
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
