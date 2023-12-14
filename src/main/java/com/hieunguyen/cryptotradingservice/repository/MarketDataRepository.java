package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketDataRepository extends JpaRepository<MarketDataEntity, Long> {
}
