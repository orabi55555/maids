package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;



@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookMapper bookMapper;
	
	@Override
	public BookDto createBook(BookDto bookDto) {
		Book book = new Book();
		book = bookMapper.mapBookDtoToBook(bookDto);
		bookRepository.save(book);
		return bookMapper.mapToBookDto(book);
	}

	@Override
	@Transactional
	public BookDto updateBookById(Long bookId, BookDto bookDto) {
		Optional<Book> book = bookRepository.findById(bookId);
		Book bookObject = book.get();
		bookObject.setDescription(bookDto.getDescription());
		bookObject.setAuthor(bookDto.getAuthor());
		bookObject.setIsbn(bookDto.getIsbn());
		bookObject.setPublicationYear(bookDto.getPublicationYear());
		bookObject.setTitle(bookDto.getTitle());
		bookRepository.save(bookObject);
		return bookMapper.mapToBookDto(bookObject);
	}

	@Override
	public BookDto getBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		Book bookObject = book.get();
		return bookMapper.mapToBookDto(bookObject);
	}

	@Override
	public List<BookDto> getAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		return bookMapper.mapToBookDtoList(bookList);
	}
	
	@Override
	public void deleteBookById(Long bookId) {
		bookRepository.deleteById(bookId);
	}

}
