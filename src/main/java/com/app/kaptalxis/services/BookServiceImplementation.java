package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookServiceImplementation implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public boolean createBook(Book book) {
        Book bookFromDb = bookRepository.findByAuthorIgnoreCase(book.getAuthor());
        if(bookFromDb != null) return false;
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean updateBook(Book book) {
        Book bookFromDb = bookRepository.findByAuthorIgnoreCase(book.getAuthor());
        if(bookFromDb != null) return false;
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean deleteBook(Book book) {
        Book bookFromDb = bookRepository.findByAuthorIgnoreCase(book.getAuthor());
        if(bookFromDb != null) return false;
        bookRepository.delete(book);
        return true;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
