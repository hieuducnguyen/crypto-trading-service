package com.hieunguyen.cryptotradingservice.tasklet;

import com.hieunguyen.cryptotradingservice.entity.CryptocurrencyEntity;
import com.hieunguyen.cryptotradingservice.entity.MarketDataEntity;
import com.hieunguyen.cryptotradingservice.model.TradingItemModel;
import com.hieunguyen.cryptotradingservice.model.binance.BinanceExchangeModel;
import com.hieunguyen.cryptotradingservice.model.binance.BinanceTickerModel;
import com.hieunguyen.cryptotradingservice.model.houbi.HuobiExchangeResponse;
import com.hieunguyen.cryptotradingservice.properties.BatchProperties;
import com.hieunguyen.cryptotradingservice.repository.CryptocurrencyRepository;
import com.hieunguyen.cryptotradingservice.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum.BITCOIN;
import static com.hieunguyen.cryptotradingservice.enums.CryptoCurrencyEnum.ETHEREUM;

@Log4j2
@Component
@RequiredArgsConstructor
public class PriceAggregateScheduleTasklet implements Tasklet {

    private final RestTemplate restTemplate;
    private final MarketDataRepository marketDataRepository;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final BatchProperties batchProperties;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        try {
            // Fetch prices from Binance
            List<BinanceTickerModel> binanceTickerModelList = restTemplate.exchange(batchProperties.getBinanceApiUrl(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<BinanceTickerModel>>() {
                    }).getBody();
            log.debug("Fetched Binance price: {}", binanceTickerModelList);

            BinanceExchangeModel binancePrice = BinanceExchangeModel.builder()
                    .tickers(binanceTickerModelList)
                    .build();
            // Fetch prices from Huobi
            HuobiExchangeResponse huobiPrice = restTemplate.exchange(batchProperties.getHoubiApiUrl(),
                    HttpMethod.GET, null, HuobiExchangeResponse.class).getBody();
            log.debug("Fetched Huobi price: {}", huobiPrice);

            // Compare prices and determine the best bid and ask prices
            assert binancePrice != null;
            assert huobiPrice != null;

            TradingItemModel bestBidBitcoinPrice = binancePrice.getBidPriceBitcoin().getPrice() > huobiPrice.getBidPriceBitcoin().getPrice() ? binancePrice.getBidPriceBitcoin() : huobiPrice.getBidPriceBitcoin();
            TradingItemModel bestAskBitcoinPrice = binancePrice.getAskPriceBitcoin().getPrice() < huobiPrice.getAskPriceBitcoin().getPrice() ? binancePrice.getAskPriceBitcoin() : huobiPrice.getAskPriceBitcoin();
            //best price of Bitcoin
            CryptocurrencyEntity bitcoinEntity = cryptocurrencyRepository.findBySymbol(BITCOIN.getSymbol());
            MarketDataEntity bestPriceBitcoin = MarketDataEntity.builder()
                    .cryptoCurrency(bitcoinEntity)
                    .bidPrice(BigDecimal.valueOf(bestBidBitcoinPrice.getPrice()))
                    .bidSize(bestBidBitcoinPrice.getSize())
                    .askPrice(BigDecimal.valueOf(bestAskBitcoinPrice.getPrice()))
                    .askSize(bestAskBitcoinPrice.getSize())
                    .build();

            TradingItemModel bestBidEthereumPrice = binancePrice.getBidPriceEthereum().getPrice() > huobiPrice.getBidPriceEthereum().getPrice() ? binancePrice.getBidPriceEthereum() : huobiPrice.getBidPriceEthereum();
            TradingItemModel bestAskEthereumPrice = binancePrice.getAskPriceEthereum().getPrice() < huobiPrice.getAskPriceEthereum().getPrice() ? binancePrice.getAskPriceEthereum() : huobiPrice.getAskPriceEthereum();
            // the best price of Ethereum
            CryptocurrencyEntity ethereumEntity = cryptocurrencyRepository.findBySymbol(ETHEREUM.getSymbol());
            MarketDataEntity bestPriceEthereum = MarketDataEntity.builder()
                    .cryptoCurrency(ethereumEntity)
                    .bidPrice(BigDecimal.valueOf(bestBidEthereumPrice.getPrice()))
                    .bidSize(bestBidEthereumPrice.getSize())
                    .askPrice(BigDecimal.valueOf(bestAskEthereumPrice.getPrice()))
                    .askSize(bestAskEthereumPrice.getSize())
                    .build();

            log.info("Best price for Bitcoin: {}", bestPriceBitcoin);
            marketDataRepository.save(bestPriceBitcoin);
            log.info("Best price for Ethereum: {}", bestPriceEthereum);
            marketDataRepository.save(bestPriceEthereum);
            return RepeatStatus.FINISHED;
        } catch (Exception e) {
            log.error("Error occurred while aggregating prices", e);
            throw new UnexpectedJobExecutionException("Error occurred while aggregating prices", e);
        }
    }
}


