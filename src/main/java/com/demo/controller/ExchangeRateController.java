package com.demo.controller;

import com.demo.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/fetchExchangeRate")
    public HttpEntity<Void> fetchAndSaveExchangeRate() {
        exchangeRateService.fetchAndSaveExchangeRate();
        return ResponseEntity.ok().build();
    }
}
