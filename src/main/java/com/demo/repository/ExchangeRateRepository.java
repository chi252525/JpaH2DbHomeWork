package com.demo.repository;

import com.demo.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findByDateBetweenAndName(LocalDateTime startDate, LocalDateTime endDate, String currency);
}
