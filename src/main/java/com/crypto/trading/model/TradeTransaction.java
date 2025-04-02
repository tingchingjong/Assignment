package com.crypto.trading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "trade_transaction")
public class TradeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "TRADE_PRICE")
    private BigDecimal tradePrice;

    @Column(name = "TRADE_TYPE")
    private String tradeType;

    @Column(name = "USER_ID")
    private Long userId;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
