package com.delphian.bush.service;

import com.delphian.bush.config.CryptoPanicSourceConnectorConfig;
import com.delphian.bush.dto.CryptoNews;
import com.delphian.bush.dto.CryptoNewsResponse;
import com.delphian.bush.util.json.NewsJsonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.CRYPTO_PANIC_KEY_CONFIG;
import static com.delphian.bush.config.CryptoPanicSourceConnectorConfig.PROFILE_ACTIVE_CONFIG;
import static com.delphian.bush.util.WebUtil.getRestTemplate;

@Slf4j
public class CryptoPanicServiceImpl implements CryptoPanicService {

    private final CryptoPanicSourceConnectorConfig config;

    public CryptoPanicServiceImpl(CryptoPanicSourceConnectorConfig config) {
        this.config = config;
    }

    public static final String TEST_PROFILE = "test";
    public static final String PROD_PROFILE = "prod";
    public static final int START_PAGE = 1;

    @Override
    public CryptoNewsResponse getCryptoNewsByProfile(boolean fetchAllPreviousNews, Optional<Long> sourceOffset) {
        String profile = config.getString(PROFILE_ACTIVE_CONFIG);
        if (profile.equals(TEST_PROFILE)) {
            return getMockedCryptoNews();
        } else {
            if (!fetchAllPreviousNews) {
                return getCryptoNews(String.valueOf(START_PAGE));
            }

            if (sourceOffset.isEmpty()) { // First poll. Fetch all pages
                return getAllPages();
            }

            return getNewsFilteredByLatestOffset(sourceOffset);
        }
    }

    @SuppressWarnings("all")
    private CryptoNewsResponse getNewsFilteredByLatestOffset(Optional<Long> sourceOffset) {
        List<CryptoNewsResponse> pagedResponses = new ArrayList<>();
        long page = 1;

        CryptoNewsResponse firstPage = getCryptoNews(String.valueOf(page));
        boolean containsLatestSourceOffset = firstPage.getResults().stream()
                .anyMatch(n -> sourceOffset.get().equals(Long.parseLong(n.getId())));
        boolean hasNext = firstPage.getNext() != null || !firstPage.getNext().equals("null");

        while (!containsLatestSourceOffset && hasNext) {
            page++;
            CryptoNewsResponse cryptoNewsNormal = getCryptoNews(String.valueOf(page));
            pagedResponses.add(cryptoNewsNormal);
            containsLatestSourceOffset = firstPage.getResults().stream()
                    .anyMatch(n -> sourceOffset.get().equals(Long.parseLong(n.getId())));
            hasNext = cryptoNewsNormal.getNext() != null && !cryptoNewsNormal.getNext().equals("null");
        }

        List<CryptoNews> news = pagedResponses.stream()
                .map(CryptoNewsResponse::getResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        firstPage.getResults().addAll(news);
        return firstPage;
    }

    private CryptoNewsResponse getAllPages() {
        CryptoNewsResponse firstPage = getCryptoNews(String.valueOf(START_PAGE));
        List<CryptoNews> news = IntStream.rangeClosed(START_PAGE + 1, 10)
                .mapToObj(page -> getCryptoNews(String.valueOf(page)))
                .map(CryptoNewsResponse::getResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        firstPage.getResults().addAll(news);
        return firstPage;
    }

    private static CryptoNewsResponse getMockedCryptoNews() {
        log.info("Using test mocked news");
        try {
            log.info("Response from mocked-news file");
            return new NewsJsonServiceImpl(new ObjectMapper()).getFromJson();
        } catch (IOException e) {
            log.error("Something happened. {}", e.getMessage());
            throw new RuntimeException();
        }
    }


    private CryptoNewsResponse getCryptoNews(String page) {
        String cryptoPanicKey = config.getString(CRYPTO_PANIC_KEY_CONFIG);
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Getting news from API");
        String apiUrl = "https://cryptopanic.com/api/v1/posts/" +
                "?auth_token=" + cryptoPanicKey +
                "&public=true" +
                "&page=" + page;
        ResponseEntity<CryptoNewsResponse> responseEntity = getRestTemplate().getForEntity(apiUrl, CryptoNewsResponse.class);
        return responseEntity.getBody();
    }
}
