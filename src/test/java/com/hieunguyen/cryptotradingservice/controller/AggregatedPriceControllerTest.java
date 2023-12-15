package com.hieunguyen.cryptotradingservice.controller;

import com.hieunguyen.cryptotradingservice.model.aggregateprice.AggregatedPriceResponse;
import com.hieunguyen.cryptotradingservice.service.AggregatedPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AggregatedPriceControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AggregatedPriceService aggregatedPriceService;

    @InjectMocks
    private AggregatedPriceController aggregatedPriceController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(aggregatedPriceController).build();
    }

    @Test
    public void testGetPrice() throws Exception {
        AggregatedPriceResponse mockResponse = new AggregatedPriceResponse();

        when(aggregatedPriceService.getPrice()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/aggregated-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aggregatedPriceModelList").exists())
                .andExpect(jsonPath("$.aggregatedPriceModelList[0].name").value("Bitcoin"));
        // Add more assertions as needed

        verify(aggregatedPriceService).getPrice();
    }
}