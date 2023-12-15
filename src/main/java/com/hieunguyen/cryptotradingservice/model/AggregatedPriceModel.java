package com.hieunguyen.cryptotradingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AggregatedPriceModel {
    private String name;
    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private long timestamp;
}
