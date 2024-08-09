package com.example.demo.controller;

import com.example.demo.model.dto.BookDto;
import com.example.demo.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    private BookDto bookDto;

    @BeforeEach
    public void setup() {

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("Harper Lee");
        bookDto.setTitle("To Kill a Mockingbird");
        bookDto.setIsbn("1234567890");
        bookDto.setPublicationYear(1960);
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.createBook(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"Harper Lee\",\"title\":\"To Kill a Mockingbird\",\"isbn\":\"1234567890\",\"publicationYear\":1960}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Harper Lee"))
                .andExpect(jsonPath("$.title").value("To Kill a Mockingbird"))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.publicationYear").value(1960));

        verify(bookService, times(1)).createBook(any(BookDto.class));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(bookDto));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Harper Lee"))
                .andExpect(jsonPath("$[0].title").value("To Kill a Mockingbird"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookDto);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Harper Lee"))
                .andExpect(jsonPath("$.title").value("To Kill a Mockingbird"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testUpdateBookById() throws Exception {
        when(bookService.updateBookById(eq(1L), any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"Harper Lee\",\"title\":\"To Kill a Mockingbird\",\"isbn\":\"1234567890\",\"publicationYear\":1960}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Harper Lee"))
                .andExpect(jsonPath("$.title").value("To Kill a Mockingbird"));

        verify(bookService, times(1)).updateBookById(eq(1L), any(BookDto.class));
    }

    @Test
    public void testDeleteBookById() throws Exception {
        doNothing().when(bookService).deleteBookById(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBookById(1L);
    }
}
