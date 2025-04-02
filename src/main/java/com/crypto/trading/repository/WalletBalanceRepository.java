package com.crypto.trading.repository;

import com.crypto.trading.model.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, Long> {
    Optional<WalletBalance> findByUserId(Long userId);
}
