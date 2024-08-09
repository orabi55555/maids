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
		
		Optional<Book> book = bookRepository.findById(bookId);
		borrowingRecord.setBook(book.get());

		Optional<Patron> patron = patronRepository.findById(patronId);
		borrowingRecord.setPatron(patron.get());

		borrowingRecord.setBorrowDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordDto(borrowingRecord);
	}

	@Override
	public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
		BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
		borrowingRecord.setReturnDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordDto(borrowingRecord);
	}

}
