package com.crypto.trading.Controller;

import com.crypto.trading.model.WalletBalance;
import com.crypto.trading.repository.WalletBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletBalanceRepository walletBalanceRepository;

    @GetMapping
    public ResponseEntity<WalletBalance> getWalletBalance(@RequestParam Long userId) {
        WalletBalance wallet = walletBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return ResponseEntity.ok(wallet);
    }
}
