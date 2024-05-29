package com.farhan.StockTracker.controller;

import com.farhan.StockTracker.entity.Stock;
import com.farhan.StockTracker.service.StockApiClient;
import com.farhan.StockTracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockApiClient stockApiClient;

    @Autowired
    private StockService stockService;

    @GetMapping ("/{symbol}")
    public Stock getStockDetails (String symbol) {
        return stockService.updateStockDetails(symbol);
    }
    @PostMapping
    public Stock addStock (@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }
}
