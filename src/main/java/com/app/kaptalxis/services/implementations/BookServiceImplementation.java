package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookCopyFoundException;
import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findBooksByPhrase(String phrase) {
        return bookRepository
                .findByDescriptionIgnoreCase(phrase);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book markRead(UUID bookId) {
        Book readBook = getBookById(bookId);
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
        Book updateBook = getBookById(book.getId());
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
    public Page getPages(Integer size, Integer page) {
        if (size != null | page != null)
            return bookRepository.findAll(PageRequest.of(page, size));
        return null;
    }

    @Override
    public Book getBookById(UUID bookId) {
        return bookRepository
                .findById(bookId)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Page testFindBooksByPhrase(String phrase, Integer size, Integer page) {
        return bookRepository.findByDescription(phrase,PageRequest.of(page, size));
    }
}
