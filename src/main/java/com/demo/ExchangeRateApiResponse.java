package com.demo;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeRateApiResponse {
   List<ExchangeRateItem> itemsList;
}
