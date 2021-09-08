package com.example.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/books")
    CollectionModel<EntityModel<Book>> all() {

        List<EntityModel<Book>> books = repo.findAll().stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).one(book.getId())).withSelfRel(),
                        linkTo(methodOn(BookController.class).all()).withRel("books")))
                .collect(Collectors.toList());
        return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @GetMapping("/books/{id}")
    EntityModel<Book> one(@PathVariable Long id) {

        Book book = repo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).one(id)).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"));
    }

    @PostMapping("/books")
    Book newEmployee(@RequestBody Book newBook) {
        return repo.save(newBook);
    }

    @PutMapping("/books/{id}")
    Book replaceEmployee(@RequestBody Book newBook, @PathVariable Long id) {

        return repo.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    return repo.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repo.save(newBook);
                });
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
