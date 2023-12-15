package com.hieunguyen.cryptotradingservice.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties("app.common")
@Log4j2
public class AppCommonProperties {
    private Integer restTemplateConnectionTimeout;
    private Integer restTemplateReadTimeout;


    @PostConstruct
    public void init() {
        log.debug("---------------------AppCommonProperties------------------------------");
        log.info("RestTemplate connection timeout: " + restTemplateConnectionTimeout);
        log.info("RestTemplate read timeout: " + restTemplateReadTimeout);
        log.debug("-----------------------------------------------------------------");
    }
}