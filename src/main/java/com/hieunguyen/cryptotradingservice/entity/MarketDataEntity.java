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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private CryptocurrencyEntity cryptoCurrency;

    @Column(name = "bid_price", nullable = false)
    private BigDecimal bidPrice;

    @Column(name = "bid_quality", nullable = false)
    private Double bidQuality;

    @Column(name = "ask_price", nullable = false)
    private BigDecimal askPrice;

    @Column(name = "ask_quality", nullable = false)
    private Double askQuality;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}
