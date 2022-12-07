package com.delphian.bush.service;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.CRYPTO_PANIC_KEY_CONFIG;
import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.PROFILE_ACTIVE_CONFIG;
import static com.delphian.bush.service.CryptoPanicServiceImpl.*;
import static com.delphian.bush.util.Constants.*;
import static com.delphian.bush.util.NewsUtil.getCurrenciesCount;
import static com.delphian.bush.util.PropertiesUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CryptoPanicServiceImplTest {

    public static final int MOCKED_NEWS_COUNT = 20;
    public static final int MOCKED_NEWS_BTC_COUNT = 7;
    public static final int MOCKED_NEWS_NULL_COUNT = 8;
    public static final int MOCKED_NEWS_ETH_COUNT = 2;
    public static final int MOCKED_NEWS_RUNE_COUNT = 1;
    public static final int MOCKED_NEWS_ADA_COUNT = 2;

    @Test
    void getCryptoNewsByProfileTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, TEST_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, null);
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        CryptoNewsResponse response = cryptoPanicService.getCryptoNewsByProfile(false, Optional.empty());
        Map<String, Integer> currenciesCount = getCurrenciesCount(response.getResults(), true);
        assertEquals(MOCKED_NEWS_COUNT, response.getResults().size());
        assertEquals(MOCKED_NEWS_BTC_COUNT, currenciesCount.get(BTC));
        assertEquals(MOCKED_NEWS_NULL_COUNT, currenciesCount.get(null));
        assertEquals(MOCKED_NEWS_ETH_COUNT, currenciesCount.get(ETH));
        assertEquals(MOCKED_NEWS_RUNE_COUNT, currenciesCount.get(RUNE));
        assertEquals(MOCKED_NEWS_ADA_COUNT, currenciesCount.get(ADA));
    }

    @Test
    void getCryptoNewsNotFetchAllPreviousNewsTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        CryptoNewsResponse cryptoNewsResponse = cryptoPanicService.getCryptoNewsByProfile(false, Optional.empty());
        assertEquals(20, cryptoNewsResponse.getResults().size());
        String createdAt = cryptoNewsResponse.getResults().get(0).getCreatedAt();
        LocalDateTime newsDate = TimeUtil.parse(createdAt);
        assertEquals(TimeUtil.nowFormatted().getDayOfMonth(), newsDate.getDayOfMonth());
    }

    @Test
    void getCryptoNewsSourceOffsetIsEmptyTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        CryptoNewsResponse cryptoNewsResponse = cryptoPanicService.getCryptoNewsByProfile(true, Optional.empty());
        assertEquals(200, cryptoNewsResponse.getResults().size());
        String createdAt = cryptoNewsResponse.getResults().get(0).getCreatedAt();
        LocalDateTime newsDate = TimeUtil.parse(createdAt);
        assertEquals(TimeUtil.nowFormatted().getDayOfMonth(), newsDate.getDayOfMonth());
    }

    @Test
    void getNewsFilteredByLatestOffsetTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        CryptoNewsResponse allPages = cryptoPanicService.getCryptoNewsByProfile(false, Optional.empty());
        String sourceId = allPages.getResults().get(allPages.getResults().size() / 2).getId(); // get element from the middle
        CryptoNewsResponse filteredNews = cryptoPanicService.getCryptoNewsByProfile(true, Optional.of(Long.valueOf(sourceId)));
        assertEquals(20, filteredNews.getResults().size());
    }

    private CryptoPanicSourceConnectorConfig getConfig(Map<String, String> overriddenProperties) {
        return new CryptoPanicSourceConnectorConfig(getPropertiesOverridden(overriddenProperties));
    }
}