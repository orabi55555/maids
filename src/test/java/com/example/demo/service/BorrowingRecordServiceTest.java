package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Book;
import com.example.demo.entity.BorrowingRecord;
import com.example.demo.entity.Patron;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.BorrowingRecordDto;
import com.example.demo.model.dto.PatronDto;
import com.example.demo.model.mapper.BorrowingRecordMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRecordRepository;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.impl.BorrowingRecordServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BorrowingRecordMapper borrowingRecordMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDto borrowingRecordDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book Title");

        patron = new Patron();
        patron.setId(1L);
        patron.setName("Sample Patron Name");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDateTime.now());

        borrowingRecordDto = new BorrowingRecordDto();
        borrowingRecordDto.setBook(new BookDto());
        borrowingRecordDto.getBook().setId(1L); 
        borrowingRecordDto.setPatron(new PatronDto());
        borrowingRecordDto.getPatron().setId(1L);
        borrowingRecordDto.setBorrowDate(LocalDateTime.now());
    }

    @Test
    public void testBorrowBook_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.mapToBorrowingRecordDto(any(BorrowingRecord.class))).thenReturn(borrowingRecordDto);

        BorrowingRecordDto result = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getBook().getId());
        assertEquals(1L, result.getPatron().getId());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                borrowingRecordService.borrowBook(1L, 1L));

        assertEquals("Book not found", exception.getMessage());
    }


    @Test
    public void testBorrowBook_PatronNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                borrowingRecordService.borrowBook(1L, 1L));

        assertEquals("Patron not found", exception.getMessage());
    }

    @Test
    public void testReturnBook_Success() {
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.mapToBorrowingRecordDto(any(BorrowingRecord.class))).thenReturn(borrowingRecordDto);

        BorrowingRecordDto result = borrowingRecordService.returnBook(1L, 1L);

        assertNotNull(result);
        assertNotNull(result.getBorrowDate());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    public void testReturnBook_RecordNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(null);

        RuntimeException  exception = assertThrows(RuntimeException .class, () ->
                borrowingRecordService.returnBook(1L, 1L));

        assertEquals("Borrowing record not found", exception.getMessage());
    }
}
