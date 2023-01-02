/*
 * MIT License
 *
 * Copyright (c) 2023 Leon Galushko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.delphian.bush.service;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @BeforeEach
    public void waitApiTimeout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10L);
    }

    @Test
    void getFilteredNewsIsSortedTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getFilteredNews(true, Optional.empty());
        assertEquals(20, news.size());

        List<CryptoNews> expectedSorted = news.stream().sorted(Comparator.comparing(CryptoNews::getId))
                .collect(Collectors.toList());
        assertEquals(expectedSorted, news);
    }

    @Test
    void getFilteredNewsFilteredByOffsetTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getFilteredNews(true, Optional.empty());
        int pivot = news.size() / 2; ;
        Long offset = Long.valueOf(news.get(pivot - 1).getId());
        List<CryptoNews> filtered = cryptoPanicService.getFilteredNews(true, Optional.of(offset));
        assertTrue(filtered.size() < news.size());
        assertEquals(pivot, filtered.size());
    }

    @Test
    void getNewsWithTestProfileTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, TEST_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, null);
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getNews(true, Optional.empty());
        Map<String, Integer> currenciesCount = getCurrenciesCount(news, true);
        assertEquals(MOCKED_NEWS_COUNT, news.size());
        assertEquals(MOCKED_NEWS_BTC_COUNT, currenciesCount.get(BTC));
        assertEquals(MOCKED_NEWS_NULL_COUNT, currenciesCount.get(null));
        assertEquals(MOCKED_NEWS_ETH_COUNT, currenciesCount.get(ETH));
        assertEquals(MOCKED_NEWS_RUNE_COUNT, currenciesCount.get(RUNE));
        assertEquals(MOCKED_NEWS_ADA_COUNT, currenciesCount.get(ADA));
    }

    @Test
    void getNewsRecentPageOnlyTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getNews(true, Optional.empty());
        assertEquals(20, news.size());
        String createdAt = news.get(0).getCreatedAt();
        LocalDateTime newsDate = TimeUtil.parse(createdAt);
        assertEquals(TimeUtil.nowFormatted().getDayOfMonth(), newsDate.getDayOfMonth());
    }

    @Test
    void getNewsSourceOffsetIsEmptyTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getNews(false, Optional.empty());
        assertEquals(200, news.size());
        String createdAt = news.get(0).getCreatedAt();
        LocalDateTime newsDate = TimeUtil.parse(createdAt);
        assertEquals(TimeUtil.nowFormatted().getDayOfMonth(), newsDate.getDayOfMonth());
    }

    @Test
    void getPagesBeforeOffsetIncludingTest() {
        Map<String, String> properties = new HashMap<>();
        properties.put(PROFILE_ACTIVE_CONFIG, PROD_PROFILE);
        properties.put(CRYPTO_PANIC_KEY_CONFIG, getApiKey());
        CryptoPanicServiceImpl cryptoPanicService = new CryptoPanicServiceImpl(getConfig(properties));
        List<CryptoNews> news = cryptoPanicService.getNews(true, Optional.empty());
        String sourceId = news.get(news.size() / 2).getId(); // get element from the middle
        List<CryptoNews> filteredNews = cryptoPanicService.getNews(false, Optional.of(Long.valueOf(sourceId)));
        assertEquals(20, filteredNews.size());
    }

    private CryptoPanicSourceConnectorConfig getConfig(Map<String, String> overriddenProperties) {
        return new CryptoPanicSourceConnectorConfig(getPropertiesOverridden(overriddenProperties));
    }
}