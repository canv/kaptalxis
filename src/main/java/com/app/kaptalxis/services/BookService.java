package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BookService {
    Book createBook(Book book);

    Book updateBook(Book book);

    Book markRead(UUID bookId);

    Book findBookById(UUID bookId);

    Page<Book> findBooksByPhrase(String phrase, Pageable pageRequest);

    Page<Book> getBooks(Pageable pageRequest);
}
