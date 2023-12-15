package com.hieunguyen.cryptotradingservice.model.binance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinanceTickerModel {
    private String symbol;
    private Double bidPrice;
    private Double bidQty;
    private Double askPrice;
    private Double askQty;
}
