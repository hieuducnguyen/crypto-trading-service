package com.hieunguyen.cryptotradingservice.model.houbi;

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
public class HuobiExchangeResponse {
    private List<HuobiTickerModel> data;
    private String status;
    private Long ts;

    public Double getBidPriceBitcoin() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN)) {
                return ticker.getBid();
            }
        }
        return null;
    }

    public Double getAskPriceBitcoin() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(BITCOIN)) {
                return ticker.getAsk();
            }
        }
        return null;
    }

    public Double getBidPriceEthereum() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM)) {
                return ticker.getBid();
            }
        }
        return null;
    }

    public Double getAskPriceEthereum() {
        if (data == null) {
            return null;
        }
        for (HuobiTickerModel ticker : data) {
            if (ticker.getSymbol().equalsIgnoreCase(ETHEREUM)) {
                return ticker.getAsk();
            }
        }
        return null;
    }
}
