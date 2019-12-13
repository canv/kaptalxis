package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/testdb/create_books_before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testdb/remove_books_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookServiceImplementationTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    //more checks coming soon

    @Test
    public void getBooksTest() {
        bookService.getBooks(PageRequest.of(0, 1));

        verify(bookRepository, times(1))
                .findAll(PageRequest.of(0, 1));
    }

    @Test
    public void createBookTest() {
        Book testBookOne = new Book();
        Book testBookTwo = new Book();

        bookService.createBook(testBookOne);
        bookService.createBook(testBookTwo);

        verify(bookRepository, times(2))
                .save(ArgumentMatchers.any(Book.class));
    }

    @Test
    public void findBooksByPhraseTest() {
        Book testBook = new Book();
        String testDesc = "testDescription";
        testBook.setDescription("testDesc");
        Pageable testPageable = PageRequest.of(0, 1);

        Page foundBooks = bookService.findBooksByPhrase(testDesc, testPageable);

//        Assert.assertTrue(foundBooks.hasContent(testBook));
        verify(bookRepository, times(1))
                .findByDescriptionContaining(testDesc, testPageable);
    }


    @Test
    public void markReadTest() {
        Book testBook = new Book();
        UUID testID = UUID.randomUUID();
        testBook.setReadAlready(false);
        testBook.setId(testID);

        bookService.markRead(testID);

        Book book = bookService.findBookById(testID);

//        Assert.assertTrue(book.isReadAlready());
        verify(bookRepository, times(1))
                .save(ArgumentMatchers.any(Book.class));
    }

    @Test
    public void updateBookTest() {
        Book testBook = new Book();
        UUID testID = UUID.randomUUID();
        testBook.setDescription("testDescription");
        testBook.setTitle("testTitle");
        testBook.setIsbn("testISBN");
        testBook.setPrintYear(1111);
        testBook.setId(testID);
        bookService.createBook(testBook);

        Book updateBook = new Book();
        updateBook.setDescription("updateDescription");
        updateBook.setTitle("updateTitle");
        updateBook.setIsbn("updateISBN");
        updateBook.setPrintYear(1112);
        updateBook.setId(testID);

        bookService.updateBook(updateBook);

        verify(bookRepository, times(1))
                .save(ArgumentMatchers.any(Book.class));
    }
}
