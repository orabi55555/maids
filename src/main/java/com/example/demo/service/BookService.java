package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.dto.BookDto;

@Service
public interface BookService {

	public BookDto createBook(BookDto bookReqModel);
	public BookDto updateBookById(Long bookId, BookDto bookReqModel);
	public BookDto getBookById(Long bookId);
	public List<BookDto> getAllBooks();
	public void deleteBookById(Long bookId);

}
