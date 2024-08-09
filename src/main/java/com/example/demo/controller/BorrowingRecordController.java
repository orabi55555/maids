package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.BorrowingRecordDto;
import com.example.demo.service.BorrowingRecordService;


@RestController
@RequestMapping
public class BorrowingRecordController {
	
	@Autowired
	BorrowingRecordService borrowingRecordService;

	// --------------------------------------------------------------------------------

	@PostMapping(path="/borrow/{bookId}/patron/{patronId}")
	public ResponseEntity<BorrowingRecordDto> createBorrowingRecord(
			@PathVariable("bookId") Long bookId,
			@PathVariable("patronId") Long patronId) {
			return new ResponseEntity<>(borrowingRecordService.borrowBook(bookId, patronId), HttpStatus.OK);
	}

	@PutMapping(path = "/return/{bookId}/patron/{patronId}")
	public ResponseEntity<BorrowingRecordDto> updateBorrowingRecordById(
			@PathVariable("bookId") Long bookId,
			@PathVariable("patronId") Long patronId) {
			return new ResponseEntity<>(borrowingRecordService.returnBook(bookId, patronId), HttpStatus.OK);
	}

}
