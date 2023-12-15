package com.hieunguyen.cryptotradingservice.model;

import com.hieunguyen.cryptotradingservice.enums.TradeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionModel {
    private TradeTypeEnum tradeType;
    private String cryptocurrency;
    private BigDecimal quantity;
    private BigDecimal pricePerItem;
    private String tradeAt;
}
