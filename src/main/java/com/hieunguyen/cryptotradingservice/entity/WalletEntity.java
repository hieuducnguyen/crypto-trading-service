package com.hieunguyen.cryptotradingservice.entity;

import com.hieunguyen.cryptotradingservice.model.WalletModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private CryptocurrencyEntity cryptocurrencyEntity;

    @Column(nullable = false)
    private Double balance;

    public WalletModel toModel() {
        return WalletModel.builder()
                .cryptoCurrency(this.cryptocurrencyEntity.getName())
                .balance(this.balance)
                .build();
    }
}
