package com.hieunguyen.cryptotradingservice.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@Data
@ConfigurationProperties("app.batch")
@Log4j2
public class BatchProperties {
    private String binanceApiUrl;
    private String houbiApiUrl;


    @PostConstruct
    public void init() {
        log.debug("---------------------BatchProperties------------------------------");
        log.info("Binance API URL: " + binanceApiUrl);
        log.info("Houbi API URL: " + houbiApiUrl);
        log.debug("-----------------------------------------------------------------");
    }
}