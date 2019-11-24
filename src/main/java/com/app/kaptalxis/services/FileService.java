package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {

    boolean saveBookFile(Book book, MultipartFile file) throws IOException;

    boolean saveBookImg(Book book, MultipartFile img) throws IOException;
}
