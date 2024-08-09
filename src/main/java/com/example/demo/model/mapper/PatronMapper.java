package com.example.demo.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entity.Patron;
import com.example.demo.model.dto.PatronDto;


@Mapper(componentModel = "spring")
public interface PatronMapper {
	
	PatronDto mapToPatronDto(Patron patron);
	List<PatronDto> mapToPatronDtoList(List<Patron> patron);

	Patron mapPatronDtoToPatron(PatronDto patronDto);

}
