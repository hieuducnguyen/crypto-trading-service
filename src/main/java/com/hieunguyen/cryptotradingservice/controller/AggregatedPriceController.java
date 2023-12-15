package com.hieunguyen.cryptotradingservice.controller;

import com.hieunguyen.cryptotradingservice.model.aggregateprice.AggregatedPriceResponse;
import com.hieunguyen.cryptotradingservice.service.AggregatedPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/aggregated-price")
@RequiredArgsConstructor
@Log4j2
public class AggregatedPriceController {

    private final AggregatedPriceService aggregatedPriceService;

    @GetMapping
    public AggregatedPriceResponse getPrice() {
        log.info("Get aggregated price");
        return aggregatedPriceService.getPrice();
    }
}