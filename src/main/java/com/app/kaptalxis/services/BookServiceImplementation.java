package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${upload.path.img}")
    private String uploadPathImg;

    @Value("${upload.path.file}")
    private String uploadPathFile;

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean markRead(Book book, boolean mark) {
        if (isBookInDb(book)) {
            book.setReadAlready(mark);
            return true;
        } else return false;
    }

    @Override
    public boolean createBook(String title, String description,
                              String author, String isbn, String printYear,
                              MultipartFile file, MultipartFile img
    ) throws IOException {
        if (Objects.isNull(bookRepository.findByTitleIgnoreCase(author))) {
            Book book = new Book();
            book.setTitle(title);
            book.setDescription(description);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPrintYear(Integer.parseInt(printYear));
            book.setReadAlready(false);
            saveBookFile(book,file);
            saveBookFile(book,img);
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
    public boolean saveBookFile(Book book, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            book.setFilePath(saveFile(file, uploadPathFile));
            return true;
        } else return false;
    }

    @Override
    public boolean saveBookImg(Book book, MultipartFile img) throws IOException {
        if (img != null && !img.getOriginalFilename().isEmpty()) {
            book.setImgPath(saveFile(img, uploadPathImg));
            return true;
        } else return false;
    }

    @Override
    public Page givePages(Integer size, Integer page) {
        if (size != null | page != null)
            return bookRepository.findAll(PageRequest.of(page, size));
        return null;
    }

    private String saveFile(MultipartFile fileOrImg, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (uploadDir.exists()) uploadDir.mkdir();
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + fileOrImg.getOriginalFilename();

        fileOrImg.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }

    private boolean isBookInDb(Book book) {
        Book bookFromDb = bookRepository.findByAuthorIgnoreCase(book.getAuthor());
        return bookFromDb != null;
    }
}
