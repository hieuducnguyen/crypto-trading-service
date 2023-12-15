package com.hieunguyen.cryptotradingservice.model.transactionhistory;

import com.hieunguyen.cryptotradingservice.model.TransactionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryResponse {
    List<TransactionModel> transactions;
}
