package com.farhan.StockTracker.service;

import com.farhan.StockTracker.entity.Stock;
import com.farhan.StockTracker.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockApiClient stockApiClient;

    public Stock getStockDetails (String symbol) {
        return stockRepository.findBySymbol(symbol);
    }
    public Stock saveStock (Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStockDetails (String symbol) {
        String priceData = stockApiClient.getStockDetails(symbol);
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            stock = new Stock();
            stock.setSymbol(symbol);
        }
        updateStockFromJson(stock, priceData);
        return stockRepository.save(stock);
    }
    private void updateStockFromJson (Stock stock,String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode globalQuote = root.path("Global Quote");
            stock.setOpen(globalQuote.path("02. open").asDouble());
            stock.setHigh(globalQuote.path("03. high").asDouble());
            stock.setLow((globalQuote.path("04. low").asDouble()));
            stock.setPrice(globalQuote.path("05. price").asDouble());
            stock.setVolume(globalQuote.path("06. volume").asLong());
            stock.setLatestTradingDay(globalQuote.path("07. latest trading day").asText());
            stock.setChange(globalQuote.path("08. change").asDouble());
            stock.setChangePercentage(globalQuote.path("09. change percentage").asText());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse stock data from JSON", e);
        }
    }
}
