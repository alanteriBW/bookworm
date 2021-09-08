package com.example.api;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Book {

    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private Long rating;


    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    public Book() {}

    @Override
    public String toString() {
        return "Book{" + "id=" + this.id + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + '}';
    }
}
