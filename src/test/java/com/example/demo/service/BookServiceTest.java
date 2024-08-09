package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Book;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Generic Book Title");
        bookDto.setAuthor("Author Name");
        bookDto.setIsbn("9876543210");
        bookDto.setPublicationYear(2023L);

        book = new Book();
        book.setId(1L);
        book.setTitle("Generic Book Title");
        book.setAuthor("Author Name");
        book.setIsbn("9876543210");
        book.setPublicationYear(2023L);
    }

    @Test
    void createBook_success() {
        when(bookMapper.mapBookDtoToBook(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.createBook(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapBookDtoToBook(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.updateBookById(1L, bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.updateBookById(1L, bookDto);
        });

        assertEquals("No value present", exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.getBookById(1L);
        });

        assertEquals("No value present", exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllBooks_success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapToBookDtoList(anyList())).thenReturn(List.of(bookDto));

        List<BookDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_noBooksFound() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.getAllBooks();
        });

        assertEquals("No books found in the list", exception.getMessage());
        verify(bookRepository, times(1)).findAll();
    }



    @Test
    void deleteBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBookById(1L);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).deleteById(anyLong());
    }

}
