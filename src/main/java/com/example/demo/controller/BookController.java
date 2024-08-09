package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.BookDto;
import com.example.demo.service.BookService;


@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	// --------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
			return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<BookDto> updateBookById(@Valid @RequestBody BookDto bookDto,
			@PathVariable("id") Long bookId) {
			return new ResponseEntity<>(bookService.updateBookById(bookId, bookDto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<BookDto> deleteBookById(@PathVariable("id") Long bookId) {
			bookService.deleteBookById(bookId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
