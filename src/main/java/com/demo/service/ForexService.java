package com.demo.service;

import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.demo.request.ApiResponse;
import com.demo.request.CurrencyResponse;
import com.demo.request.ErrorResponse;
import com.demo.request.ForexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForexService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public ResponseEntity<ApiResponse> getRatesHistory(ForexRequest request) {
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            endDate = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(new ErrorResponse("E001", "日期格式不符"), null));
        }

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        if (startDate.isBefore(oneYearAgo) || endDate.isAfter(yesterday) || endDate.isBefore(startDate)) {
            return ResponseEntity.badRequest().body(new ApiResponse(new ErrorResponse("E001", "日期區間不符"), null));
        }

        List<ExchangeRate> rates = exchangeRateRepository.findByDateBetweenAndName(startDate.atStartOfDay(), endDate.atStartOfDay(), request.getCurrency());
        List<CurrencyResponse> currencyResponses = rates.stream()
                .map(rate -> new CurrencyResponse(rate.getDate(), rate.getPrice()))
                .collect(Collectors.toList());
        ErrorResponse success = new ErrorResponse("0000", "成功");
        return ResponseEntity.ok(new ApiResponse(success, currencyResponses));
    }
}
