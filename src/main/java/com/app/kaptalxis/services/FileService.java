package com.app.kaptalxis.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {

    ResponseEntity<?> saveFile(String id, String uploadPath, MultipartFile bookFile, String type) throws IOException;

    ResponseEntity<?> loadFile(String id, String uploadPath) throws IOException;
}
