package ir.sahab.rsstoy.model;

public class News {
    private String title;
    private String Author;
    private String date;
    private String description;
    private String content;

    public News(String title, String author, String date, String description) {
        this.title = title;
        Author = author;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
