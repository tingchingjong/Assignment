package com.crypto.trading.Controller;

import com.crypto.trading.model.TradeTransaction;
import com.crypto.trading.repository.TradeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeHistoryController {

    @Autowired
    private TradeTransactionRepository tradeTransactionRepository;

    @GetMapping
    public ResponseEntity<List<TradeTransaction>> getTradeHistory(
            @RequestParam Long userId,
            @RequestParam(required = false) String symbol) {

        List<TradeTransaction> transactions;
        if (symbol != null) {
            transactions = tradeTransactionRepository.findByUserIdAndSymbolOrderByTimestampDesc(userId, symbol);
        } else {
            transactions = tradeTransactionRepository.findByUserIdOrderByTimestampDesc(userId);
        }

        return ResponseEntity.ok(transactions);
    }
}
