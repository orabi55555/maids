package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ForeignKey;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "borrowing_record", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_BORROWING_RECORD_BOOK_AND_PATRON", columnNames = {"BOOK_ID", "PATRON_ID"})
})
public class BorrowingRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2614976822110168322L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID",nullable = false, foreignKey = @ForeignKey(name = "FK_BORROWING_RECORD_BOOK"))
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "PATRON_ID",nullable = false, foreignKey = @ForeignKey(name = "FK_BORROWING_RECORD_PATRON"))
	private Patron patron;
	
	@Column(nullable = false)
	private LocalDateTime borrowDate;
	
	@Column
	private LocalDateTime returnDate;
}
