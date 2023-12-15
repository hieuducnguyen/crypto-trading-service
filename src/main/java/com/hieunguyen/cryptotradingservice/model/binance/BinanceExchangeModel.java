package com.hieunguyen.cryptotradingservice.model.binance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.hieunguyen.cryptotradingservice.constant.CurrencyConstant.BITCOIN;
import static com.hieunguyen.cryptotradingservice.constant.CurrencyConstant.ETHEREUM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinanceExchangeModel {
    private List<BinanceTickerModel> tickers;

    public Double getBidPriceBitcoin() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN)) {
                return ticker.getBidPrice();
            }
        }
        return null;
    }

    public Double getAskPriceBitcoin(){
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN)) {
                return ticker.getAskPrice();
            }
        }
        return null;
    }

    public Double getBidPriceEthereum() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM)) {
                return ticker.getBidPrice();
            }
        }
        return null;
    }

    public Double getAskPriceEthereum() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM)) {
                return ticker.getAskPrice();
            }
        }
        return null;
    }

}
