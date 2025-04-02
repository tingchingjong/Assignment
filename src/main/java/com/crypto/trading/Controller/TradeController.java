package com.crypto.trading.Controller;

import com.crypto.trading.model.CryptoPrice;
import com.crypto.trading.model.TradeTransaction;
import com.crypto.trading.model.WalletBalance;
import com.crypto.trading.repository.CryptoPriceRepository;
import com.crypto.trading.repository.TradeTransactionRepository;
import com.crypto.trading.repository.WalletBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private WalletBalanceRepository walletBalanceRepository;

    @Autowired
    private TradeTransactionRepository tradeTransactionRepository;

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @PostMapping
    public ResponseEntity<?> executeTrade(
            @RequestParam Long userId,
            @RequestParam String symbol,
            @RequestParam String tradeType,
            @RequestParam BigDecimal quantity) {

        if (!symbol.equals("BTCUSDT") && !symbol.equals("ETHUSDT")) {
            return ResponseEntity.badRequest().body("Unsupported trading pair");
        }

        CryptoPrice cryptoPrice = cryptoPriceRepository.findTopBySymbolOrderByTimestampDesc(symbol);
        if (cryptoPrice == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Price not available");
        }

        BigDecimal tradePrice = tradeType.equals("BUY") ?
                cryptoPrice.getBestAskPrice() : cryptoPrice.getBestBidPrice();
        BigDecimal totalAmount = tradePrice.multiply(quantity);

        WalletBalance wallet = walletBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (tradeType.equals("BUY")) {
            if (wallet.getUsdtBalance().compareTo(totalAmount) < 0) {
                return ResponseEntity.badRequest().body("Insufficient USDT balance");
            }

            wallet.setUsdtBalance(wallet.getUsdtBalance().subtract(totalAmount));
            if (symbol.equals("BTCUSDT")) {
                wallet.setBtcBalance(wallet.getBtcBalance().add(quantity));
            } else {
                wallet.setEthBalance(wallet.getEthBalance().add(quantity));
            }
        } else {
            if (symbol.equals("BTCUSDT")) {
                if (wallet.getBtcBalance().compareTo(quantity) < 0) {
                    return ResponseEntity.badRequest().body("Insufficient BTC balance");
                }
                wallet.setBtcBalance(wallet.getBtcBalance().subtract(quantity));
            } else {
                if (wallet.getEthBalance().compareTo(quantity) < 0) {
                    return ResponseEntity.badRequest().body("Insufficient ETH balance");
                }
                wallet.setEthBalance(wallet.getEthBalance().subtract(quantity));
            }
            wallet.setUsdtBalance(wallet.getUsdtBalance().add(totalAmount));
        }

        walletBalanceRepository.save(wallet);

        TradeTransaction transaction = new TradeTransaction();
        transaction.setUserId(userId);
        transaction.setSymbol(symbol);
        transaction.setTradeType(tradeType);
        transaction.setQuantity(quantity);
        transaction.setTradePrice(tradePrice);
        transaction.setTimestamp(LocalDateTime.now());

        tradeTransactionRepository.save(transaction);

        return ResponseEntity.ok(transaction);
    }

}
