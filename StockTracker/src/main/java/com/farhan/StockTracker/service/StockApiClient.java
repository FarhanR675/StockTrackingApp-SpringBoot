package com.farhan.StockTracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class StockApiClient {

    private static final String API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey={apikey}";
    private final String API_KEY = "FR7G5XKY8N88243J";

    public String getStockDetails(String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{symbol}", symbol).replace("{apikey}", API_KEY);
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch data from external API for symbol " + symbol);
        }
    }
}
