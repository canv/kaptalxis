package com.app.kaptalxis.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public interface FileService {

    UUID saveFile(String id, String uploadPath, MultipartFile bookFile) throws IOException;

    MultipartFile loadFile(String id, String uploadPath) throws IOException;
}
