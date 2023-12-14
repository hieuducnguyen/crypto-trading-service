package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.CryptocurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptocurrencyRepository extends JpaRepository<CryptocurrencyEntity, Long> {
}
