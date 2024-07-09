package com.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

@Component
public class ExchangeRateClient {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateClient.class);

    public static final String PATH_GET = "/v1/DailyForeignExchangeRates";
    @Autowired
    private ExchangeRateProperties properties;
    @Autowired
    private  RestTemplate restTemplate;

    public ExchangeRateApiResponse getExchangeRateAPI() {
        String url = "https://openapi.taifex.com.tw" + PATH_GET;

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/octet-stream");

        // Build URI with query parameters
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode()
                .toUri();

        // Disable SSL certificate verification
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ExchangeRateItem[] exchangeRateItems = objectMapper.readValue(responseBody, ExchangeRateItem[].class);
            ExchangeRateApiResponse apiResponse = new ExchangeRateApiResponse();
            apiResponse.setItemsList(Arrays.asList(exchangeRateItems));
            return apiResponse;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}