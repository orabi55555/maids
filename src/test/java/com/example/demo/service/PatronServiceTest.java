package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Patron;
import com.example.demo.model.dto.PatronDto;
import com.example.demo.model.mapper.PatronMapper;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.impl.PatronServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

	@InjectMocks
	private PatronServiceImpl patronService;

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private PatronMapper patronMapper;

	private Patron patron;
	private PatronDto patronDto;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		patron = new Patron();
		patron.setId(1L);
		patron.setName("John Doe");

		patronDto = new PatronDto();
		patronDto.setName("John Doe");

		patronDto = new PatronDto();
		patronDto.setId(1L);
		patronDto.setName("John Doe");
	}

	@Test
	public void testCreatePatron_Success() {
		when(patronMapper.mapPatronDtoToPatron(any(PatronDto.class))).thenReturn(patron);
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapToPatronDto(any(Patron.class))).thenReturn(patronDto);

		PatronDto result = patronService.createPatron(patronDto);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(patronRepository, times(1)).save(any(Patron.class));
	}

	@Test
	public void testUpdatePatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
		when(patronMapper.mapPatronDtoToPatron(any(PatronDto.class))).thenReturn(patron);
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapToPatronDto(any(Patron.class))).thenReturn(patronDto);

		PatronDto result = patronService.updatePatronById(1L, patronDto);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(patronRepository, times(1)).save(any(Patron.class));
	}

	@Test
	public void testUpdatePatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> patronService.updatePatronById(1L, patronDto));
		assertEquals("Patron not found", exception.getMessage());
	}

	@Test
	public void testGetPatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
		when(patronMapper.mapToPatronDto(any(Patron.class))).thenReturn(patronDto);

		PatronDto result = patronService.getPatronById(1L);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(patronRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testGetPatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> patronService.getPatronById(1L));
		assertEquals("Patron not found", exception.getMessage());
	}

	@Test
	public void testGetAllPatrons_Success() {
		List<Patron> patrons = new ArrayList<>();
		patrons.add(patron);

		when(patronRepository.findAll()).thenReturn(patrons);
		when(patronMapper.mapToPatronDtoList(anyList())).thenReturn(List.of(patronDto));

		List<PatronDto> result = patronService.getAllPatrons();

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("John Doe", result.get(0).getName());
		verify(patronRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllPatrons_EmptyList() {
		when(patronRepository.findAll()).thenReturn(new ArrayList<>());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> patronService.getAllPatrons());
		assertEquals("No patrons found in the list", exception.getMessage());
		
	}

	@Test
	public void testDeletePatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));

		patronService.deletePatronById(1L);

		verify(patronRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void testDeletePatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> patronService.deletePatronById(1L));
		assertEquals("Patron not found", exception.getMessage());
		
	}
}
