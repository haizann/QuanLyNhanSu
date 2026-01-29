package com.example.quanlyns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlyns.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsById(Long id);
}
