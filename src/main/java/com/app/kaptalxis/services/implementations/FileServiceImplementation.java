package com.app.kaptalxis.services.implementations;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.repositories.BookRepository;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    private String saveFile(MultipartFile fileOrImg, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (uploadDir.exists()) uploadDir.mkdir();
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + fileOrImg.getOriginalFilename();

        fileOrImg.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }
}
