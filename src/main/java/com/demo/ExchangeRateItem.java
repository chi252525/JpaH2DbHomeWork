package com.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ExchangeRateItem {

    @JsonProperty("Date")
    private String date;
    @JsonProperty("USD/NTD")
    private String usdToNtd;
}
