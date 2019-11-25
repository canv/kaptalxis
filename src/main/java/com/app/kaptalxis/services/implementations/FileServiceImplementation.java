package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Autowired
    BookRepository bookRepository;

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
    public Resource getBookFile(Book book) {
        try {
            Path filePath = Paths.get(book.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new BookNotFoundException();
            }
        } catch (MalformedURLException ex) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public Resource getBookImg(Book book) {
        //in progress
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
}
