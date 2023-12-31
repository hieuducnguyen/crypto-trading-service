package com.hieunguyen.cryptotradingservice.model.trading;

import com.hieunguyen.cryptotradingservice.model.WalletModel;
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
