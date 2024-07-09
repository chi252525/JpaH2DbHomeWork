package com.demo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyResponse {
    private String date;
    private BigDecimal price;

    public CurrencyResponse(String date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }
}
