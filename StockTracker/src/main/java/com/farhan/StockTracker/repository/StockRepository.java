package com.farhan.StockTracker.repository;

import com.farhan.StockTracker.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findBySymbol(String symbol);
}
