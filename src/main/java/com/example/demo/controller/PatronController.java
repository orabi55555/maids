package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.PatronDto;
import com.example.demo.service.PatronService;



@RestController
@RequestMapping("/patrons")
public class PatronController {

	@Autowired
	PatronService patronService;

	// --------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<PatronDto> createPatron(@Valid @RequestBody PatronDto patronDto) {
			return new ResponseEntity<>(patronService.createPatron(patronDto), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<PatronDto> updatePatronById(@Valid @RequestBody PatronDto patronDto,
			@PathVariable("id") Long patronId) {
			return new ResponseEntity<>(patronService.updatePatronById(patronId, patronDto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PatronDto>> getAllPatrons() {
		return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PatronDto> getPatronById(@PathVariable("id") Long patronId) {
		return new ResponseEntity<>(patronService.getPatronById(patronId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<PatronDto> deletePatronById(@PathVariable("id") Long patronId) {
			patronService.deletePatronById(patronId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
