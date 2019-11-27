package com.app.kaptalxis.controllers;

import com.app.kaptalxis.exceptions.InvalidIdException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> showAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookService.createBook(book);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> changeEdition(@RequestBody Book book) {
        if (book != null) {
            Book saved = bookService.updateBook(book);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/mark/{id}")
    public ResponseEntity<Book> markRead(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            Book readBook = bookService.markRead(UUID.fromString(id));
            return new ResponseEntity<>(readBook, HttpStatus.OK);
        } else throw new InvalidIdException();
    }

    @GetMapping("/pages")
    public ResponseEntity<Page> bookPagination(
            @RequestParam(value = "size", defaultValue = "1") Integer size,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        Page pages = bookService.getPages(size, page);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @GetMapping("/findByPhrase/{phrase}")
    public ResponseEntity<List<Book>> findByPhrase(@PathVariable("phrase") String phrase) {
        if (phrase != null && !phrase.isEmpty()) {
            List<Book> booksByPhrase = bookService.findBooksByPhrase(phrase);
            return new ResponseEntity<>(booksByPhrase, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            Book foundBook = bookService.getBookById(UUID.fromString(id));
            return new ResponseEntity<>(foundBook, HttpStatus.OK);
        } else throw new InvalidIdException();
    }
}
