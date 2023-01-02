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

package com.delphian.bush;

import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.Currency;
import com.delphian.bush.dto.NewsSource;
import com.delphian.bush.config.schema.CurrencySchema;
import com.delphian.bush.config.schema.NewsSourceSchema;
import com.delphian.bush.util.mapper.CryptoNewsMapper;
import com.delphian.bush.util.mapper.CurrencyMapper;
import com.delphian.bush.util.mapper.NewsSourceMapper;
import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoPanicSourceTaskTest {
    private CryptoPanicSourceTask cryptoPanicSourceTask = new CryptoPanicSourceTask();
    private static final Logger log = LoggerFactory.getLogger(CryptoPanicSourceTaskTest.class);

    @Test
    public void buildRecordValueTest() {
        NewsSource newsSource = NewsSource.builder()
                .domain("bitcoinist.com")
                .path(null)
                .region("en")
                .title("Bitcoinist")
                .build();

        Currency currency = Currency.builder()
                .code("SHIB")
                .title("Shiba Inu")
                .slug("shiba-inu")
                .url("https://cryptopanic.com/news/shiba-inu/")
                .build();

        CryptoNews cryptoNews = CryptoNews.builder()
                .source(newsSource)
                .currencies(Collections.singletonList(currency))
                .createdAt("2022-06-13T08:04:01Z")
                .domain("bitcoinist.com")
                .id("15482265")
                .kind("news")
                .url("https://cryptopanic.com/news/15482265/Top-crypto-projects-that-are-actually-trying-to-do-better-for-the-world")
                .publishedAt("2022-06-13T08:04:01Z")
                .slug("Top-crypto-projects-that-are-actually-trying-to-do-better-for-the-world")
                .title("Top crypto projects that are actually trying to do better for the world")
                .build();

        Struct struct = cryptoPanicSourceTask.buildRecordValue(cryptoNews);
        assertDoesNotThrow(struct::validate);

        Struct newsFromStruct = (Struct) struct.get(NewsSourceSchema.SCHEMA_NAME);
        assertEqualsNewsSource(newsSource, NewsSourceMapper.INSTANCE.to(newsFromStruct));

        List<Struct> currenciesFromStruct = (List<Struct>) struct.get(CurrencySchema.SCHEMA_NAME);
        assertEqualsCurrencies(Collections.singletonList(currency),
                currenciesFromStruct.stream().map(c -> CurrencyMapper.INSTANCE.to(c))
                        .collect(Collectors.toList())
        );

        assertEqualsCryptoNews(cryptoNews, struct);
        log.debug("News are equal");
    }

    private void assertEqualsCryptoNews(CryptoNews expected, Struct struct) {
        CryptoNews actual = CryptoNewsMapper.INSTANCE.to(struct);
        assertEquals(expected.getKind(), actual.getKind());
        assertEquals(expected.getDomain(), actual.getDomain());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getPublishedAt(), actual.getPublishedAt());
        assertEquals(expected.getSlug(), actual.getSlug());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
    }

    private void assertEqualsNewsSource(NewsSource expected, NewsSource actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getPath(), actual.getPath());
        assertEquals(expected.getDomain(), actual.getDomain());
        assertEquals(expected.getRegion(), actual.getRegion());
    }

    /**
     *
     * Checks only first element. Not null-safe
     */
    private void assertEqualsCurrencies(List<Currency> expected, List<Currency> actual) {
        Currency expectedCurrency = expected.get(0);
        Currency actualCurrency = actual.get(0);

        assertEquals(expectedCurrency.getUrl(), actualCurrency.getUrl());
        assertEquals(expectedCurrency.getCode(), actualCurrency.getCode());
        assertEquals(expectedCurrency.getSlug(), actualCurrency.getSlug());
        assertEquals(expectedCurrency.getTitle(), actualCurrency.getTitle());
    }

}
