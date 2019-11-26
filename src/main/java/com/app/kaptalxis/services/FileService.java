package com.app.kaptalxis.services;

import com.app.kaptalxis.models.Book;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface FileService {

    boolean saveBookFile(Book book, MultipartFile file) throws IOException;

    boolean saveBookImg(Book book, MultipartFile img) throws IOException;

    ResponseEntity<Resource> getBookFile(HttpServletRequest request, String id);

    ResponseEntity<Resource> getBookImg(HttpServletRequest request, String id);
}
