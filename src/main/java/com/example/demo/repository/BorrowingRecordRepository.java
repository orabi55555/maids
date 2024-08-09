package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BorrowingRecord;

@Repository

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long>{

	BorrowingRecord findByBookIdAndPatronId(Long bookId,  Long patronId);
}
