package com.crypto.trading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_price")
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(name = "BEST_ASK_PRICE")
    private BigDecimal bestAskPrice;

    @Column(name = "BEST_BID_PRICE")
    private BigDecimal bestBidPrice;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(BigDecimal bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public BigDecimal getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(BigDecimal bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
