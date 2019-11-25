package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findBooksByPhrase(String phrase) {
        if (phrase != null)
            return bookRepository
                    .findByDescriptionIgnoreCase(phrase);
        else throw new BookNotFoundException();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean markRead(Book book) {
        if (isBookInDb(book)) {
            if (!book.isReadAlready()) {
                book.setReadAlready(true);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public boolean createBook(Book book) {
        if (Objects.isNull(bookRepository.findById(book.getId()))) {
            book.setReadAlready(false);
            bookRepository.save(book);
            return true;
        } else return false;
    }

    @Override
    public boolean updateBook(Book book, String title,
                              String description, String isbn,
                              String printYear
    ) {
        if (isBookInDb(book)) {
            book.setTitle(title);
            book.setDescription(description);
            book.setIsbn(isbn);
            book.setPrintYear(Integer.parseInt(printYear));
            book.setReadAlready(false);
            return true;
        } else return false;
    }


    @Override
    public boolean deleteBook(Book book) {
        if (isBookInDb(book)) return false;
        bookRepository.delete(book);
        return true;
    }

    @Override
    public Page getPages(Integer size, Integer page) {
        if (size != null | page != null)
            return bookRepository.findAll(PageRequest.of(page, size));
        return null;
    }

    private boolean isBookInDb(Book book) {
        return bookRepository.findById(book.getId()).isPresent();
    }
}
