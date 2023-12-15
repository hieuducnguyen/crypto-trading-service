package com.hieunguyen.cryptotradingservice.model.houbi;

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
public class HuobiExchangeResponse {
    private List<HuobiTickerModel> data;
    private String status;
    private Long ts;

    public TradingItemModel getBidPriceBitcoin() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getBid())
                        .size(ticker.getBidSize())
                        .build();
            }
        }
        return null;
    }

    public TradingItemModel getAskPriceBitcoin() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getAsk())
                        .size(ticker.getAskSize())
                        .build();
            }
        }
        return null;
    }

    public TradingItemModel getBidPriceEthereum() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getBid())
                        .size(ticker.getBidSize())
                        .build();
            }
        }
        return null;
    }

    public TradingItemModel getAskPriceEthereum() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM.getSymbol())) {
                return TradingItemModel.builder()
                        .price(ticker.getAsk())
                        .size(ticker.getAskSize())
                        .build();
            }
        }
        return null;
    }
}
