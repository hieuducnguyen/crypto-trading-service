package com.hieunguyen.cryptotradingservice.controller;

import com.hieunguyen.cryptotradingservice.model.aggregateprice.AggregatedPriceModel;
import com.hieunguyen.cryptotradingservice.model.aggregateprice.AggregatedPriceResponse;
import com.hieunguyen.cryptotradingservice.service.AggregatedPriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AggregatedPriceController.class)
public class AggregatedPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AggregatedPriceService aggregatedPriceService;

    @Test
    public void whenGetPrice_thenReturnAggregatedPrice() throws Exception {
        AggregatedPriceResponse response = new AggregatedPriceResponse();
        List<AggregatedPriceModel> aggregatedPriceModelList = List.of(
                AggregatedPriceModel.builder()
                        .symbol("BTCUSDT")
                        .name("BITCOIN")
                        .bidPrice(BigDecimal.ONE)
                        .build()
        );
        response.setAggregatedPriceModelList(aggregatedPriceModelList);
        when(aggregatedPriceService.getPrice()).thenReturn(response);

        mockMvc.perform(get("/api/aggregated-price"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aggregatedPriceModelList[0].symbol", is("BTCUSDT")))
                .andExpect(jsonPath("$.aggregatedPriceModelList[0].name", is("BITCOIN")))
                .andExpect(jsonPath("$.aggregatedPriceModelList[0].bidPrice", is(1)));
    }
}
