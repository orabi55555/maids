package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Override
	@CacheEvict(value = "allBooks", allEntries = true)
	public BookDto createBook(BookDto bookDto) {
		Book book = new Book();
		book = bookMapper.mapBookDtoToBook(bookDto);
		bookRepository.save(book);
		return bookMapper.mapToBookDto(book);
	}

	@Override
	@Transactional
	@Caching(evict = {
		    @CacheEvict(value = "allBooks", allEntries = true),  
		    @CacheEvict(value = "books", key = "#book.id")
		})
	public BookDto updateBookById(Long bookId, BookDto bookDto) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
	        throw new RuntimeException("Book not found");
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
    @Cacheable(value = "books", key = "#bookId")
	public BookDto getBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		Book bookObject = book.get();
        logger.info("Fetching book details from the database for book ID: " + bookId);
		return bookMapper.mapToBookDto(bookObject);
	}

	@Override
    @Cacheable(value = "allBooks")
	public List<BookDto> getAllBooks() {
	    List<Book> books = bookRepository.findAll();
        logger.info("Fetching all books from the database");

	    if (books.isEmpty()) {
	        throw new RuntimeException("No books found in the list");
	    }
	    return bookMapper.mapToBookDtoList(books);
	}

	
	@Override
	@CacheEvict(value = "allBooks", allEntries = true)
	public void deleteBookById(Long id) {
	    Optional<Book> book = bookRepository.findById(id);
	    if (book.isPresent()) {
	        bookRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("Book not found");
	    }
	}


}
