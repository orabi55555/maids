package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.dto.PatronDto;


@Service
public interface PatronService {
	
	public PatronDto createPatron(PatronDto patronReqModel);
	public PatronDto updatePatronById(Long patronId, PatronDto patronReqModel);
	public PatronDto getPatronById(Long patronId);
	public List<PatronDto> getAllPatrons();
	public void deletePatronById(Long patronId);
	
}
