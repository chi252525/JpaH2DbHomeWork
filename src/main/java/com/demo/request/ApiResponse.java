package com.demo.request;

import lombok.Data;

import java.util.List;
@Data
public class ApiResponse {

    private ErrorResponse error;
    private List<CurrencyResponse> currency;

    public ApiResponse(ErrorResponse error, List<CurrencyResponse> currency) {
        this.error = error;
        this.currency = currency;
    }
}
