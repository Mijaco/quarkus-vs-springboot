package com.redhat.example.repository;

import com.redhat.example.entity.Calculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculatorRepository extends JpaRepository<Calculator, Long> {
    List<Calculator> findTop5ByOrderByIdDesc();
}
