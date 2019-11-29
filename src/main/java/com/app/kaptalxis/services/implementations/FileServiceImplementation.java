package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookNotFoundException;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${upload.path.img}")
    private String uploadPathImg;

    @Value("${upload.path.file}")
    private String uploadPathFile;

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
    public ResponseEntity<Resource> getBookFile(HttpServletRequest request, String id) {
        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);
            return getResource(request, book.getFilePath());

        } else throw new InvalidIdException();
    }

    @Override
    public ResponseEntity<Resource> getBookImg(HttpServletRequest request, String id) {
        if (id != null && !id.isEmpty()) {
            Book book = bookRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(BookNotFoundException::new);
            return getResource(request, book.getImgPath());

        } else throw new InvalidIdException();
    }

    @Override
    public UUID easySaveBookFile(String id, MultipartFile bookFile) throws IOException {
        Book book = bookRepository.findById(UUID.fromString(id)).orElseThrow(InvalidIdException::new);

        byte[] bytes = bookFile.getBytes();

        String resultFilePath =
                uploadPathFile + "/" +
                UUID.randomUUID().toString() + "--" +
                bookFile.getOriginalFilename();
        Path path = Paths.get(resultFilePath);
        Files.write(path, bytes);

        book.setFilePath(resultFilePath);
        bookRepository.save(book);
        return book.getId();
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

    private String saveFile(MultipartFile fileOrImg, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (uploadDir.exists()) uploadDir.mkdir();
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + fileOrImg.getOriginalFilename();

        fileOrImg.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }
}
