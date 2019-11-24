package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import com.app.kaptalxis.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FileService fileService;

    @PutMapping("/bookFile")
    public HttpStatus addBookFile(@RequestParam("id") Book book,
                                 @RequestParam("bookFile") MultipartFile bookFile
    ) throws IOException {
        if (fileService.saveBookFile(book,bookFile))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }

    @PutMapping("/bookImg")
    public HttpStatus addBookImg(@RequestParam("id") Book book,
                                 @RequestParam("bookFile") MultipartFile bookImg
    ) throws IOException {
        if (fileService.saveBookFile(book,bookImg))
            return HttpStatus.ACCEPTED;
        else return HttpStatus.EXPECTATION_FAILED;
    }


}
