package com.example.demo.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entity.Book;
import com.example.demo.model.dto.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookDto mapToBookDto(Book book);
	List<BookDto> mapToBookDtoList(List<Book> book);

	Book mapBookDtoToBook(BookDto bookDto);
}
