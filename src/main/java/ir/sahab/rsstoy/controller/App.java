package ir.sahab.rsstoy.controller;


import ir.sahab.rsstoy.template.Template;
import ir.sahab.rsstoy.template.TemplateFinder;

/**
 * RSS Toy
 */
public class App {
    private App() {

    }

    public static void main(String[] args) {

    }

    private void inputHandler(String input) {
        if (input.matches("show top \\d+ \\X+"))
            System.out.println(input);
    }
}