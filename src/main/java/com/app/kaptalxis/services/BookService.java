package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    boolean createBook(Book book);

    boolean updateBook(Book book, String title, String description,
                       String isbn, String printYear);

    List<Book> getAllBooks();

    List<Book> findBooksByPhrase(String phrase);

    boolean markRead(Book book);

    Page getPages(Integer size, Integer page);
}
