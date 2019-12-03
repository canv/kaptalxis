package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<Book>> showAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(bookService.getBooks(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping // title, description, isbn, printYear
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

    @GetMapping("/findByPhrase/{phrase}")
    public ResponseEntity<Page<Book>> findByPhrase(@PathVariable("phrase") String phrase,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size
    ) {
        if (phrase != null && !phrase.isEmpty()) {
            return ResponseEntity.ok(bookService.findBooksByPhrase(phrase, PageRequest.of(page, size)));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            return ResponseEntity.ok(bookService.findBookById(UUID.fromString(id)));
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
