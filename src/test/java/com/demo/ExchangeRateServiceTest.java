package com.demo;

import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.demo.service.ExchangeRateService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@EnableScheduling
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@Transactional
public class ExchangeRateServiceTest {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private ExchangeRateClient client;
    @Autowired
    ExchangeRateRepository repository;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @BeforeEach
    public void setUp() {
        ExchangeRateApiResponse exchangeRateAPI = client.getExchangeRateAPI();
        String todayDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        List<ExchangeRateItem> itemsToday = exchangeRateAPI.getItemsList().stream()
                .filter(e -> e.getDate().equals(todayDate))
                .collect(Collectors.toList());

        for (ExchangeRateItem item : itemsToday) {
            ExchangeRate entity = new ExchangeRate();
            entity.setDate(item.getDate());
            entity.setPrice(new BigDecimal(item.getUsdToNtd()));
            repository.save(entity);
        }

    }

    @Test
    public void testFetchAndSaveExchangeRate() {
        exchangeRateService.fetchAndSaveExchangeRate();
    }
}
