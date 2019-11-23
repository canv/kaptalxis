package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> findBooksByTitle(String title);
    boolean createBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(Book book);
    List<Book> getAllBooks();
}
