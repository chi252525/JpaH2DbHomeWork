package com.demo;

import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.demo.service.ExchangeRateService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@PropertySource("classpath:application.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.demo")
public class ExchangeRateServiceTest {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private ExchangeRateClient client;
    @Autowired
    ExchangeRateRepository repository;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    public void testFetchAndSaveExchangeRate() {
        exchangeRateService.fetchAndSaveExchangeRate();
        // 查詢昨天日期的 "USD/NTD" 匯率數據 （發現當日只能查到昨天的匯率）
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<ExchangeRate> exchangeRates = repository.findByDateBetweenAndName(yesterday, yesterday, "USD/NTD");
        assertEquals(1, exchangeRates.size()); // 確保只有一條匯率數據
        ExchangeRate exchangeRate = exchangeRates.get(0);
        assertEquals("USD/NTD", exchangeRate.getName()); // 確保名稱為 "USD/NTD"

    }
}
