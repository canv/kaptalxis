package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/book")
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
        if (fileService.saveBookImg(book, bookImg))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<Resource> getBookFile(@PathVariable("id") String id,
                                                HttpServletRequest request
    ) {
        return fileService.getBookFile(request, id);
    }

    @GetMapping("/getImg/{id}")
    public ResponseEntity<Resource> getBookImg(@PathVariable("id") String id,
                                               HttpServletRequest request
    ) {
        return fileService.getBookImg(request, id);
    }
}
