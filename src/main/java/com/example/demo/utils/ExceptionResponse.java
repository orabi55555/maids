package com.example.demo.utils;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionResponse {
	private LocalDateTime timestamp;
	private String message;
	private String path;
	private String error;


	public ExceptionResponse(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();

	}

	public ExceptionResponse(String message, String path, String error) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.path = path;
		this.error = error;
	}
	
	public ExceptionResponse(String message,String error) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.error = error;
	}
}
