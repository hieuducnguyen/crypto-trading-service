package com.hieunguyen.cryptotradingservice.repository;

import com.hieunguyen.cryptotradingservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
