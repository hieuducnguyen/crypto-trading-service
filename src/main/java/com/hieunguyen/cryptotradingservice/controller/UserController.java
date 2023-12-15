package com.hieunguyen.cryptotradingservice.controller;

import com.hieunguyen.cryptotradingservice.model.trading.TradingRequest;
import com.hieunguyen.cryptotradingservice.model.trading.TradingResponse;
import com.hieunguyen.cryptotradingservice.model.walletbalance.WalletBalanceResponse;
import com.hieunguyen.cryptotradingservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/trading")
    @Transactional
    public TradingResponse trade(@RequestBody TradingRequest tradingRequest) {
        log.info("trade tradingRequest: {}", tradingRequest);
        return userService.trade(tradingRequest);
    }

    @GetMapping("/balance/{userId}")
    public WalletBalanceResponse getBalance(@PathVariable("userId") Long userId) {
        log.info("getBalance {}", userId);
        return userService.getBalance(userId);
    }
}