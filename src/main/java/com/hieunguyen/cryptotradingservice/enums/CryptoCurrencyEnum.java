package com.hieunguyen.cryptotradingservice.enums;

public enum CryptoCurrencyEnum {
    BITCOIN("BTCUSDT"),
    ETHEREUM("ETHUSDT"),
    USDT("USDT");

    private final String symbol;

    CryptoCurrencyEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
