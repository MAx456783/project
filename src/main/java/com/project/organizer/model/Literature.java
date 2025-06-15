package com.project.organizer.model;

import java.util.List;

public class Literature extends StudyMaterial {
    private String author;
    private int year;

    public Literature() {
        super();
    }

    public Literature(String title, String discipline, String description, 
                     List<String> tags, String author, int year) {
        super(title, discipline, description, tags);
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
