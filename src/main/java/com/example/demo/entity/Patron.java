package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "patron", uniqueConstraints = {@UniqueConstraint(name = "UQ_PATRON_PHONE_NUMBER", columnNames = {"phoneNumber"}),
        	@UniqueConstraint(name = "UQ_PATRON_EMAIL", columnNames = {"EMAIL"})})
public class Patron implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 255, nullable = false)
    @NotEmpty
	private String name;
	
	@Column(length = 11, nullable = false)
    @NotEmpty
	private String phoneNumber;
	
	@Column(length = 50, nullable = false)
    @NotEmpty
	private String email;

}
