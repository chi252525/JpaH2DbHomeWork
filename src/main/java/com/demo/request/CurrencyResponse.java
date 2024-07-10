package com.demo.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CurrencyResponse {
    private String date;
    private String price;

    public CurrencyResponse(LocalDateTime date, BigDecimal price) {
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.price = price.toString();
    }

    public CurrencyResponse(String date, String price) {
        this.date = date;
        this.price = price;
    }
}
