package com.crypto.trading.service;

import com.crypto.trading.model.CryptoPrice;
import com.crypto.trading.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CryptoPriceService {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    private static final String BINANCE_API = "https://api.binance.com/api/v3/ticker/bookTicker";
    private static final String HUOBI_API = "https://api.huobi.pro/market/tickers";

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000) // Runs every 10 seconds
    public void fetchAndStoreBestCryptoPrices() {
        System.out.println("Fetching latest crypto prices...");
        try {
            BigDecimal[] ethPrices = fetchBestPrices("ETHUSDT");
            BigDecimal[] btcPrices = fetchBestPrices("BTCUSDT");

            storeBestPrice("ETHUSDT", ethPrices[0], ethPrices[1]);
            storeBestPrice("BTCUSDT", btcPrices[0], btcPrices[1]);

            System.out.println("Updated crypto prices successfully.");
        } catch (Exception e) {
            System.err.println("Error fetching crypto prices: " + e.getMessage());
        }
    }

    private BigDecimal[] fetchBestPrices(String symbol) {
        BigDecimal binanceBid = BigDecimal.ZERO, binanceAsk = new BigDecimal(Double.MAX_VALUE);
        BigDecimal huobiBid = BigDecimal.ZERO, huobiAsk = new BigDecimal(Double.MAX_VALUE);

        try {
            JsonNode binanceResponse = restTemplate.getForObject(BINANCE_API, JsonNode.class);
            for (JsonNode node : binanceResponse) {
                if (node.get("symbol").asText().equals(symbol)) {
                    binanceBid = new BigDecimal(node.get("bidPrice").asText());
                    binanceAsk = new BigDecimal(node.get("askPrice").asText());
                    break;
                }
            }

            JsonNode huobiResponse = restTemplate.getForObject(HUOBI_API, JsonNode.class).get("data");
            for (JsonNode node : huobiResponse) {
                if (node.get("symbol").asText().equalsIgnoreCase(symbol)) {
                    huobiBid = new BigDecimal(node.get("bid").asText());
                    huobiAsk = new BigDecimal(node.get("ask").asText());
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching prices: " + e.getMessage());
        }

        BigDecimal bestBid = binanceBid.max(huobiBid);
        BigDecimal bestAsk = binanceAsk.min(huobiAsk);

        return new BigDecimal[]{bestBid, bestAsk};
    }

    private void storeBestPrice(String symbol, BigDecimal bestBid, BigDecimal bestAsk) {
        Optional<CryptoPrice> existingPrice = Optional.ofNullable(cryptoPriceRepository.findTopBySymbolOrderByTimestampDesc(symbol));
        CryptoPrice priceRecord = existingPrice.orElse(new CryptoPrice());

        priceRecord.setSymbol(symbol);
        priceRecord.setBestBidPrice(bestBid);
        priceRecord.setBestAskPrice(bestAsk);
        priceRecord.setTimestamp(LocalDateTime.now());

        cryptoPriceRepository.save(priceRecord);
    }
}
