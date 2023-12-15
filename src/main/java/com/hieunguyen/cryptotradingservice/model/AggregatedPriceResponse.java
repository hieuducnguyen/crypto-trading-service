package com.hieunguyen.cryptotradingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AggregatedPriceResponse {
    List<AggregatedPriceModel> aggregatedPriceModelList;
}
