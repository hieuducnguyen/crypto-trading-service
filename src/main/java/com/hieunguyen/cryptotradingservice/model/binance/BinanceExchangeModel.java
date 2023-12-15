package com.hieunguyen.cryptotradingservice.model.binance;

import com.hieunguyen.cryptotradingservice.model.TradingItemModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum.BITCOIN;
import static com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum.ETHEREUM;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinanceExchangeModel {
    private List<BinanceTickerModel> tickers;

    public TradingItemModel getBidPriceBitcoin() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getBidPrice())
                        .size(ticker.getBidQty())
                        .build();
            }
        }
        return null;
    }



    public TradingItemModel getAskPriceBitcoin(){
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getAskPrice())
                        .size(ticker.getAskQty())
                        .build();
            }
        }
        return null;
    }

    public TradingItemModel getBidPriceEthereum() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getBidPrice())
                        .size(ticker.getBidQty())
                        .build();
            }
        }
        return null;
    }

    public TradingItemModel getAskPriceEthereum() {
        for (BinanceTickerModel ticker : tickers) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getAskPrice())
                        .size(ticker.getAskQty())
                        .build();
            }
        }
        return null;
    }

}
