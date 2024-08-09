package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Patron;

@Repository

public interface PatronRepository extends JpaRepository<Patron, Long>{

}
