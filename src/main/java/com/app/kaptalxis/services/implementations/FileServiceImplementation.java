package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.exceptions.InvalidFileException;
import com.app.kaptalxis.exceptions.InvalidIdException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ServletContext servletContext;

    @Override
    public ResponseEntity<String> saveFile(String id, String uploadPath, MultipartFile bookFile, String type)
            throws IOException, IllegalArgumentException,
            InvalidIdException, InvalidFileException, BookNotFoundException {

        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);

            if (bookFile != null && !bookFile.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String fileDir = uploadPath + "/"
                        + UUID.randomUUID().toString() + "."
                        + bookFile.getOriginalFilename();

                bookFile.transferTo(new File(fileDir));

                if (type.equals("File"))
                    book.setFilePath(fileDir);
                else if (type.equals("Img")) book.setImgPath(fileDir);
                else throw new InvalidFileException();
                bookRepository.save(book);

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("book/get" + type + "/" + book.getId())
                        .toUriString();
                return ResponseEntity.ok().body(fileDownloadUri);
            } else throw new InvalidFileException();
        } else throw new InvalidIdException();

    }

    @Override
    public ResponseEntity<InputStreamResource> loadFile(String id, String type)
            throws IOException, IllegalArgumentException,
            InvalidIdException, BookNotFoundException {

        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);

            File file = new File(book.getFilePath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            MediaType mediaType = MediaType.parseMediaType(servletContext.getMimeType(file.getName()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);

        } else throw new InvalidIdException();
    }
}
