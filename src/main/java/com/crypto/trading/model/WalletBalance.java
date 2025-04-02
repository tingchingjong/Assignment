package com.crypto.trading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_balance")
public class WalletBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BTC_BALANCE")
    private BigDecimal btcBalance;

    @Column(name = "ETH_BALANCE")
    private BigDecimal ethBalance;

    @Column(name = "USDT_BALANCE")
    private BigDecimal usdtBalance;

    @Column(name = "USER_ID")
    private Long userId;

    public BigDecimal getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(BigDecimal btcBalance) {
        this.btcBalance = btcBalance;
    }

    public BigDecimal getEthBalance() {
        return ethBalance;
    }

    public void setEthBalance(BigDecimal ethBalance) {
        this.ethBalance = ethBalance;
    }

    public BigDecimal getUsdtBalance() {
        return usdtBalance;
    }

    public void setUsdtBalance(BigDecimal usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
