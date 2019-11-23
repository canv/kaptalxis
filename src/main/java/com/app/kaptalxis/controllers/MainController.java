package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private BookService bookService;

    @GetMapping("books")
    public List<Book> registration() {
        return bookService.getAllBooks();
    }

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }


    @PostMapping("/create")
    public HttpStatus createBook(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("author") String author,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("printYear") String printYear,
                             @RequestParam("imgPath") String imgPath
    ){
        Book book = new Book();

        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrintYear(Integer.parseInt(printYear));
        book.setImgPath(imgPath);

        if(bookService.createBook(book)) return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;//change it
    }

    @PutMapping("/mark")
    public HttpStatus markRead(@RequestParam("mark") boolean mark,
                               @RequestParam("id") Book book
    ){
        book.setReadAlready(mark);
        return HttpStatus.ACCEPTED;
    }
}
