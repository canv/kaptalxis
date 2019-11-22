package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private BookService bookService;

    @GetMapping("/creation")
    public String registration() {
        return "creation";
    }

    @PostMapping("/creation")
    public String createBook(@RequestParam("title") String title,
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

        if(bookService.createBook(book)) return "redirect:/";
        else return "redirect:/error1";
    }
}
