package com.crypto.trading.Controller;

import com.crypto.trading.model.CryptoPrice;
import com.crypto.trading.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
public class CryptoPriceController {
    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @GetMapping("/{symbol}")
    public CryptoPrice getLatestPrice(@PathVariable String symbol) {
        return cryptoPriceRepository.findTopBySymbolOrderByTimestampDesc(symbol.toUpperCase());
    }
}
