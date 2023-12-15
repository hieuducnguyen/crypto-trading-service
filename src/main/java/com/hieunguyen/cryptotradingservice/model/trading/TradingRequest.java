package com.hieunguyen.cryptotradingservice.model.trading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TradingRequest {
    private final String crypto;
    private final Double amount;
    private final String tradeType;
    private final Long userId;
}
