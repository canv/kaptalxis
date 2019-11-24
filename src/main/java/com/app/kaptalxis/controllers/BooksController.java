package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<Book> showAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/create")
    public HttpStatus createBook(@Valid Book book) {
        if (bookService.createBook(book)) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PatchMapping("/mark")
    public HttpStatus markRead(@RequestParam("id") Book book) {
        if (bookService.markRead(book)) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PutMapping("/change")
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
        return bookService.getPages(size, page);
    }
}
