package com.hieunguyen.cryptotradingservice.service;

import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import com.hieunguyen.cryptotradingservice.entity.TransactionEntity;
import com.hieunguyen.cryptotradingservice.entity.UserEntity;
import com.hieunguyen.cryptotradingservice.entity.WalletEntity;
import com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum;
import com.hieunguyen.cryptotradingservice.enums.TradeTypeEnum;
import com.hieunguyen.cryptotradingservice.exceptions.InvalidInputDataException;
import com.hieunguyen.cryptotradingservice.exceptions.NotFoundException;
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
            throw new InvalidInputDataException(String.format("Invalid trading request %s", tradingRequest));
        }

        // Get trading request data
        TradeTypeEnum tradeType = TradeTypeEnum.valueOf(tradingRequest.getTradeType().toUpperCase());
        CryptoCurrencyEnum tradingCurrency = CryptoCurrencyEnum.valueOf(tradingRequest.getCrypto().toUpperCase());
        Double amount = tradingRequest.getAmount();
        Long userId = tradingRequest.getUserId();

        // Get user data
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
        WalletEntity usdtWallet = user.getWallets().stream().filter(w -> w.getCryptocurrencyEntity().getSymbol()
                        .equalsIgnoreCase(USDT.getSymbol())).findFirst()
                .orElseThrow(() -> new InvalidInputDataException(String.format("Wallet not found %s", USDT.getSymbol())));
        WalletEntity tradingWallet = user.getWallets().stream().filter(w -> w.getCryptocurrencyEntity().getSymbol()
                        .equalsIgnoreCase(tradingCurrency.getSymbol())).findFirst()
                .orElseThrow(() -> new InvalidInputDataException(String.format("Wallet not found %s", tradingCurrency.getSymbol())));
        MarketDataEntity marketData = marketDataRepository.findFirstByCryptoCurrencyOrderByCreatedAtDesc(tradingCurrency.getSymbol());

        // Trade logic
        TradingResponse response = new TradingResponse();
        if (tradeType.equals(TradeTypeEnum.BUY)) {
            BigDecimal askPrice = marketData.getAskPrice();
            Double askQuality = marketData.getAskQuality();
            Double total = askPrice.multiply(BigDecimal.valueOf(askQuality)).doubleValue();
            // Check if user has enough balance to buy
            if (total <= usdtWallet.getBalance() && askQuality <= amount) {
                usdtWallet.setBalance(usdtWallet.getBalance() - total);
                tradingWallet.setBalance(tradingWallet.getBalance() + askQuality);
                String description = String.format("Buy %s %s with %s %s", askQuality, tradingCurrency.name(),
                        total, USDT.name());
                log.info("user: {}, description: {}", user, description);
                response.setDescription(description);
                response.setWalletUSDTModel(usdtWallet.toModel());
                response.setWalletTradingModel(tradingWallet.toModel());
                TransactionEntity transaction = TransactionEntity.builder()
                        .cryptocurrency(marketData.getCryptoCurrency())
                        .pricePerItem(askPrice)
                        .quantity(BigDecimal.valueOf(askQuality))
                        .tradeType(tradeType)
                        .trader(user)
                        .build();
                transactionRepository.save(transaction);
            } else {
                if (total > usdtWallet.getBalance()) {
                    throw new InvalidInputDataException(String.format("Not enough balance to buy %s %s with price %s USDT",
                            askQuality, tradingCurrency.name(), total));
                } else {
                    throw new InvalidInputDataException(String.format("Need to buy upto %s %s", askQuality, tradingCurrency.name()));
                }

            }
        } else {
            BigDecimal bidPrice = marketData.getBidPrice();
            Double bidQuality = marketData.getBidQuality();
            double total = bidPrice.multiply(BigDecimal.valueOf(bidQuality)).doubleValue();
            // Check if user has enough balance to sell
            if (amount <= tradingWallet.getBalance() && bidQuality <= amount) {
                usdtWallet.setBalance(usdtWallet.getBalance() + total);
                tradingWallet.setBalance(tradingWallet.getBalance() - bidQuality);
                String description = String.format("Sell %s %s with %s %s", bidQuality, tradingCurrency.name(),
                        total, USDT.name());
                log.info("user: {}, description: {}", user, description);
                response.setDescription(description);
                response.setWalletUSDTModel(usdtWallet.toModel());
                response.setWalletTradingModel(tradingWallet.toModel());
                TransactionEntity transaction = TransactionEntity.builder()
                        .cryptocurrency(marketData.getCryptoCurrency())
                        .pricePerItem(bidPrice)
                        .quantity(BigDecimal.valueOf(bidQuality))
                        .tradeType(tradeType)
                        .trader(user)
                        .build();
                transactionRepository.save(transaction);
            } else {
                if (amount > tradingWallet.getBalance()) {
                    throw new InvalidInputDataException(String.format("Not enough balance to sell %s %s",
                            amount, tradingCurrency.name()));
                } else {
                    throw new InvalidInputDataException(String.format("Need to sell upto %s %s", amount, tradingCurrency.name()));
                }

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
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
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
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
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
