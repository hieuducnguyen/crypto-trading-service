package com.hieunguyen.cryptotradingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunguyen.cryptotradingservice.model.WalletModel;
import com.hieunguyen.cryptotradingservice.model.trading.TradingRequest;
import com.hieunguyen.cryptotradingservice.model.trading.TradingResponse;
import com.hieunguyen.cryptotradingservice.model.transactionhistory.TransactionHistoryResponse;
import com.hieunguyen.cryptotradingservice.model.walletbalance.WalletBalanceResponse;
import com.hieunguyen.cryptotradingservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void whenTrade_thenReturnTradingResponse() throws Exception {
        TradingRequest tradingRequest = TradingRequest.builder().build();

        TradingResponse tradingResponse = new TradingResponse();
        tradingResponse.setDescription("Buy 1 BITCOIN successfully");

        when(userService.trade(any(TradingRequest.class))).thenReturn(tradingResponse);

        mockMvc.perform(post("/api/user/trading")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tradingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is(tradingResponse.getDescription())));
    }

    @Test
    public void whenGetBalance_thenReturnWalletBalanceResponse() throws Exception {
        WalletBalanceResponse balanceResponse = new WalletBalanceResponse();
        List<WalletModel> wallets = new ArrayList<>();
        WalletModel walletModel = new WalletModel();
        walletModel.setBalance(1.0);
        walletModel.setCryptoCurrency("BITCOIN");
        wallets.add(walletModel);
        balanceResponse.setWallets(wallets);

        when(userService.getBalance(anyLong())).thenReturn(balanceResponse);

        mockMvc.perform(get("/api/user/balance/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.wallets[0].balance", is(walletModel.getBalance())));
    }

    @Test
    public void whenGetTransaction_thenReturnTransactionHistoryResponse() throws Exception {
        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
        transactionHistoryResponse.setTransactions(new ArrayList<>());

        when(userService.getTransaction(anyLong())).thenReturn(transactionHistoryResponse);

        mockMvc.perform(get("/api/user/transaction/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transactions", hasSize(transactionHistoryResponse.getTransactions().size())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
