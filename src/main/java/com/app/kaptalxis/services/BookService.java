package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBooks();

    List<Book> findBooksByTitle(String title);

    boolean markRead(Book book, boolean mark);

    boolean deleteBook(Book book);

    boolean saveBookFile(Book book, MultipartFile file) throws IOException;

    boolean saveBookImg(Book book, MultipartFile img) throws IOException;

    Page givePages(Integer size, Integer page);

    boolean createBook(String title, String description, String author,
                       String isbn, String printYear,
                       MultipartFile file, MultipartFile img) throws IOException;

    boolean updateBook(Book book, String title, String description,
                       String isbn, String printYear);
}
