package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketDataRepository extends JpaRepository<MarketDataEntity, Long> {
    @Query("SELECT m FROM MarketDataEntity m WHERE m.cryptoCurrency.symbol = :symbol ORDER BY m.createdAt DESC LIMIT 1")
    MarketDataEntity findFirstByCryptoCurrencyOrderByCreatedAtDesc(String symbol);
}
