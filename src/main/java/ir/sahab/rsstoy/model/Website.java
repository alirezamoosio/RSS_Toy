package ir.sahab.rsstoy.model;

public class Website {
    private String name;
    private String rssURL;

    public Website(String name, String rssURL) {
        this.name = name;
        this.rssURL = rssURL;
    }

    public String getRssURL() {
        return rssURL;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
