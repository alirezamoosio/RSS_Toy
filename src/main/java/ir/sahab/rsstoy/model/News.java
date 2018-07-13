package ir.sahab.rsstoy.model;

import java.sql.Timestamp;
import java.util.Date;

public class News {
    private String title;
    private String author;
    private String description;
    private String content;
    private Website website;
    private Date date;

    private News(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.description = builder.description;
        this.content = builder.content;
        this.website = builder.website;
        this.date = builder.date;
    }

    public static Builder newNews() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Website getWebsite() {
        return website;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "Author: " + author + "\n"
                + "Website: " + website + "\n"
                + "Date: " + date + "\n"
                + "Description: " + description + "\n"
                + "Content: " + content + "\n";
    }

    public static final class Builder {
        private String title;
        private String author;
        private String description;
        private String content;
        private Website website;
        private Date date;

        private Builder() {
        }

        public News build() {
            return new News(this);
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder website(Website website) {
            this.website = website;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }
    }
}
