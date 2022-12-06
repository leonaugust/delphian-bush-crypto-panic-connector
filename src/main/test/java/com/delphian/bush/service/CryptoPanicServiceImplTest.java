package com.delphian.bush.service;

import com.delphian.bush.dto.CryptoNewsResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static com.delphian.bush.service.CryptoPanicServiceImpl.TEST_PROFILE;
import static com.delphian.bush.util.Constants.*;
import static com.delphian.bush.util.NewsUtil.getCurrenciesCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoPanicServiceImplTest {

    public static final int MOCKED_NEWS_COUNT = 20;
    public static final int MOCKED_NEWS_BTC_COUNT = 7;
    public static final int MOCKED_NEWS_NULL_COUNT = 8;
    public static final int MOCKED_NEWS_ETH_COUNT = 2;
    public static final int MOCKED_NEWS_RUNE_COUNT = 1;
    public static final int MOCKED_NEWS_ADA_COUNT = 2;
    CryptoPanicService cryptoPanicService = new CryptoPanicServiceImpl();

    @Test
    void getCryptoNewsByProfileTest() {
        CryptoNewsResponse response = cryptoPanicService.getCryptoNewsByProfile(TEST_PROFILE, null, false, Optional.empty());
        Map<String, Integer> currenciesCount = getCurrenciesCount(response.getResults(), true);
        assertEquals(MOCKED_NEWS_COUNT, response.getResults().size());
        assertEquals(MOCKED_NEWS_BTC_COUNT, currenciesCount.get(BTC));
        assertEquals(MOCKED_NEWS_NULL_COUNT, currenciesCount.get(null));
        assertEquals(MOCKED_NEWS_ETH_COUNT, currenciesCount.get(ETH));
        assertEquals(MOCKED_NEWS_RUNE_COUNT, currenciesCount.get(RUNE));
        assertEquals(MOCKED_NEWS_ADA_COUNT, currenciesCount.get(ADA));
    }

}