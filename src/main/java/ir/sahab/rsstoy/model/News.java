package ir.sahab.rsstoy.model;

import java.util.Date;

public class News {
    private String title;
    private String Author;
    private Date date;
    private String description;
    private String content;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return Author;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
