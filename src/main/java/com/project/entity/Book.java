package com.project.entity;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Component
public class Book {

    private int id;
    @NotEmpty(message = "Enter name of book")
    private String name;

    @NotEmpty(message = "Enter author of book")
    private String author;

    @Max(2022)
    private int year;

    public Book(){

    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
