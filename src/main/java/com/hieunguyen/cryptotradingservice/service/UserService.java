package com.hieunguyen.cryptotradingservice.service;

import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import com.hieunguyen.cryptotradingservice.entity.TransactionEntity;
import com.hieunguyen.cryptotradingservice.entity.UserEntity;
import com.hieunguyen.cryptotradingservice.entity.WalletEntity;
import com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum;
import com.hieunguyen.cryptotradingservice.enums.TradeTypeEnum;
import com.hieunguyen.cryptotradingservice.model.TransactionModel;
import com.hieunguyen.cryptotradingservice.model.WalletModel;
import com.hieunguyen.cryptotradingservice.model.trading.TradingRequest;
import com.hieunguyen.cryptotradingservice.model.trading.TradingResponse;
import com.hieunguyen.cryptotradingservice.model.transactionhistory.TransactionHistoryResponse;
import com.hieunguyen.cryptotradingservice.model.walletbalance.WalletBalanceResponse;
import com.hieunguyen.cryptotradingservice.repository.MarketDataRepository;
import com.hieunguyen.cryptotradingservice.repository.TransactionRepository;
import com.hieunguyen.cryptotradingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum.USDT;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final MarketDataRepository marketDataRepository;
    private final TransactionRepository transactionRepository;

    public TradingResponse trade(TradingRequest tradingRequest) {
        if (!validateTradeRequest(tradingRequest)) {
            throw new RuntimeException("Invalid trading request");
        }
        TradeTypeEnum tradeType = TradeTypeEnum.valueOf(tradingRequest.getTradeType().toUpperCase());
        CryptoCurrencyEnum tradingCurrency = CryptoCurrencyEnum.valueOf(tradingRequest.getCrypto().toUpperCase());
        Double amount = tradingRequest.getAmount();
        Long userId = tradingRequest.getUserId();

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        WalletEntity usdtWallet = user.getWallets().stream().filter(w -> w.getCryptocurrencyEntity().getSymbol().equalsIgnoreCase(USDT.getSymbol())).findFirst()
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        WalletEntity tradingWallet = user.getWallets().stream().filter(w -> w.getCryptocurrencyEntity().getSymbol().equalsIgnoreCase(tradingCurrency.getSymbol())).findFirst()
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        MarketDataEntity marketData = marketDataRepository.findFirstByCryptoCurrencyOrderByCreatedAtDesc(tradingCurrency.getSymbol());

        TradingResponse response = new TradingResponse();
        if (tradeType.equals(TradeTypeEnum.BUY)) {
            BigDecimal askPrice = marketData.getAskPrice();
            Double askSize = marketData.getAskSize();
            Double total = askPrice.multiply(BigDecimal.valueOf(askSize)).doubleValue();
            if (total <= usdtWallet.getBalance() && askSize <= amount) {
                usdtWallet.setBalance(usdtWallet.getBalance() - total);
                tradingWallet.setBalance(tradingWallet.getBalance() + askSize);
                String description = String.format("Buy %s %s with %s %s", askSize, tradingCurrency.name(),
                        total, USDT.name());
                log.info("user: {}, description: {}", user, description);
                response.setDescription(description);
                response.setWalletUSDTModel(usdtWallet.toModel());
                response.setWalletTradingModel(tradingWallet.toModel());
                TransactionEntity transaction = TransactionEntity.builder()
                        .cryptocurrency(marketData.getCryptoCurrency())
                        .pricePerItem(askPrice)
                        .quantity(BigDecimal.valueOf(askSize))
                        .tradeType(tradeType)
                        .trader(user)
                        .build();
                transactionRepository.save(transaction);
            } else {
                throw new RuntimeException("Not enough balance");
            }
        } else {
            BigDecimal bidPrice = marketData.getBidPrice();
            Double bidSize = marketData.getBidSize();
            double total = bidPrice.multiply(BigDecimal.valueOf(bidSize)).doubleValue();
            if (total <= tradingWallet.getBalance() && bidSize <= amount) {
                usdtWallet.setBalance(usdtWallet.getBalance() + total);
                tradingWallet.setBalance(tradingWallet.getBalance() - bidSize);
                String description = String.format("Sell %s %s with %s %s", bidSize, tradingCurrency.name(),
                        total, USDT.name());
                log.info("user: {}, description: {}", user, description);
                response.setDescription(description);
                response.setWalletUSDTModel(usdtWallet.toModel());
                response.setWalletTradingModel(tradingWallet.toModel());
                TransactionEntity transaction = TransactionEntity.builder()
                        .cryptocurrency(marketData.getCryptoCurrency())
                        .pricePerItem(bidPrice)
                        .quantity(BigDecimal.valueOf(bidSize))
                        .tradeType(tradeType)
                        .trader(user)
                        .build();
                transactionRepository.save(transaction);
            } else {
                throw new RuntimeException("Not enough balance");
            }
        }
        return response;
    }

    public boolean validateTradeRequest(TradingRequest request) {
        return request.getAmount() > 0
                && (request.getTradeType() != null && (
                request.getTradeType().equalsIgnoreCase(TradeTypeEnum.BUY.name())
                        || request.getTradeType().equalsIgnoreCase(TradeTypeEnum.SELL.name())))
                && request.getCrypto() != null && (
                request.getCrypto().equalsIgnoreCase(CryptoCurrencyEnum.BITCOIN.name())
                        || request.getCrypto().equalsIgnoreCase(CryptoCurrencyEnum.ETHEREUM.name()))
                && request.getUserId() > 0;
    }

    public WalletBalanceResponse getBalance(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<WalletEntity> wallets = user.getWallets();
        List<WalletModel> walletModels = new ArrayList<>();
        for (WalletEntity wallet : wallets) {
            walletModels.add(wallet.toModel());
        }
        return WalletBalanceResponse.builder()
                .wallets(walletModels)
                .build();
    }


    public TransactionHistoryResponse getTransaction(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<TransactionEntity> transactions = transactionRepository.findByTraderOrderByCreatedAtDesc(user);
        List<TransactionModel> transactionModels = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            transactionModels.add(transaction.toModel());
        }
        return TransactionHistoryResponse.builder()
                .transactions(transactionModels)
                .build();
    }
}
