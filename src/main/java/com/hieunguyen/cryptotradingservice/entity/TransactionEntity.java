package com.hieunguyen.cryptotradingservice.entity;

import com.hieunguyen.cryptotradingservice.enums.TradeTypeEnum;
import com.hieunguyen.cryptotradingservice.model.TransactionModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntity {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader", nullable = false)
    private UserEntity trader;

    @Column(name = "trade_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeTypeEnum tradeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private CryptocurrencyEntity cryptocurrency;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "price_per_item", nullable = false)
    private BigDecimal pricePerItem;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    public TransactionModel toModel() {
        return TransactionModel.builder()
                .tradeType(tradeType)
                .cryptocurrency(cryptocurrency.getName())
                .quantity(quantity)
                .pricePerItem(pricePerItem)
                .tradeAt(DATE_FORMAT.format(createdAt))
                .build();
    }
}
