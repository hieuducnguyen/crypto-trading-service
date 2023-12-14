package com.hieunguyen.cryptotradingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cryptocurrency")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptocurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String name;
}
