package com.example.demo.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BookDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3110238236072302265L;

	private Long id;
	
    @NotEmpty
	private String title;
    
    @NotEmpty
	private String author;
	
	private long publicationYear;
	
	private String description;

	@Size(max = 13)
	@Pattern(regexp = "^(?:\\d{9}[\\dX]|\\d{13})$")
    @NotEmpty
	private String isbn;

}
