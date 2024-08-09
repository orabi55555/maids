package com.example.demo.service;


import org.springframework.stereotype.Service;

import com.example.demo.model.dto.BorrowingRecordDto;

@Service
public interface BorrowingRecordService {

	public BorrowingRecordDto borrowBook( Long bookId,  Long patronId);
	public BorrowingRecordDto returnBook( Long bookId,  Long patronId);

}
