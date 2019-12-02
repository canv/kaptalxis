package com.app.kaptalxis.controllers;

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
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping
    public ResponseEntity<Book> changeEdition(@RequestBody Book book) {
        if (book != null) {
            return ResponseEntity.ok(bookService.updateBook(book));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/mark/{id}")
    public ResponseEntity<?> markRead(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            return ResponseEntity.ok(bookService.markRead(UUID.fromString(id)));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/pages")
    public ResponseEntity<Page> bookPagination(
            @RequestParam(value = "size", defaultValue = "1") Integer size,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        return ResponseEntity.ok(bookService.getPages(size, page));
    }

    @GetMapping("/testFindByPhrase/{phrase}")
    public ResponseEntity<Page> testFindByPhrase(@PathVariable("phrase") String phrase,
                                                 @RequestParam(value = "size", defaultValue = "1") Integer size,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (phrase != null && !phrase.isEmpty()) {
            return ResponseEntity.ok(bookService.testFindBooksByPhrase(phrase, size, page));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findByPhrase/{phrase}")
    public ResponseEntity<List<Book>> findByPhrase(@PathVariable("phrase") String phrase) {
        if (phrase != null && !phrase.isEmpty()) {
            return ResponseEntity.ok(bookService.findBooksByPhrase(phrase));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            return ResponseEntity.ok(bookService.getBookById(UUID.fromString(id)));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
