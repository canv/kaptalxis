package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> showAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/create")
    public HttpStatus createBook(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("author") String author,
                                 @RequestParam("isbn") String isbn,
                                 @RequestParam("printYear") String printYear,
                                 @RequestParam("file") MultipartFile bookFile,
                                 @RequestParam("img") MultipartFile bookImg
    ) throws IOException {
        boolean isBookCreated = bookService.createBook(
                title, description, author,
                isbn, printYear, bookFile, bookImg);

        if (isBookCreated) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PutMapping("/mark")
    public HttpStatus markRead(@RequestParam("mark") boolean mark,
                               @RequestParam("id") Book book
    ) {
        if (bookService.markRead(book, mark)) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PostMapping("/change")
    public HttpStatus changeEdition(@RequestParam("id") Book book,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("isbn") String isbn,
                                    @RequestParam("printYear") String printYear
    ) {
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setPrintYear(Integer.parseInt(printYear));
        book.setReadAlready(false);
        boolean isEditionChanged = bookService.updateBook(
                book, title, description, isbn, printYear);

        if (isEditionChanged) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @GetMapping("/pages")
    public Page bookPagination(
            @RequestParam(value = "size", defaultValue = "1") Integer size,
            @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        return bookService.givePages(size, page);
    }
}
