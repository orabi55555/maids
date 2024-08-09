package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Patron;
import com.example.demo.model.dto.PatronDto;
import com.example.demo.model.mapper.PatronMapper;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.PatronService;

@Service
public class PatronServiceImpl implements PatronService {

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	PatronMapper patronMapper;

	@Override
	public PatronDto createPatron(PatronDto patronDto) {
		Patron patron = new Patron();
		patron = patronMapper.mapPatronDtoToPatron(patronDto);
		patronRepository.save(patron);
		return patronMapper.mapToPatronDto(patron);
	}

	@Override
	public PatronDto updatePatronById(Long patronId, PatronDto patronDto) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
	        throw new RuntimeException("Patron not found");
		Patron patronObject = patron.get();
		patronObject = patronMapper.mapPatronDtoToPatron(patronDto);
		patronRepository.save(patronObject);
		return patronMapper.mapToPatronDto(patronObject);
	}

	@Override
	public PatronDto getPatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
	        throw new RuntimeException("Patron not found");
		Patron patronObject = patron.get();
		return patronMapper.mapToPatronDto(patronObject);
	}

	@Override
	public List<PatronDto> getAllPatrons() {
		List<Patron> patronList = patronRepository.findAll();
		if (patronList.isEmpty()) {
	        throw new RuntimeException("No patrons found in the list");
	    }
		return patronMapper.mapToPatronDtoList(patronList);
	}

	@Override
	public void deletePatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
	        throw new RuntimeException("Patron not found");
		patronRepository.deleteById(patronId);
	}

}
