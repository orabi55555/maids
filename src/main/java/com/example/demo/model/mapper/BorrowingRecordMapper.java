package com.example.demo.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entity.BorrowingRecord;
import com.example.demo.model.dto.BorrowingRecordDto;


@Mapper(componentModel = "spring", uses= {BookMapper.class, PatronMapper.class})
public interface BorrowingRecordMapper {

	BorrowingRecordDto mapToBorrowingRecordDto(BorrowingRecord borrowingRecord);
	List<BorrowingRecordDto> mapToBorrowingRecordDtoList(List<BorrowingRecord> borrowingRecord);
	

}
