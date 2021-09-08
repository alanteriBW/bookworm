package com.example.api;

public class BookNotFoundException extends RuntimeException {

    BookNotFoundException(Long id) {
        super("Could not find the book " + id);
    }

}
