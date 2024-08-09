package com.example.demo.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PatronDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4772163069039254681L;
	
	private Long id;

	private String name;
	
	@Size(min = 11, max = 11)
	private String phoneNumber;
	
	@Email
	@Size(max = 50)
	@Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	
}
