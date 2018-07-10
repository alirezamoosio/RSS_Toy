package ir.sahab.rsstoy.model;

public class News {
    private String title;
    private String author;
    private String date;
    private String description;
    private String content;

    public News(String title, String author, String date, String description, String content) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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
        this.author = author;
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

    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "Author: " + author + "\n"
                + "Date: " + date + "\n"
                + "Description: " + description + "\n"
                + "Content: " + content + "\n";
    }
}
