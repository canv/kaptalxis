package com.app.kaptalxis.controllers;

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
        this.mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": null,\"title\": \"moreTitle\", \"description\": \"moreDescription\"," +
                        "\"author\": \"moreAuthor\", \"isbn\": \"moreISBN\", \"printYear\": 2019, " +
                        "\"readAlready\": false, \"imgPath\": null, \"filePath\": null }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void markReadTest() throws Exception {
        this.mockMvc.perform(patch("/book/mark/411c8960-047f-4d15-bccf-1397d9f00d05"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void findByPhrase() throws Exception {
        this.mockMvc.perform(get("/book/findByPhrase/test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void findBookByIdTest() throws Exception {
        this.mockMvc.perform(get("/book/findById/421c8960-047f-4d15-bccf-1397d9f00d05"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }
}