package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Patron;
import com.example.demo.model.dto.PatronDto;
import com.example.demo.model.mapper.PatronMapper;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.PatronService;

@Service
public class PatronServiceImpl implements PatronService {

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	PatronMapper patronMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Override
	@CacheEvict(value = "allPatrons", allEntries = true)

	public PatronDto createPatron(PatronDto patronDto) {
		Patron patron = new Patron();
		patron = patronMapper.mapPatronDtoToPatron(patronDto);
		patronRepository.save(patron);
		return patronMapper.mapToPatronDto(patron);
	}

	@Override
	@Caching(evict = {
		    @CacheEvict(value = "allPatrons", allEntries = true),  
		    @CacheEvict(value = "patrons", key = "#patron.id")
		})
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
    @Cacheable(value = "patrons", key = "#patronId")

	public PatronDto getPatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
	        throw new RuntimeException("Patron not found");
		Patron patronObject = patron.get();
		return patronMapper.mapToPatronDto(patronObject);
	}

	@Override
    @Cacheable(value = "allPatrons")

	public List<PatronDto> getAllPatrons() {
		List<Patron> patronList = patronRepository.findAll();
		if (patronList.isEmpty()) {
	        throw new RuntimeException("No patrons found in the list");
	    }
		return patronMapper.mapToPatronDtoList(patronList);
	}

	@Override
	@CacheEvict(value = "allPatrons", allEntries = true)

	public void deletePatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
	        throw new RuntimeException("Patron not found");
		patronRepository.deleteById(patronId);
	}

}
