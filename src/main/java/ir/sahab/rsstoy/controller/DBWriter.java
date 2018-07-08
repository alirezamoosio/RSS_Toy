package ir.sahab.rsstoy.controller;

import ir.sahab.rsstoy.model.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWriter {
    private String url;
    private String username;
    private String pass;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBWriter(String url, String username, String pass) {
        this.url = url;
        this.username = username;
        this.pass = pass;
    }

    public void writeNews(News news) {

    }
}
