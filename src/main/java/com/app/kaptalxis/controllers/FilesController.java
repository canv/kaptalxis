package com.app.kaptalxis.controllers;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.exceptions.InvalidFileException;
import com.app.kaptalxis.exceptions.InvalidIdException;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/book")
public class FilesController {

    @Autowired
    private FileService fileService;

    @Value("${upload.path.img}")
    private String uploadPathImg;

    @Value("${upload.path.file}")
    private String uploadPathFile;

    @GetMapping("/getFile/{id}")
    public ResponseEntity<?> getBookFile(@PathVariable("id") String id) {
        return loadFileExcHandling(id, uploadPathFile);
    }

    @GetMapping("/getImg/{id}")
    public ResponseEntity<?> getBookImg(@PathVariable("id") String id) {
        return loadFileExcHandling(id, uploadPathImg);
    }


    @PostMapping("/addFile/{id}")
    public ResponseEntity<?> addBookFile(@PathVariable("id") String id,
                                         @RequestParam("bookFile") MultipartFile bookFile
    ) {
        return saveFileExcHandling(id, uploadPathFile, bookFile, "File");
    }

    @PostMapping("/addImg/{id}")
    public ResponseEntity<?> addImgFile(@PathVariable("id") String id,
                                        @RequestParam("bookImg") MultipartFile bookImg
    ) {
        return saveFileExcHandling(id, uploadPathImg, bookImg, "Img");
    }


    private ResponseEntity<?> loadFileExcHandling(String id, String path) {
        try {
            return fileService.loadFile(id, path);

        } catch (IOException e) {
            return new ResponseEntity<>("Error reading file!",
                    HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | InvalidIdException e) {
            return new ResponseEntity<>("invalid book id - " + id,
                    HttpStatus.BAD_REQUEST);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("No book found for this ID - " + id,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> saveFileExcHandling(String id, String path, MultipartFile file, String type) {
        try {
            return fileService.saveFile(id, path, file, type);

        } catch (IOException e) {
            return new ResponseEntity<>("Error writing file - " + file.getOriginalFilename(),
                    HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | InvalidIdException e) {
            return new ResponseEntity<>("invalid book id - " + id,
                    HttpStatus.BAD_REQUEST);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("No book found for this ID - " + id,
                    HttpStatus.BAD_REQUEST);
        } catch (InvalidFileException e) {
            return new ResponseEntity<>("File is not input",
                    HttpStatus.OK);
        }
    }
}
