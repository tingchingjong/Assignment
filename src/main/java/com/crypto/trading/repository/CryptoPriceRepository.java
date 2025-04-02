package com.crypto.trading.repository;

import com.crypto.trading.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {
    CryptoPrice findTopBySymbolOrderByTimestampDesc(String symbol);
}
