package com.app.kaptalxis.controllers;

import com.app.kaptalxis.models.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/testdb/create_books_before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/testdb/remove_books_after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showAllBooksTest() throws Exception {
        this.mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void createBookTest() throws Exception {
//        MockHttpServletRequestBuilder multipart = multipart("/book")
//                .param("id","431c8960-047f-4d15-bccf-1397d9f00d05")
//                .param("title","testTitleThree")
//                .param("author","testAuthor")
//                .param("description","testDescriptionThree")
//                .param("isbn","testISBN")
//                .param("print_year","1113")
//                .param("read_already","false")
//                .param("img_path","test/img/path")
//                .param("file_path", "test/file/path");
        this.mockMvc.perform(post("/book").)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

}