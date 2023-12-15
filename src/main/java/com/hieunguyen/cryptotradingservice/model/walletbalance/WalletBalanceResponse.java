package com.hieunguyen.cryptotradingservice.model.walletbalance;

import com.hieunguyen.cryptotradingservice.model.WalletModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletBalanceResponse {
    List<WalletModel> wallets;
}
