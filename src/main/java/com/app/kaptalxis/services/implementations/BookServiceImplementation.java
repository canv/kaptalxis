package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookCopyFoundException;
import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.exceptions.InvalidSearchWordException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> findBooksByPhrase(String phrase, Pageable pageRequest) {
        if (phrase != null && !phrase.isEmpty()) {
            return bookRepository.findByDescriptionContaining(phrase, pageRequest);
        } else throw new InvalidSearchWordException();
    }

    @Override
    public Page<Book> getBooks(Pageable pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public Book markRead(UUID bookId) {
        Book readBook = findBookById(bookId);
        if (readBook != null) {
            readBook.setReadAlready(true);
            bookRepository.save(readBook);
            return readBook;
        } else throw new BookNotFoundException();
    }

    @Override
    public Book createBook(Book book) {
        if (book == null) throw new BookNotFoundException();
        boolean isNotPresentByTitle = bookRepository
                .findByTitleIgnoreCase(book.getTitle()) == null;
        if (isNotPresentByTitle) {
            book.setReadAlready(false);
            bookRepository.save(book);
            return book;
        } else throw new BookCopyFoundException();
    }

    @Override
    public Book updateBook(Book book) {
        Book updateBook = findBookById(book.getId());
        if (updateBook != null) {
            String updateTitle = book.getTitle();
            if (updateTitle != null)
                updateBook.setTitle(updateTitle);
            String updateDescription = book.getDescription();
            if (updateDescription != null)
                updateBook.setDescription(updateDescription);
            String updateIsbn = book.getIsbn();
            if (updateIsbn != null)
                updateBook.setIsbn(updateIsbn);
            int updatePrintYear = book.getPrintYear();
            if (updatePrintYear != updateBook.getPrintYear())
                updateBook.setPrintYear(updatePrintYear);
            updateBook.setReadAlready(false);
            bookRepository.save(updateBook);
            return updateBook;
        } else throw new BookNotFoundException();
    }

    @Override
    public Book findBookById(UUID bookId) {
        return bookRepository
                .findById(bookId)
                .orElseThrow(BookNotFoundException::new);
    }
}
