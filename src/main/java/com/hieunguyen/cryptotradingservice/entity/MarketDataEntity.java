package com.hieunguyen.cryptotradingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "market_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MarketDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private CryptocurrencyEntity cryptoCurrency;

    @Column(nullable = false)
    private BigDecimal bidPrice;

    @Column(nullable = false)
    private BigDecimal askPrice;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;
}
