package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.exceptions.InvalidFileException;
import com.app.kaptalxis.exceptions.InvalidIdException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Autowired
    private BookRepository bookRepository;


    public ResponseEntity<Resource> getBookFile(HttpServletRequest request, String id) {
        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);
            return getResource(request, book.getFilePath());

        } else throw new InvalidIdException();
    }

    public ResponseEntity<Resource> getBookImg(HttpServletRequest request, String id) {
        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);
            return getResource(request, book.getImgPath());

        } else throw new InvalidIdException();
    }

    @Override
    public UUID saveFile(String id, String uploadPath, MultipartFile bookFile)
            throws IOException, IllegalArgumentException,
            InvalidIdException, InvalidFileException, BookNotFoundException {

        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);

            if (bookFile != null && !bookFile.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + bookFile.getOriginalFilename();

                bookFile.transferTo(new File(uploadPath + "/" + resultFileName));
                bookRepository.save(book);
                return book.getId();
            } else throw new InvalidFileException();
        } else throw new InvalidIdException();

    }

    @Override
    public MultipartFile loadFile(String id, String uploadPath) throws IOException {
        return null;
    }

    private ResponseEntity<Resource> getResource(HttpServletRequest request, String path) {
        try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType;
                contentType = request
                        .getServletContext()
                        .getMimeType(resource.getFile().getAbsolutePath());

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);

            } else throw new BookNotFoundException();

        } catch (IOException ex) {
            throw new BookNotFoundException();
        }
    }
}
