package com.demo;

import com.demo.controller.ForexController;
import com.demo.entity.ExchangeRate;
import com.demo.repository.ExchangeRateRepository;
import com.demo.request.ApiResponse;
import com.demo.request.CurrencyResponse;
import com.demo.request.ErrorResponse;
import com.demo.request.ForexRequest;
import com.demo.service.ForexService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@PropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.demo")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ForexController.class)
public class ForexControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private ExchangeRateClient client;
    @Autowired
    ExchangeRateRepository repository;
    @Autowired
    private ForexService forexService;

    @Autowired
    private ObjectMapper objectMapper;
    private ForexRequest validRequest;
    private ForexRequest invalidDateRangeRequest;
    private List<ExchangeRateItem> itemsYesterday = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        ExchangeRateApiResponse exchangeRateAPI = client.getExchangeRateAPI();
        String yesterdayDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);

        itemsYesterday = exchangeRateAPI.getItemsList().stream()
                .filter(e -> e.getDate().equals(yesterdayDate))
                .collect(Collectors.toList());

        for (ExchangeRateItem item : itemsYesterday) {
            ExchangeRate entity = new ExchangeRate();
            entity.setDate(LocalDate.parse(item.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            entity.setPrice(new BigDecimal(item.getUsdToNtd()));
            repository.save(entity);
        }
        validRequest = new ForexRequest();
        validRequest.setStartDate(LocalDate.now().minusYears(1).format(DateTimeFormatter.BASIC_ISO_DATE));
        validRequest.setEndDate(LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE));
        validRequest.setCurrency("USD/TWD");

        invalidDateRangeRequest = new ForexRequest();
        invalidDateRangeRequest.setStartDate(LocalDate.now().minusYears(1).format(DateTimeFormatter.BASIC_ISO_DATE));
        invalidDateRangeRequest.setEndDate(LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE));
        invalidDateRangeRequest.setCurrency("USD/TWD");

    }

    @Test
    public void testForexApiSuccess() throws Exception {
        List<CurrencyResponse> currencyResponses = itemsYesterday.stream()
                .map(rate -> new CurrencyResponse(rate.getDate(), rate.getUsdToNtd()))
                .collect(Collectors.toList());

        when(forexService.getRatesHistory(validRequest)).thenReturn(
                ResponseEntity.ok(new ApiResponse(new ErrorResponse("0000", "成功"), currencyResponses))
        );

        mockMvc.perform(post("/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("0000"))
                .andExpect(jsonPath("$.error.message").value("成功"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}
