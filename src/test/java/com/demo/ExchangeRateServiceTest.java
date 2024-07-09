package com.demo;

import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.demo.service.ExchangeRateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    private ExchangeRateService exchangeRateService;

    @Test
    public void testFetchAndSaveExchangeRate() {
        // 執行定時任務方法
        exchangeRateService.fetchAndSaveExchangeRate();

        // 驗證是否成功保存 ExchangeRate 記錄
        // 從資料庫中讀取最後一條插入的 ExchangeRate 記錄
//        ExchangeRate savedExchangeRate = exchangeRateRepository.findAll();

//        assertNotNull(savedExchangeRate);
//        assertNotNull(savedExchangeRate.getId());
    }
}
