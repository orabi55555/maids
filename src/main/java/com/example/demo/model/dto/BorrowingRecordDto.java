package com.example.demo.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BorrowingRecordDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -347929919164549346L;

	private Long id;
	private BookDto book;
	private PatronDto patron;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

}
