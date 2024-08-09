package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.BorrowingRecord;
import com.example.demo.entity.Patron;
import com.example.demo.model.dto.BorrowingRecordDto;
import com.example.demo.model.mapper.BorrowingRecordMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRecordRepository;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.BorrowingRecordService;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;

	@Autowired
	BorrowingRecordMapper borrowingRecordMapper;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PatronRepository patronRepository;

	@Override
	@Transactional
	public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
		BorrowingRecord borrowingRecord = new BorrowingRecord();

		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new RuntimeException("Patron not found"));

		borrowingRecord.setPatron(patron);
		borrowingRecord.setBook(book);
		borrowingRecord.setBorrowDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordDto(borrowingRecord);
	}

	@Override
	public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
		BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
		if (borrowingRecord == null) {
	        throw new RuntimeException("Borrowing record not found");
	    }
		borrowingRecord.setReturnDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordDto(borrowingRecord);
	}

}
