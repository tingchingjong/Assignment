package com.crypto.trading.repository;

import com.crypto.trading.model.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    List<TradeTransaction> findByUserIdOrderByTimestampDesc(Long userId);
    List<TradeTransaction> findByUserIdAndSymbolOrderByTimestampDesc(Long userId, String symbol);
}
