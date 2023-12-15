package com.hieunguyen.cryptotradingservice.model;

import com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletModel {
    private String cryptoCurrency;
    private Double balance;
}
