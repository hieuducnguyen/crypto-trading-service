package com.hieunguyen.cryptotradingservice.entity;

import com.hieunguyen.cryptotradingservice.enums.DataSourceEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Table(name = "market_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol", nullable = false)
    private CryptocurrencyEntity cryptocurrencyEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataSourceEnum source;

    @Column(nullable = false)
    private BigDecimal bidPrice;

    @Column(nullable = false)
    private BigDecimal bidQty;

    @Column(nullable = false)
    private BigDecimal askPrice;

    @Column(nullable = false)
    private BigDecimal askQty;

    @CreationTimestamp
    @Column(nullable = false)
    private Date timestamp;
}
