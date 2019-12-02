package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BookService {
    Book createBook(Book book);

    Book updateBook(Book book);

    Book markRead(UUID bookId);

    List<Book> getAllBooks();

    List<Book> findBooksByPhrase(String phrase);

    Page getPages(Integer size, Integer page);

    Book getBookById(UUID bookId);

    Page testFindBooksByPhrase(String phrase, Integer size, Integer page );
}
