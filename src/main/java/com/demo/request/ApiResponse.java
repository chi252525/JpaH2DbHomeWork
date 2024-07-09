package com.demo.request;

import lombok.Data;

import java.util.List;
@Data
public class ApiResponse {
    private String code;
    private String message;
    private List<CurrencyResponse> currency;

    public ApiResponse(String code, String message, List<CurrencyResponse> currency) {
        this.code = code;
        this.message = message;
        this.currency = currency;
    }
}
