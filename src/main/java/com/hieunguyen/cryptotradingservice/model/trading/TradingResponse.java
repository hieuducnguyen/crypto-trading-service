package com.hieunguyen.cryptotradingservice.model.trading;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradingResponse {
    String description;
    WalletModel walletTradingModel;
    WalletModel walletUSDTModel;
}
