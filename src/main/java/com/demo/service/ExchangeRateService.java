package com.demo.service;

import com.demo.ExchangeRateClient;
import com.demo.ExchangeRateApiResponse;
import com.demo.ExchangeRateItem;
import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    @Autowired
    private ExchangeRateClient client;
    @Autowired
    ExchangeRateRepository repository;

    public ExchangeRate save(ExchangeRate exchangeRate) {
        return repository.save(exchangeRate);
    }

    @Scheduled(cron = "0 0 18 * * ?") // 每天下午 6 點觸發
    public void fetchAndSaveExchangeRate() {
        ExchangeRateApiResponse exchangeRateAPI = client.getExchangeRateAPI();
        log.info("exchangeRateAPI"+new Gson().toJson(exchangeRateAPI));
        String todayDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        List<ExchangeRateItem> itemsToday = exchangeRateAPI.getItemsList().stream()
                .filter(e -> e.getDate().equals(todayDate))
                .collect(Collectors.toList());

        for (ExchangeRateItem item : itemsToday) {
            ExchangeRate entity = new ExchangeRate();
            entity.setDate(item.getDate());
            entity.setPrice(new BigDecimal(item.getUsdToNtd()));
            save(entity);
        }

    }

}
