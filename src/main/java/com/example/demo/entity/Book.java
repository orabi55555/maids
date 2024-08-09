package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_BOOK_TITLE_AND_AUTHOR", columnNames = {"TITLE", "AUTHOR"}),
        @UniqueConstraint(name = "UQ_BOOK_ISBN", columnNames = {"ISBN"})
})
public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9101102575745711071L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private long publicationYear;
	
	@Column
	private String description;
	
	@Column(length = 13, nullable = false)
	private String isbn;
}
