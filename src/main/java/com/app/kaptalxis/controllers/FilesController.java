package com.app.kaptalxis.controllers;

import com.app.kaptalxis.exceptions.BookNotFoundException;
import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FileService fileService;

    @PostMapping("/addFile/{book}")
    public HttpStatus addBookFile(@RequestParam("id") Book book,
                                  @RequestParam("bookFile") MultipartFile bookFile
    ) throws IOException {
        if (fileService.saveBookFile(book, bookFile))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PostMapping("/addImg/{book}")
    public HttpStatus addBookImg(@RequestParam("id") Book book,
                                 @RequestParam("bookFile") MultipartFile bookImg
    ) throws IOException {
        if (fileService.saveBookFile(book, bookImg))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @GetMapping("/getFile/{book}")
    public ResponseEntity<Resource> getBookFile(@PathVariable("id") Book book,
                                                HttpServletRequest request
    ) {
        Resource resource = fileService.getBookFile(book);
        String contentType;
        try {
            contentType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new BookNotFoundException();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getImg/{book}")
    public HttpStatus getBookImg(@RequestParam("id") Book book,
                                 @RequestParam("bookFile") MultipartFile bookImg
    ) throws IOException {
        if (fileService.saveBookFile(book, bookImg))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }
}
