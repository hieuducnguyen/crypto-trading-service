package com.hieunguyen.cryptotradingservice.service;

import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import com.hieunguyen.cryptotradingservice.model.AggregatedPriceModel;
import com.hieunguyen.cryptotradingservice.model.AggregatedPriceResponse;
import com.hieunguyen.cryptotradingservice.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hieunguyen.cryptotradingservice.constant.CurrencyConstant.BITCOIN;
import static com.hieunguyen.cryptotradingservice.constant.CurrencyConstant.ETHEREUM;

@Service
@RequiredArgsConstructor
public class AggregatedPriceService {

    private final MarketDataRepository marketDataRepository;

    public AggregatedPriceResponse getPrice() {
        MarketDataEntity bitcoinEntity = marketDataRepository.findFirstByCryptoCurrencyOrderByCreatedAtDesc(BITCOIN);
        MarketDataEntity ethereum = marketDataRepository.findFirstByCryptoCurrencyOrderByCreatedAtDesc(ETHEREUM);
        List<AggregatedPriceModel> aggregatedPriceModelList = List.of(
                AggregatedPriceModel.builder()
                        .name(bitcoinEntity.getCryptoCurrency().getName())
                        .symbol(bitcoinEntity.getCryptoCurrency().getSymbol())
                        .bidPrice(bitcoinEntity.getBidPrice())
                        .askPrice(bitcoinEntity.getAskPrice())
                        .timestamp(bitcoinEntity.getCreatedAt().getTime())
                        .build(),
                AggregatedPriceModel.builder()
                        .name(ethereum.getCryptoCurrency().getName())
                        .symbol(ethereum.getCryptoCurrency().getSymbol())
                        .bidPrice(ethereum.getBidPrice())
                        .askPrice(ethereum.getAskPrice())
                        .timestamp(ethereum.getCreatedAt().getTime())
                        .build()
        );
        AggregatedPriceResponse aggregatedPriceResponse = new AggregatedPriceResponse();
        aggregatedPriceResponse.setAggregatedPriceModelList(aggregatedPriceModelList);
        return aggregatedPriceResponse;
    }
}
