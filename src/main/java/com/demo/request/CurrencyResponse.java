package com.demo.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CurrencyResponse {
    private LocalDate date;
    private BigDecimal price;

    public CurrencyResponse(LocalDate date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }
}
