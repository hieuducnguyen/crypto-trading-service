package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
